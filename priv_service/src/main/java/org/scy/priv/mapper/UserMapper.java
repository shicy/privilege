package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.UserModel;

import java.util.List;

/**
 * 用户
 * Created by shicy on 2017/10/30.
 */
@Mapper
@SuppressWarnings("unused")
public interface UserMapper extends BaseMapper<UserModel> {

    UserModel getByCode(String code, int paasId);
    UserModel getByName(String name, int paasId);
    UserModel getByMobile(String mobile, int paasId);
    UserModel getByEmail(String email, int paasId);
    List<UserModel> getByIds(int[] ids, int paasId);

    int updateState(UserModel model);
    int updatePassword(UserModel model);

}
