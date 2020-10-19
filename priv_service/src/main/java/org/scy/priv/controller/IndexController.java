package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.annotation.Auth;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.common.web.session.LoginForm;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.manager.TokenManager;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.scy.priv.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by shicy on 2017/8/31
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public Object version() {
        return HttpResult.ok(getAppVersion());
    }

//    @Auth
//    @RequestMapping("/test/auth")
//    @ResponseBody
//    public Object testAuth() {
//        return HttpResult.ok("ok");
//    }
//
//    @RequestMapping(value = "/test/bean", method = RequestMethod.POST)
//    @ResponseBody
//    public Object testBean(@RequestBody User user,
//            @RequestParam(value = "groupIds", required = false) String groupIds,
//            @RequestParam(value = "roleIds", required = false) String roleIds) {
//        System.out.println("TestBean: xxxxx，" + groupIds + roleIds);
//        return HttpResult.ok("ok");
//    }

    /**
     * 账户登录
     * 参数：
     * -param username 账户名称、手机号码或邮箱
     * -param password 登录密码
     * -param validCode 验证码，使用“/valid/code”获取登录验证码
     * -param validCodeId 验证码编号，获取验证码时一起返回
     * @return 返回账户信息
     */
    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginForm loginForm) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", loginForm.getUsername());
        params.put("password", loginForm.getPassword());
        params.put("validCode", loginForm.getValidCode());
        params.put("validCodeId", loginForm.getValidCodeId());
        params.put("expires", 30 * 60); // 半小时
        params.put("ip", HttpUtilsEx.getIP(request));
        params.put("domain", request.getServerName());
        params.put("userAgent", request.getHeader("User-Agent"));
        params.put("client", SessionManager.uuid.get());

        // 登录并获取 Token，登录出错将会异常返回
        String token = tokenService.doLoginByAccount(params);
        setToken(response, token);

        return HttpResult.ok(getAccountSafety(token));
    }

    /**
     * 账户退出登录
     */
    @RequestMapping(value = "/account/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        String token = SessionManager.token.get();
        if (StringUtils.isNotBlank(token)) {
            tokenService.doLogout(token);
        }
        setToken(response, null);
        return HttpResult.ok();
    }

    /**
     * 验证当前登录账户是否有效（即是否过期）
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/account/valid", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(HttpServletRequest request, HttpServletResponse response) {
        String token = SessionManager.token.get();
        if (StringUtils.isNotBlank(token)) {
            boolean isValidate = tokenService.isUserTokenValidate(token, true);
            if (isValidate) {
                setToken(response, token);
                if (HttpUtilsEx.getBooleanValue(request, "needUser")) {
                    return HttpResult.ok(getAccountSafety(token));
                }
                return HttpResult.ok();
            }
        }
        return HttpResult.error(HttpResult.NOAUTH);
    }

    @Auth
    @RequestMapping(value = "/account/access/token", method = RequestMethod.GET)
    @ResponseBody
    public Object getAccessToken() {
        String token = SessionManager.token.get();
        int accountId = TokenManager.getLoginTokenUserId(token);
        if (accountId > 0) {
            AccountModel accountModel = accountService.getByIdAsPlat(accountId);
            if (accountModel != null) {
                String accessToken = TokenManager.getAccessToken(accountModel.getCode());
                return HttpResult.ok(accessToken);
            }
        }
        return HttpResult.error("账户不存在");
    }

    /**
     * 设置Cookie，有效期30分钟
     */
    private void setToken(HttpServletResponse response, String token) {
        HttpUtilsEx.setCookie(response, SessionManager.TOKEN_KEY, token, 30 * 60);
    }

    /**
     * 获取账户信息，去掉登录密码和密钥
     * @param token 用户 Token 信息
     */
    private AccountModel getAccountSafety(String token) {
        int accountId = TokenManager.getLoginTokenUserId(token);
        if (accountId > 0) {
            AccountModel accountModel = accountService.getByIdAsPlat(accountId);
            if (accountModel != null) {
                accountModel.setPassword(null);
                accountModel.setSecret(null);
                return accountModel;
            }
        }
        return null;
    }

}
