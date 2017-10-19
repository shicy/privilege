package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.manager.TokenManager;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    private AccountService accountService;

    /**
     * 获取 AccessToken，新获取的 token 具有15分钟的有效期，不需要频繁获取
     * 参数：
     * -param appid 帐户对应的应用编号
     * -param secret 密钥
     * @return 返回一个32位的 token 字符串
     */
    @RequestMapping(value = "/token/access", method = RequestMethod.GET)
    public Object getAccessToken() {
        return HttpResult.ok();
    }

    /**
     * 验证 AccessToken 是否过期
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/access/{token}", method = RequestMethod.GET)
    public Object validAccessToken(@PathVariable("token") String token) {
        boolean validate = TokenManager.isAccessTokenValidate(token);
        return HttpResult.ok(validate ? "1" : "0");
    }

    /**
     * 验证用户 session 是否过期
     * @return “1” - 未过期 “0” - “过期”
     */
    @RequestMapping(value = "/valid/session/{token}", method = RequestMethod.GET)
    public Object validUserToken(@PathVariable("token") String token) {
        return HttpResult.ok("1");
    }

    /**
     * 获取帐户信息
     */
    @RequestMapping(value = "/session/account/{token}", method = RequestMethod.GET)
    public Object accountInfo(@PathVariable("token") String token) {
        String code = TokenManager.getAccessTokenValue(token);
        if (StringUtils.isNotBlank(code)) {
            AccountModel accountModel = accountService.getByCode(code);
            if (accountModel != null) {
                Map<String, Object> values = new HashMap<String, Object>();
                values.put("id", accountModel.getId());
                values.put("name", accountModel.getName());
                values.put("code", accountModel.getCode());
                values.put("state", accountModel.getState());
                return HttpResult.ok(values);
            }
        }
        return HttpResult.ok();
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/session/info/{token}", method = RequestMethod.GET)
    public Object userInfo(@PathVariable("token") String token) {
        return HttpResult.ok();
    }

    /**
     * 登录
     * -param username 用户名称、手机号或邮箱
     * -param password 登录密码
     * -param expires 有效期（秒），大于零时有效，否则无限期
     * -param loginType 登录方式，默认所有登录方式
     * @return 登录成功将返回用户token信息
     */
    @AccessToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) {
        return HttpResult.ok(null);
    }

    /**
     * 登出
     * -param token 用户登录得到的token信息
     */
    @AccessToken
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
