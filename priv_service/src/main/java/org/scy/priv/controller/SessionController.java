package org.scy.priv.controller;

import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.manager.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Session相关
 * Created by shicy on 2017/10/10.
 */
@Controller
@SuppressWarnings("unused")
public class SessionController extends BaseController {

    /**
     * 验证 AccessToken 是否过期
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/access/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object validAccessToken(@PathVariable("token") String token) {
        boolean validate = TokenManager.isAccessTokenValidate(token);
        return HttpResult.ok(validate ? "1" : "0");
    }

    /**
     * 验证用户 session 是否过期
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/session/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object validUserToken(@PathVariable("token") String token) {
        return HttpResult.ok("1");
    }

    /**
     * 获取帐户信息
     */
    @RequestMapping(value = "/session/account/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object accountInfo(@PathVariable("token") String token) {
        return HttpResult.ok();
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/session/info/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object userInfo(@PathVariable("token") String token) {
        return HttpResult.ok();
    }

}
