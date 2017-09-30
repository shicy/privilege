package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.utils.ValidCodeUtils;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.manager.TokenManager;
import org.scy.priv.model.Account;
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
 * 账户
 * Created by shicy on 2017/8/31.
 */
@Controller
@SuppressWarnings("unused")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    /**
     * 获取 AccessToken，新获取的 token 具有15分钟的有效期，不需要频繁获取
     * 参数：
     * -param appid 帐户对应的应用编号
     * -param secret 密钥
     * @return 返回一个32位的 token 字符串
     */
    @RequestMapping(value = "/account/token", method = RequestMethod.POST)
    @ResponseBody
    public Object accessToken(HttpServletRequest request) {
        String appid = HttpUtilsEx.getStringValue(request, "appid");
        String secret = HttpUtilsEx.getStringValue(request, "secret");

        if (StringUtils.isBlank(appid) || StringUtils.isBlank(secret)) {
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);
        }

        AccountModel account = accountService.getWithSecret(appid, secret);
        if (account == null)
            return HttpResult.error(Const.MSG_CODE_ACCOUNTERROR);

        String token = TokenManager.getAccessToken(account.getCode());
        if (token == null)
            return HttpResult.error("获取 AccessToken 失败！");

        return HttpResult.ok(token);
    }

    /**
     * 注册帐户
     * @return
     */
    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(HttpServletRequest request) {
        return new Account();
    }

    /**
     * 邮箱注册时，获取验证码
     * @return
     */
    @RequestMapping(value = "/account/register/validcode", method = RequestMethod.GET)
    @ResponseBody
    public Object getValidInfo() {
        String code = ValidCodeUtils.getCode(4);
        String image = ValidCodeUtils.getBase64CodeImage(code);
        Map<String, String> datas = new HashMap<String, String>();
//        datas.put("code", code);
        datas.put("url", image);
        return HttpResult.ok(datas);
    }

    /**
     * 发送短信验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/account/register/sendcode/{mobile}", method = RequestMethod.GET)
    @ResponseBody
    public Object sendValidCode(@PathVariable("mobile") String mobile) {
        return HttpResult.ok(null);
    }

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

}
