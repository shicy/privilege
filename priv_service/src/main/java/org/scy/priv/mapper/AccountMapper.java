package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.priv.model.AccountModel;

/**
 * 帐户信息相关映射类
 * Created by shicy on 2017/9/27.
 */
@Mapper
@SuppressWarnings("unused")
public interface AccountMapper {

    /**
     * 获取单个帐户信息
     */
    AccountModel getById(int id);
    AccountModel getByCode(String code);
    AccountModel getByName(String name);
    AccountModel getByMobile(String mobile);
    AccountModel getByEmail(String email);

    /**
     * 添加帐户
     */
    int add(AccountModel account);

    /**
     * 更新帐户
     */
    int update(AccountModel account);

}
