package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.AccountModel;

/**
 * 帐户信息相关映射类
 * Created by shicy on 2017/9/27.
 */
@Mapper
@SuppressWarnings("unused")
public interface AccountMapper extends BaseMapper<AccountModel> {

    AccountModel getByCode(@Param("code") String code);
    AccountModel getByName(@Param("name") String name);
    AccountModel getByMobile(@Param("mobile") String mobile);
    AccountModel getByEmail(@Param("email") String email);

    int updateSecret(AccountModel accountModel);
    int updatePassword(AccountModel accountModel);
    int updateState(AccountModel accountModel);

}
