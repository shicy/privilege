package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Account;
import org.scy.priv.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 账户
 * Created by shicy on 2017/8/31.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class AccountController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * 查询帐户信息
     * 参数：
     * -param code 帐户编号
     * -param name 帐户名称
     * -param mobile 帐户手机号码
     * -param email 帐户邮箱
     * -param type 帐户类型
     * -param page
     * -param size
     * @return 帐户信息列表
     */
    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        return HttpResult.ok();
    }

    /**
     * 新增帐户
     * 参数：
     * -param type 帐户类型
     * -param name 帐户名称
     * -param mobile 帐户手机号码
     * -param email 邮箱地址（可选）
     * @return 新增的帐户信息
     */
    @RequestMapping(value = "/account/add", method = RequestMethod.POST)
    public Object addAccount(Account account) {
        return HttpResult.ok();
    }

    /**
     * 更新帐户
     * 参数：
     * -param type 帐户类型
     * -param name 帐户名称
     * -param mobile 帐户手机号码
     * -param email 邮箱地址
     * @return 帐户信息
     */
    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    public Object updateAccount(Account account) {
        return HttpResult.ok();
    }

    /**
     * 删除帐户
     * 参数：
     * -param id 想要删除的帐户编号
     */
    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public Object deleteAccount(HttpServletRequest request) {
        return HttpResult.ok();
    }

    /**
     * 更新密钥
     * 参数：
     * -param appid 应用编号
     * -param oldSecret 原密钥
     * @return 返回新密钥
     */
    @RequestMapping(value = "/account/secret/update", method = RequestMethod.POST)
    public Object changeSecret(HttpServletRequest request) {
        return HttpResult.ok();
    }

//    /**
//     * 获取 AccessToken，新获取的 token 具有15分钟的有效期，不需要频繁获取
//     * 参数：
//     * -param appid 帐户对应的应用编号
//     * -param secret 密钥
//     * @return 返回一个32位的 token 字符串
//     */
//    @RequestMapping(value = "/account/token", method = RequestMethod.POST)
//    public Object accessToken(HttpServletRequest request) {
//        String appid = HttpUtilsEx.getStringValue(request, "appid");
//        String secret = HttpUtilsEx.getStringValue(request, "secret");
//
//        if (StringUtils.isBlank(appid) || StringUtils.isBlank(secret)) {
//            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);
//        }
//
//        AccountModel account = accountService.getWithSecret(appid, secret);
//        if (account == null)
//            return HttpResult.error(Const.MSG_CODE_ACCOUNTERROR);
//
//        String token = TokenManager.getAccessToken(account.getCode());
//        if (token == null)
//            return HttpResult.error("获取 AccessToken 失败！");
//
//        return HttpResult.ok(token);
//    }

//    /**
//     * 注册帐户
//     * 参数：
//     * -param type 帐户类型
//     * -param name 帐户名称
//     * -param mobile 手机号码
//     * -param email 邮箱地址（可选）
//     * @return 返回新建的帐户信息
//     */
//    @AccessToken
//    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
//    public Object register(HttpServletRequest request, Account account, String validcode) {
//        // 平台帐户才可以创建
//        if (!SessionManager.isPlatform())
//            return HttpResult.error(Const.MSG_CODE_NOPERMISSION);
//
//        String registerCode = "register_code-" + SessionManager.uuid.get();
//
//        if (StringUtils.isBlank(validcode) || !validcode.equalsIgnoreCase(registerCode))
//            return HttpResult.error(Const.MSG_CODE_VALIDFAILED);
//
//        if (account == null)
//            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);
//
//        account.setId(0); // 确保新增
//        AccountModel newAccount = accountService.save(account);
//
//        CachedClientAdapter.delete(registerCode); // 删除验证码，用过即失效
//
//        return HttpResult.ok(newAccount);
//    }

//    /**
//     * 注册时获取验证码
//     */
//    @RequestMapping(value = "/account/register/validcode", method = RequestMethod.GET)
//    public Object getValidInfo() {
//        String code = ValidCodeUtils.getCode(4);
//        String image = ValidCodeUtils.getBase64CodeImage(code);
//
//        String uuid = SessionManager.uuid.get();
//        CachedClientAdapter.set("register_code-" + uuid, code, 15 * 60);
//
//        Map<String, Object> datas = new HashMap<String, Object>();
//        datas.put("imageUrl", image);
//
//        System.out.println(code);
//        logger.debug("register code " + code);
//
//        return HttpResult.ok(datas);
//    }

//    /**
//     * 发送短信验证码
//     * @param mobile 手机号码
//     */
//    @RequestMapping(value = "/account/register/sendcode/{mobile}", method = RequestMethod.GET)
//    public Object sendValidCode(@PathVariable("mobile") String mobile) {
//        return HttpResult.ok(null);
//    }

}
