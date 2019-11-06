package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.scy.cache.CachedClientAdapter;
import org.scy.common.Const;
import org.scy.common.exception.ResultException;
import org.scy.common.manager.SchedulerManager;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.utils.ValidCodeUtils;
import org.scy.common.web.model.ValidInfo;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.manager.TokenManager;
import org.scy.priv.mapper.AccountMapper;
import org.scy.priv.mapper.LoginRecordMapper;
import org.scy.priv.mapper.TokenMapper;
import org.scy.priv.model.AccountModel;
import org.scy.priv.model.LoginRecordModel;
import org.scy.priv.model.TokenModel;
import org.scy.priv.model.UserModel;
import org.scy.priv.service.AccountService;
import org.scy.priv.service.TokenService;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户Token服务类
 * Created by shicy on 2017/10/30.
 */
@Service
@SuppressWarnings("unused")
public class TokenServiceImpl extends MybatisBaseService implements TokenService {

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private LoginRecordMapper loginRecodeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    public TokenServiceImpl() {
        startClearTask();
    }

    @Override
    public String getAccountAccessToken(String code, String secret) {
        AccountModel accountModel = accountService.getWithSecret(code, secret);
        if (accountModel == null)
            throw new ResultException(Const.MSG_CODE_ACCOUNTERROR, "帐户不存在或密码错误");
        return TokenManager.getAccessToken(accountModel.getCode());
    }

    @Override
    public AccountModel getAccountByToken(String token) {
        String code = TokenManager.getAccessTokenValue(token);
        if (StringUtils.isNotBlank(code)) {
            // 该服务本身和权限相关，“/session/account/{token}”会用到
            // 使用 accountService.getByCode(code) 的话会导致死循环

            // 这里就暂时忽略权限了，接口上返回需要数据安全过滤
            return accountMapper.getByCode(code);
        }
        return null;
    }

    @Override
    public UserModel getUserByToken(String token) {
        int userId = TokenManager.getLoginTokenUserId(token);
        if (userId > 0)
            return userService.getById(userId);
        return null;
    }

    @Override
    public void deleteByUserId(int userId) {
        if (!SessionManager.isPlatform()) {
            UserModel userModel = userService.getById(userId);
            if (userModel == null)
                throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在");
        }
        tokenMapper.deleteByUserId(userId);
    }

    /**
     * @param params 参数:
     *      -username 登录名称：用户名、手机号或邮箱
     *      -expires Token 有效期限（秒）
     *      -loginType 指定登录方式，1-用户名称 2-手机号码 3-邮箱 0-所有
     *      -password 登录密码
     *      -validCode 验证码
     *      -validCodeId 验证码编号
     *      -ip 用户 IP 地址
     *      -domain 域名
     *      -userAgent 浏览器信息
     *      -client 客户端编号 uuid
     */
    @Override
    public String doLogin(Map<String, Object> params) {
        // 登录名称
        String username = (String)params.get("username");
        if (StringUtils.isBlank(username))
            throw new ResultException(10001, "登录名称不能为空");

        // 验证码
        if (!checkValidCode((String)params.get("validCodeId"), (String)params.get("validCode")))
            throw new ResultException(10002, "验证码错误");

        // 验证用户
        String password = (String)params.get("password");
        int loginType = (Integer)params.get("loginType");
        UserModel userModel = userService.validUser(username, password, (short)loginType);
        if (userModel == null)
            throw new ResultException(10003, "用户名或密码错误");

        return doLoginInner(userModel, params);
    }

    /**
     * 用户免密登录
     * @param params 参数：
     *      -username 登录名称：用户名、手机号或邮箱
     *      -expires Token 有效期限（秒）
     *      -loginType 指定登录方式，1-用户名称 2-手机号码 3-邮箱 0-所有
     *      -ip 用户 IP 地址
     *      -domain 域名
     *      -userAgent 浏览器信息
     *      -client 客户端编号 uuid
     * @return 返回该用户的 Token 信息
     */
    @Override
    public String doLoginWithoutPassword(Map<String, Object> params) {
        // 登录名称
        String username = (String)params.get("username");
        if (StringUtils.isBlank(username))
            throw new ResultException(10001, "登录名称不能为空");

        UserModel userModel = null;

        int loginType = (Integer)params.get("loginType");
        if (loginType <= 0 || (loginType & Const.LOGIN_TYPE_NAME) != 0) {
            userModel = userService.getByName(username);
        }
        if (userModel == null && (loginType <= 0 || (loginType & Const.LOGIN_TYPE_MOBILE) != 0)) {
            userModel = userService.getByMobile(username);
        }
        if (userModel == null && (loginType <= 0 || (loginType & Const.LOGIN_TYPE_EMAIL) != 0)) {
            userModel = userService.getByEmail(username);
        }

        if (userModel == null)
            throw new ResultException(10004, "用户名不存在");

        return doLoginInner(userModel, params);
    }

    /**
     * 生成当前用户 Token 信息，记录用户登录信息
     * @param user 登录用户对象
     * @param params 参数
     * @return 返回 Token 信息
     */
    private String doLoginInner(UserModel user, Map<String, Object> params) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.setExpires((Integer)params.get("expires"));
        tokenModel.setDomain((String)params.get("domain"));
        tokenModel.setClient((String)params.get("client"));
        tokenModel.setUserAgent((String)params.get("userAgent"));
        String token = TokenManager.addUserLoginToken(user, tokenModel);

        LoginRecordModel loginRecordModel = new LoginRecordModel();
        loginRecordModel.setUserId(user.getId());
        loginRecordModel.setLoginName((String)(params.get("mobile") != null ? params.get("mobile") : params.get("username")));
        loginRecordModel.setLoginType(((Integer)params.get("loginType")).shortValue());
        loginRecordModel.setValidcode((String)params.get("validCode"));
        loginRecordModel.setIp((String)params.get("ip"));
        loginRecordModel.setDomain(tokenModel.getDomain());
        loginRecordModel.setUserAgent(tokenModel.getUserAgent());
        loginRecordModel.setClient(tokenModel.getClient());
        loginRecordModel.setCreateDate(new Date());
        loginRecordModel.setPaasId(SessionManager.getAccountId());
        loginRecodeMapper.add(loginRecordModel);

        return token;
    }

    private boolean checkValidCode(String codeId, String codeValue) {
        if ("NVC".equals(codeId))
            return true;
        if (StringUtils.isBlank(codeId))
            return false;
        String value = CachedClientAdapter.getValue("login_code_" + codeId);
        return value != null && value.equalsIgnoreCase(codeValue);
    }

    @Override
    public void doLogout(String token) {
        UserModel userModel = getUserByToken(token);
        if (userModel != null) {
            tokenMapper.deleteByToken(token);
            TokenManager.removeLoginToken(token);
        }
    }

    @Override
    public boolean isUserTokenValidate(String token, boolean active) {
        return TokenManager.isLoginTokenValidate(token, active);
    }

    @Override
    public boolean isAccessTokenValidate(String token) {
        return TokenManager.isAccessTokenValidate(token);
    }

    @Override
    public ValidInfo getValidateInfo(int expires) {
        String codeId = null;
        while (codeId == null) {
            // 生成 8 位验证码编号
            codeId = StringUtilsEx.getRandomString(8);
            if (CachedClientAdapter.get("login_code_" + codeId) != null)
                codeId = null;
        }
        // 生成 4 位验证码
        String codeValue = ValidCodeUtils.getCode();
        String image = ValidCodeUtils.getBase64CodeImage(codeValue);

        // 放入缓存，有效期 15 分钟
        expires = expires <= 0 ? 15 : expires;
        CachedClientAdapter.set("login_code_" + codeId, codeValue, expires * 60);

        ValidInfo validInfo = new ValidInfo();
        validInfo.setCodeId(codeId);
        validInfo.setImageUrl(image);
        return validInfo;
    }

    @Override
    public boolean checkValidateCode(String codeId, String code) {
        return checkValidCode(codeId, code);
    }

    @Override
    public void sendLoginCode(String mobile) {
        throw new ResultException("暂不支持短信登录");
    }

    @Override
    public void doActive(String token) {
        if (TokenManager.activeLoginToken(token)) {
            tokenMapper.setActive(token, new Date().getTime());
        }
    }

    /**
     * 删除过期的Token信息
     */
    private void clear() {
        tokenMapper.clearInvalidateTokens(new Date().getTime());
    }

    private void startClearTask() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("instance", this);

        // 每小时执行一次
        Trigger trigger = SchedulerManager.newCronTrigger("0 0 * * * ? *");

        SchedulerManager.getInstance().addScheduleJob(ClearTask.class, trigger, data);
    }

    public static class ClearTask extends SchedulerManager.ThreadJob {
        @Override
        protected void executeJob(JobDataMap data) throws JobExecutionException {
            ((TokenServiceImpl) data.get("instance")).clear();
        }
    }

}
