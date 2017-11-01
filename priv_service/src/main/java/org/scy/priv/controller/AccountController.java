package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Account;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * -param nameLike 名称模糊
     * -param mobile 帐户手机号码
     * -param email 帐户邮箱
     * -param type 帐户类型
     * -param state 状态
     * -param page
     * -param size
     * @return 帐户信息列表
     */
    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("code", HttpUtilsEx.getStringValue(request, "code"));
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));
        params.put("mobile", HttpUtilsEx.getStringValue(request, "mobile"));
        params.put("email", HttpUtilsEx.getStringValue(request, "email"));

        String types = HttpUtilsEx.getStringValue(request, "type");
        if (StringUtils.isNotBlank(types)) {
            params.put("types", ArrayUtilsEx.transStrToInt(StringUtils.split(types, ',')));
        }

        String states = HttpUtilsEx.getStringValue(request, "state");
        if (StringUtils.isNotBlank(states)) {
            params.put("states", ArrayUtilsEx.transStrToInt(StringUtils.split(states, ',')));
        }

        PageInfo pageInfo = PageInfo.create(request);
        List<AccountModel> accountModels = accountService.find(params, pageInfo);

        return HttpResult.ok(accountModels, pageInfo);
    }

    /**
     * 获取帐户信息
     */
    @RequestMapping(value = "/account/info/{accountId}", method = RequestMethod.GET)
    public Object detail(int accountId) {
        if (accountId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);
        AccountModel accountModel = accountService.getById(accountId);
        return HttpResult.ok(accountModel);
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
        if (account == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        account.setId(0);
        AccountModel accountModel = accountService.save(account);

        return HttpResult.ok(accountModel);
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
        if (account == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        if (account.getId() <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);

        AccountModel accountModel = accountService.save(account);

        return HttpResult.ok(accountModel);
    }

    /**
     * 删除帐户
     * 参数：
     * -param id 想要删除的帐户编号
     */
    @RequestMapping(value = "/account/delete/{accountId}", method = RequestMethod.POST)
    public Object deleteAccount(int accountId) {
        if (accountId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);

        AccountModel accountModel = accountService.deleteById(accountId);
        if (accountModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "不存在的帐户信息");

        return HttpResult.ok();
    }

    /**
     * 更新密钥
     * 参数：
     * -param id 帐户编号
     * @return 返回新密钥
     */
    @RequestMapping(value = "/account/changesecret/{accountId}", method = RequestMethod.POST)
    public Object changeSecret(int accountId) {
        if (accountId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);
        String secret = accountService.refreshSecret(accountId);
        return HttpResult.ok(secret);
    }

    /**
     * 更改帐户状态
     * @return 返回新状态
     */
    @RequestMapping(value = "/account/changestate/{accountId}/{state}", method = RequestMethod.POST)
    public Object changeState(int accountId, short state) {
        if (accountId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);
        state = accountService.setAccountState(accountId, state);
        return HttpResult.ok(state);
    }

}
