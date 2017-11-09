package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    TokenModel getById(@Param("id") int id);
    TokenModel getByToken(@Param("token") String token);
    List<TokenModel> getByUserId(@Param("userId") int userId, @Param("paasId") int paasId);

    void add(TokenModel tokenModel);
    void setActive(@Param("token") String token, @Param("time") long time);

    int delete(TokenModel tokenModel);
    int deleteByUserId(@Param("userId") int userId);
    int deleteByToken(@Param("token") String token);

    List<TokenModel> find(Selector selector);
    int countFind(Selector selector);

    int clearInvalidateTokens(@Param("time") long time);

}
