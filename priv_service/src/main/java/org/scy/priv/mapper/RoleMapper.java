package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.common.ds.query.Selector;
import org.scy.priv.model.RoleModel;
import org.scy.priv.model.RoleUserModel;

import java.util.List;

/**
 * 角色
 * Created by shicy on 2017/10/15.
 */
@Mapper
@SuppressWarnings("unused")
public interface RoleMapper extends BaseMapper<RoleModel> {

    RoleModel getByName(@Param("name") String name, @Param("paasId") int paasId);
    List<RoleModel> getByIds(@Param("ids") int[] ids, @Param("paasId") int paasId);

    List<RoleModel> findWithUser(Selector selector);
    int countFindWithUser(Selector selector);

    RoleUserModel getRoleUser(@Param("roleId") int roleId, @Param("userId") int userId);
    List<RoleUserModel> getRoleUsers(@Param("roleId") int roleId, @Param("userIds") int[] userIds);
    List<RoleUserModel> getAllRoleUsers(@Param("roleId") int roleId);
    List<RoleUserModel> getUserRoles(@Param("userId") int userId, @Param("roleIds") int[] roleIds);
    List<RoleUserModel> getAllUserRoles(@Param("userId") int userId);

    void addRoleUser(RoleUserModel roleUser);

    int countRoleUser(@Param("roleId") int roleId);

    int deleteRoleUserById(@Param("id") int id);
    int deleteRoleUserByRoleId(@Param("roleId") int roleId);
    int deleteRoleUserByUserId(@Param("userId") int userId);
    int deleteRoleUserByRUId(@Param("roleId") int roleId, @Param("userId") int userId);
    int deleteRoleUserByRUIds(@Param("roleId") int roleId, @Param("userIds") int[] userIds);
    int deleteRoleUserByURIds(@Param("userId") int userId, @Param("roleIds") int[] roleIds);

}
