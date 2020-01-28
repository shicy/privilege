package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.UserModel;
import org.scy.priv.model.UserProfile;

import java.util.List;

/**
 * 用户
 * Created by shicy on 2017/10/30.
 */
@Mapper
@SuppressWarnings("unused")
public interface UserMapper extends BaseMapper<UserModel> {

    UserModel getByCode(@Param("code") String code, @Param("paasId") int paasId);
    UserModel getByName(@Param("name") String name, @Param("paasId") int paasId);
    UserModel getByMobile(@Param("mobile") String mobile, @Param("paasId") int paasId);
    UserModel getByEmail(@Param("email") String email, @Param("paasId") int paasId);
    List<UserModel> getByIds(@Param("ids") int[] ids, @Param("paasId") int paasId);

    int updateState(UserModel model);
    int updatePassword(UserModel model);

}
