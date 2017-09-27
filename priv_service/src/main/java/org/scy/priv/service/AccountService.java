package org.scy.priv.service;

import org.scy.priv.model.AccountModel;

/**
 * 账户相关服务
 * Created by shicy on 2017/9/5.
 */
public interface AccountService {

    /**
     * 根据编码获取帐户信息
     * @param code 帐户编码
     */
    AccountModel getByCode(String code);

    /**
     * 根据编码获取帐户信息，验证密钥信息，如果密钥错误返回 null
     * @param code 帐户编码
     * @param secret 密钥
     */
    AccountModel getWithSecret(String code, String secret);

}
