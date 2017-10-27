package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
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

    RoleModel getByName(String name, int paasId);

    List<RoleModel> findWithUser(Selector selector);
    int countFindWithUser(Selector selector);

    RoleUserModel getRoleUser(int roleId, int userId);
    List<RoleUserModel> getRoleUsers(int roleId, int[] userIds);
    List<RoleUserModel> getAllRoleUsers(int roleId);

    void addRoleUser(RoleUserModel roleUser);

    int countRoleUser(int roleId);

    int deleteRoleUserById(int id);
    int deleteRoleUserByRoleId(int roleId);
    int deleteRoleUserByUserId(int userId);
    int deleteRoleUserByRUId(int roleId, int userId);
    int deleteRoleUserByRUIds(int roleId, int[] userIds);

}
