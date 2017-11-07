package org.scy.priv.manager;

import org.apache.commons.lang3.StringUtils;
import org.scy.cache.CachedClientAdapter;
import org.scy.cache.model.CachedVO;
import org.scy.common.utils.StringUtilsEx;
import org.scy.priv.model.UserModel;

import java.util.Date;

/**
 * Token 管理类
 * Created by shicy on 2017/9/30.
 */
public final class TokenManager {

    /**
     * 获取登录用户的 Token 信息，
     * @param userModel 用户信息
     * @param expires 有效期（秒），小于等于0时无限期
     */
    public static String getLoginToken(UserModel userModel, int expires) {
        if (userModel != null) {
            String token = getUniqueToken("login_token-", 32);
            String tokenKey = "login_token-" + token;
            String tokenValue = "" + userModel.getId();
            if (CachedClientAdapter.set(tokenKey, tokenValue, expires, expires))
                return token;
        }
        return null;
    }

    /**
     * 判断登录用户的 Token 是否有效
     * @param token 用户 Token 信息
     * @param active 是否激活一次 Token 信息，将延长有效期
     */
    public static boolean isLoginTokenValidate(String token, boolean active) {
        if (StringUtils.isNotBlank(token)) {
            String tokenKey = "login_token-" + token;
            CachedVO cachedVO = CachedClientAdapter.get(tokenKey);
            if (cachedVO != null) {
                if (active)
                    CachedClientAdapter.replace(tokenKey, cachedVO.getValue(), cachedVO.getFlags(), cachedVO.getFlags());
                return true;
            }
        }
        return false;
    }

    /**
     * 获取登录用户 Token 值，这里是用户编号
     */
    public static String getLoginTokenValue(String token) {
        if (StringUtils.isNotBlank(token)) {
            CachedVO cachedVO = CachedClientAdapter.get("login_token-" + token);
            if (cachedVO != null)
                return cachedVO.getValue();
        }
        return null;
    }

    /**
     * 删除登录用户 Token 信息
     */
    public static void removeLoginToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            CachedClientAdapter.delete("login_token-" + token);
        }
    }

    /**
     * 激活 Token，更新 Token 过期时间
     */
    public static boolean activeLoginToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            String tokenKey = "login_token-" + token;
            CachedVO cachedVO = CachedClientAdapter.get(tokenKey);
            if (cachedVO != null) {
                CachedClientAdapter.replace(tokenKey, cachedVO.getValue(), cachedVO.getFlags(), cachedVO.getFlags());
                return true;
            }
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
            String token = getUniqueToken("access_token_val-", 32);
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
        if (StringUtils.isNotBlank(token)) {
            CachedVO cachedVO = CachedClientAdapter.get("access_token_val-" + token);
            if (cachedVO != null && StringUtils.isNotBlank(cachedVO.getValue())) {
                if ((new Date().getTime()) / 1000 - cachedVO.getFlags() < 15 * 60)
                    return true;
            }
        }
        return false;
    }

    /**
     * 获取 AccessToken 值，即帐户编码
     * @param token AccessToken
     */
    public static String getAccessTokenValue(String token) {
        if (StringUtils.isNotBlank(token)) {
            CachedVO cachedVO = CachedClientAdapter.get("access_token_val-" + token);
            if (cachedVO != null)
                return cachedVO.getValue();
        }
        return null;
    }

    /**
     * 获取一个唯一的 token 信息
     */
    private static String getUniqueToken(String prefix, int length) {
        String token = StringUtilsEx.getRandomString(length);
        String tokenKey = prefix != null ? (prefix + token) : token;
        CachedVO cachedVO = CachedClientAdapter.get(tokenKey);
        if (cachedVO == null)
            return token;
        // 已经存在缓存中，重新获取
        return getUniqueToken(prefix, length);
    }

}
