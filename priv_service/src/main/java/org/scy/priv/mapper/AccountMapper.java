package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.AccountModel;

/**
 * 帐户信息相关映射类
 * Created by shicy on 2017/9/27.
 */
@Mapper
@SuppressWarnings("unused")
public interface AccountMapper extends BaseMapper<AccountModel> {

    AccountModel getByCode(String code);
    AccountModel getByName(String name);
    AccountModel getByMobile(String mobile);
    AccountModel getByEmail(String email);

    int updateSecret(AccountModel accountModel);
    int updateState(AccountModel accountModel);

}
