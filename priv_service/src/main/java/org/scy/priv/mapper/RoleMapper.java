package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.RoleModel;
import org.scy.priv.model.RoleUser;
import org.scy.priv.model.RoleUserModel;

/**
 * 角色
 * Created by shicy on 2017/10/15.
 */
@Mapper
@SuppressWarnings("unused")
public interface RoleMapper extends BaseMapper<RoleModel> {

    RoleModel getByName(String name, int paasId);

    RoleUserModel addRoleUser(RoleUser roleUser);

    int deleteRoleUserById(int id);
    int deleteRoleUserByRoleId(int roleId);
    int deleteRoleUserByUserId(int userId);
    int deleteRoleUserByRUId(int roleId, int userId);

}
