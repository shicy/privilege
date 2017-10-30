package org.scy.priv.mapper;

import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.TokenModel;

/**
 * Token
 * Created by shicy on 2017/10/18.
 */
public interface TokenMapper extends BaseMapper<TokenModel> {

    TokenModel getByUserId(int userId);

    void deleteByUserId(int userId);

}
