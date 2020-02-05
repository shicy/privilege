package org.scy.priv.manager;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.Trigger;
import org.scy.cache.CachedClientAdapter;
import org.scy.cache.model.CachedVO;
import org.scy.common.manager.SchedulerManager;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.TokenMapper;
import org.scy.priv.model.TokenModel;
import org.scy.priv.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Token 管理类
 * Created by shicy on 2017/9/30.
 */
@Component
@SuppressWarnings("unused")
public final class TokenManager {

    private static TokenMapper tokenMapper;

    @Autowired
    private TokenMapper tokenMapperTemp;

    @PostConstruct
    public void init() {
        tokenMapper = tokenMapperTemp;
        startTokenClearTask();
    }

    /**
     * 添加用户登录信息
     * @param userModel 用户信息
     * @param tokenModel token 相关信息，其中 token 值非必要，会在方法内初始化
     * @return 返回 token
     */
    public static String addUserLoginToken(UserModel userModel, TokenModel tokenModel) {
        tokenModel.setId(0);
        tokenModel.setUserId(userModel.getId());
        tokenModel.setToken(getLoginTokenUnique());
        tokenModel.setLastActiveDate(new Date());
        tokenModel.setCreateDate(new Date());
        tokenModel.setPaasId(SessionManager.getAccountId());
        tokenMapper.add(tokenModel);
        return tokenModel.getToken();
    }

    /**
     * 判断登录用户的 Token 是否有效
     * @param token 用户 Token 信息
     * @param active 是否激活一次 Token 信息，将延长有效期
     */
    public static boolean isLoginTokenValidate(String token, boolean active) {
        if (StringUtils.isNotBlank(token)) {
            TokenModel tokenModel = tokenMapper.getByToken(token);
            if (tokenModel != null) {
                long now = new Date().getTime();
                long expires = tokenModel.getExpires();
                if (expires > 0 && expires + tokenModel.getLastActiveTime() < now) {
                    tokenMapper.delete(tokenModel);
                }
                else {
                    if (active) {
                        // 防止频繁操作，最多30秒激活一次
                        if (now - tokenModel.getLastActiveTime() > 30 * 1000)
                            tokenMapper.setActive(token, now);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据 token 获取用户编号
     */
    public static int getLoginTokenUserId(String token) {
        if (StringUtils.isNotBlank(token)) {
            TokenModel tokenModel = tokenMapper.getByToken(token);
            if (tokenModel != null)
                return tokenModel.getUserId();
        }
        return 0;
    }

    /**
     * 删除登录用户 Token 信息
     */
    public static void removeLoginToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            tokenMapper.deleteByToken(token);
        }
    }

    /**
     * 激活 Token，更新 Token 过期时间
     */
    public static boolean activeLoginToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            tokenMapper.setActive(token, new Date().getTime());
            return true;
        }
        return false;
    }

    /**
     * 获取一个 AccessToken，token 使用有效期为15分钟，因此无需频繁获取。
     * 另外，5分钟之内再次获取同一个 key 的 token 不会变更，否则 token 将更新并重新置为15分钟有效期
     * @param key 键值
     */
    public static String getAccessToken(String key) {
        if (StringUtils.isNotBlank(key)) {
            String tokenKey = "access_token-" + key;

            int flags = (int)((new Date().getTime()) / 1000); // 记录一下加入时间

            CachedVO cachedVO = CachedClientAdapter.get(tokenKey);
            if (cachedVO != null) {
                // 5分钟之内重复使用 Token
                if ((new Date().getTime()) / 1000 - cachedVO.getFlags() < 5 * 60) {
                    return cachedVO.getValue();
                }
                // 将原 token 置为失效状态（为防止 token 互串还需要保留一个较长时间）
                CachedClientAdapter.set("access_token_val-" + cachedVO.getValue(), "", 3 * 24 * 60 * 60, flags);
            }

            // 生成一个32位的 token 信息
            String token = getAccessTokenUnique("access_token_val-", 32);
            // 将 token 存储到缓存，有效期15分钟
            if (CachedClientAdapter.set(tokenKey, token, 15 * 60, flags)) {
                // 将 key 绑定到 token，支持反向查询校验，有效期3天防止 token 互串
                CachedClientAdapter.set("access_token_val-" + token, key, 3 * 24 * 60 * 60, flags);
                return token;
            }
        }
        return null;
    }

    /**
     * 验证 AccessToken 是否有效
     * @param token AccessToken
     */
    public static boolean isAccessTokenValidate(String token) {
        return getValidateAccessToken(token) != null;
    }

    /**
     * 获取 AccessToken 值，即帐户编码
     * @param token AccessToken
     */
    public static String getAccessTokenValue(String token) {
        CachedVO cachedVO = getValidateAccessToken(token);
        return cachedVO != null ? cachedVO.getValue() : null;
    }

    /**
     * 获取有效的 AccessToken 缓存对象
     */
    private static CachedVO getValidateAccessToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            CachedVO cachedVO = CachedClientAdapter.get("access_token_val-" + token);
            if (cachedVO != null && StringUtils.isNotBlank(cachedVO.getValue())) {
                if ((new Date().getTime()) / 1000 - cachedVO.getFlags() < 15 * 60)
                    return cachedVO;
            }
        }
        return null;
    }

    /**
     * 获取一个唯一的 token 信息
     */
    private static String getAccessTokenUnique(String prefix, int length) {
        String token = StringUtilsEx.getRandomString(length);
        String tokenKey = prefix != null ? (prefix + token) : token;
        CachedVO cachedVO = CachedClientAdapter.get(tokenKey);
        if (cachedVO == null)
            return token;
        // 已经存在缓存中，重新获取
        return getAccessTokenUnique(prefix, length);
    }

    /**
     * 获取唯一的用户登录 token
     */
    private static String getLoginTokenUnique() {
        String token = StringUtilsEx.getRandomString(32);
        TokenModel tokenModel = tokenMapper.getByToken(token);
        if (tokenModel == null)
            return token;
        return getLoginTokenUnique();
    }

    /**
     * 开启 Token 过期自动清理任务
     */
    private void startTokenClearTask() {
        // 每小时执行一次任务
        Trigger trigger = SchedulerManager.newCronTrigger("0 0 * * * ? *", true);
        SchedulerManager.getInstance().addScheduleJob(TokenCleanTask.class, trigger);
    }

    public static class TokenCleanTask extends SchedulerManager.ThreadJob {
        private Logger logger = LoggerFactory.getLogger(TokenCleanTask.class);

        @Override
        protected void executeJob(JobDataMap data) {
            logger.debug("execute job TokenCleanTask.");
            tokenMapper.clearInvalidateTokens(new Date().getTime());
        }
    }

}
