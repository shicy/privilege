package org.scy.priv.service;

import org.scy.priv.model.Account;
import org.scy.priv.model.AccountModel;

/**
 * 帐户相关服务
 * Created by shicy on 2017/9/5.
 */
public interface AccountService {

    /**
     * 根据编号获取帐户信息
     * @param id 帐户编号
     */
    AccountModel getById(int id);

    /**
     * 根据编码获取帐户信息
     * @param code 帐户编码
     */
    AccountModel getByCode(String code);

    /**
     * 根据手机号码获取帐户信息
     * @param mobile 帐户手机号码
     */
    AccountModel getByMobile(String mobile);

    /**
     * 根据邮箱获取帐户信息
     * @param email 帐户邮箱地址
     */
    AccountModel getByEmail(String email);

    /**
     * 根据编码获取帐户信息，验证密钥信息，如果密钥错误返回 null
     * @param code 帐户编码
     * @param secret 密钥
     */
    AccountModel getWithSecret(String code, String secret);

    /**
     * 保存帐户信息，新建或修改帐户信息
     * @param account 需要保存的帐户信息
     * @return 返回新的帐户信息
     */
    AccountModel save(Account account);

}
