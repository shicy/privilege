package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.query.Selector;
import org.scy.priv.model.TokenModel;

import java.util.List;

/**
 * Token
 * Created by shicy on 2017/10/18.
 */
@Mapper
@SuppressWarnings("unused")
public interface TokenMapper {

    TokenModel getById(int id);
    TokenModel getByToken(String token);
    List<TokenModel> getByUserId(int userId, int paasId);

    void add(TokenModel tokenModel);
    void setActive(String token, long time);

    int delete(TokenModel tokenModel);
    int deleteByUserId(int userId);
    int deleteByToken(String token);

    List<TokenModel> find(Selector selector);
    int countFind(Selector selector);

    int clearInvalidateTokens(long time);

}
