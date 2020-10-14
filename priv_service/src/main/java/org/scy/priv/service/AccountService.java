package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Account;
import org.scy.priv.model.AccountModel;

import java.util.List;
import java.util.Map;

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
     * 以平台权限，根据编号获取账户信息
     * @param id 账户编号
     */
    AccountModel getByIdAsPlat(int id);

    /**
     * 根据名称获取账户信息
     * @param name 账户名称
     */
    AccountModel getByName(String name);

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

    /**
     * 删除帐户
     * @param id 想要删除的帐户编号
     * @return 返回被删除的帐户信息
     */
    AccountModel deleteById(int id);

    /**
     * 查询帐户信息
     * @param params 参数：
     *      -param code 按编码查询
     *      -param name 按名称查询
     *      -param nameLike 按名称模糊查询
     *      -param mobile 按手机号码查询
     *      -param email 按邮箱地址查询
     *      -param type|types 按类型查询
     *      -param state|states 按状态查询
     * @param pageInfo 分页
     * @return 返回帐户列表
     */
    List<AccountModel> find(Map<String, Object> params, PageInfo pageInfo);

    /**
     * 刷新某个帐户的密钥
     * @return 返回该帐户新密钥信息
     */
    String refreshSecret(int accountId);

    /**
     * 更新帐户状态
     * @return 返回新状态
     */
    short setAccountState(int accountId, short state);

    /**
     * 验证账户登录是否正确
     * @param username 账户名称、手机号码或邮箱
     * @param password 账户密码
     * @return 如果验证通过返回账户信息
     */
    AccountModel validAccount(String username, String password);

}
