package org.scy.priv;

import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 授权服务客户端适配器
 * Created by shicy on 2017/11/13
 */
@Component
@SuppressWarnings("unused")
public class PrivilegeClientAdapter {

    private static PrivilegeClient privilegeClient;

    @Autowired
    private PrivilegeClient privilegeClientTemp;

    @PostConstruct
    public void init() {
        privilegeClient = privilegeClientTemp;
    }

    /**
     * 获取帐户信息
     */
    public static Account getAccountInfo(int accountId) {
        HttpResult result = privilegeClient.getAccountInfo(accountId);
        return result.getData(Account.class);
    }

}
