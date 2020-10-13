package org.scy.priv.controller;

import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.common.web.session.LoginForm;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页
 * Created by shicy on 2017/8/31
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult version() {
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
    public Object login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginForm loginForm) {
        return null;
    }

    /**
     * 账户退出登录
     */
    @RequestMapping(value = "/account/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    /**
     * 验证当前登录账户是否有效（即是否过期）
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/account/valid", method = RequestMethod.GET)
    public Object validate(HttpServletRequest request, HttpServletResponse response) {
        String token = SessionManager.token.get();
        boolean isValidate = tokenService.isUserTokenValidate(token, true);
        if (isValidate) {
            setToken(response, token);
            return HttpResult.ok("1");
        }
        return HttpResult.ok("0");
    }

    /**
     * 设置Cookie，有效期30分钟
     */
    private void setToken(HttpServletResponse response, String token) {
        HttpUtilsEx.setCookie(response, SessionManager.TOKEN_KEY, token, 30 * 60);
    }

}
