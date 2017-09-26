package org.scy.priv.controller;

import org.scy.common.utils.ValidCodeUtils;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
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
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    /**
     * 注册帐户
     * @return
     */
    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(HttpServletRequest request) {
        return new AccountModel();
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

}
