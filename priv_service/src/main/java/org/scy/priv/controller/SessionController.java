package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.common.web.model.ValidInfo;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.model.AccountModel;
import org.scy.priv.model.UserModel;
import org.scy.priv.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Session相关
 * Created by shicy on 2017/10/10.
 */
@Controller
@ResponseBody
@SuppressWarnings("unused")
public class SessionController extends BaseController {

    @Autowired
    private TokenService tokenService;

    /**
     * 获取 AccessToken，新获取的 token 具有15分钟的有效期，不需要频繁获取
     * 参数：
     * -param code 帐户对应的应用编号
     * -param secret 密钥
     * @return 返回一个32位的 token 字符串
     */
    @RequestMapping(value = "/token/access", method = RequestMethod.GET)
    public Object getAccessToken(HttpServletRequest request) {
        String code = HttpUtilsEx.getStringValue(request, "code");
        String secret = HttpUtilsEx.getStringValue(request, "secret");

        if (StringUtils.isBlank(code) || StringUtils.isBlank(secret))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        String token = tokenService.getAccountAccessToken(code, secret);
        if (token == null)
            return HttpResult.error(10001, "获取 AccessToken 失败！");

        return HttpResult.ok(token);
    }

    /**
     * 验证 AccessToken 是否过期
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/access/{token}", method = RequestMethod.GET)
    public Object validAccessToken(@PathVariable("token") String token) {
        boolean validate = tokenService.isAccessTokenValidate(token);
        return HttpResult.ok(validate ? "1" : "0");
    }

    /**
     * 验证用户 session 是否过期，并激活用户一次
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/session/{token}", method = RequestMethod.GET)
    public Object validUserToken(@PathVariable("token") String token) {
        boolean validate = tokenService.isUserTokenValidate(token, true);
        return HttpResult.ok(validate ? "1" : "0");
    }

    /**
     * 获取帐户信息
     */
    @AccessToken
    @RequestMapping(value = "/session/account/{token}", method = RequestMethod.GET)
    public Object accountInfo(@PathVariable("token") String token) {
        AccountModel accountModel = tokenService.getAccountByToken(token);
        if (accountModel != null) {
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("id", accountModel.getId());
            values.put("name", accountModel.getName());
            values.put("code", accountModel.getCode());
            values.put("ownerId", accountModel.getOwnerId());
            values.put("state", accountModel.getState());
            return HttpResult.ok(values);
        }
        return HttpResult.ok();
    }

    /**
     * 获取用户信息
     */
    @AccessToken
    @RequestMapping(value = "/session/info/{token}", method = RequestMethod.GET)
    public Object userInfo(@PathVariable("token") String token) {
        UserModel userModel = tokenService.getUserByToken(token);
        if (userModel != null) {
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("id", userModel.getId());
            values.put("name", userModel.getName());
            values.put("remark", userModel.getRemark());
            values.put("type", userModel.getType());
            values.put("state", userModel.getState());
            return HttpResult.ok(values);
        }
        return HttpResult.ok();
    }

    /**
     * 登录
     * -param username 用户名称、手机号或邮箱
     * -param password 登录密码
     * -param expires 有效期（秒），大于零时有效，否则无限期
     * -param loginType 登录方式，默认所有登录方式
     * -param validCode 验证码，使用“/login/code”获取登录验证码
     * -param validCodeId 验证码编号，获取验证码时一起返回
     * @return 登录成功将返回用户token信息
     */
    @AccessToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", HttpUtilsEx.getStringValue(request, "username"));
        params.put("password", HttpUtilsEx.getStringValue(request, "password"));
        params.put("loginType", HttpUtilsEx.getIntValue(request, "loginType", 0));
        params.put("validCode", HttpUtilsEx.getStringValue(request, "validCode"));
        params.put("validCodeId", HttpUtilsEx.getStringValue(request, "validCodeId"));
        params.put("expires", HttpUtilsEx.getIntValue(request, "expires", 0));
        params.put("ip", HttpUtilsEx.getIP(request));
        params.put("domain", request.getServerName());
        params.put("userAgent", request.getHeader("User-Agent"));
        params.put("client", SessionManager.uuid.get());
        String token = tokenService.doLogin(params);

        setTokenCookie(response, token, (Integer)params.get("expires"));

        return HttpResult.ok(token);
    }

    /**
     * 通过手机号码登录
     * 参数
     * -param mobile 手机号码
     * -param validCode 手机验证码
     * -param expires 有效期（秒），大于零时有效，否则无限期
     * @return 登录成功将返回用户token信息
     */
    @AccessToken
    @RequestMapping(value = "/login/mobile", method = RequestMethod.POST)
    public Object loginByMobile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", HttpUtilsEx.getStringValue(request, "mobile"));
        params.put("validCode", HttpUtilsEx.getStringValue(request, "validCode"));
        params.put("expires", HttpUtilsEx.getIntValue(request, "expires", 0));
        params.put("ip", HttpUtilsEx.getIP(request));
        params.put("domain", request.getServerName());
        params.put("userAgent", request.getHeader("User-Agent"));
        params.put("client", SessionManager.uuid.get());
        String token = tokenService.doLoginByMobile(params);

        setTokenCookie(response, token, (Integer)params.get("expires"));

        return HttpResult.ok(token);
    }

    /**
     * 登出
     * -param token 用户登录得到的token信息
     */
    @AccessToken
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        String token = HttpUtilsEx.getStringValue(request, "token");
        if (StringUtils.isBlank(token))
            token = SessionManager.getToken();

        if (StringUtils.isNotBlank(token))
            tokenService.doLogout(token);
        else
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        removeTokenCookie(response);

        return HttpResult.ok();
    }

    /**
     * 获取登录验证码，图片格式
     * @return 返回验证码信息{codeId, imageUrl}
     */
    @RequestMapping(value = "/login/code", method = RequestMethod.GET)
    public Object getValidCode() {
        ValidInfo validInfo = tokenService.getLoginValidateInfo();
        return HttpResult.ok(validInfo);
    }

    /**
     * 发送登录短信验证码
     */
    @AccessToken
    @RequestMapping(value = "/login/code/{mobile}", method = RequestMethod.GET)
    public Object sendMobileCode(String mobile) {
        tokenService.sendLoginCode(mobile);
        return HttpResult.ok();
    }

    /**
     * 添加 Cookie（如果是第三方，这样设置估计没用）
     * @param expires 过期时间（秒），小于零时无限期
     */
    private void setTokenCookie(HttpServletResponse response, String token, int expires) {
        Cookie cookie = new Cookie(SessionManager.TOKEN_KEY, token);
        cookie.setPath("/");
        cookie.setMaxAge(expires > 0 ? expires : Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    private void removeTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SessionManager.TOKEN_KEY, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
