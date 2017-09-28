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
     * 根据编码获取帐户信息
     */
    AccountModel getByCode(String code);

}
