package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.priv.model.PrivilegeModel;

import java.util.List;

/**
 * 权限
 * Created by shicy on 2017/10/18.
 */
@Mapper
@SuppressWarnings("unused")
public interface PrivilegeMapper {

    List<PrivilegeModel> getByUserId(@Param("userId") int userId);
    List<PrivilegeModel> getByGroupId(@Param("groupId") int groupId);
    List<PrivilegeModel> getByRoleId(@Param("roleId") int roleId);
    List<PrivilegeModel> getByModuleId(@Param("moduleId") int moduleId);
    List<PrivilegeModel> getByUserReference(@Param("userId") int userId);

    void add(PrivilegeModel privilege);

    int deleteById(@Param("id") int id);
    int deleteByIds(@Param("ids") int[] ids);
    int deleteByUserId(@Param("userId") int userId);
    int deleteByGroupId(@Param("groupId") int groupId);
    int deleteByRoleId(@Param("roleId") int roleId);
    int deleteByModuleId(@Param("moduleId") int moduleId);

    List<PrivilegeModel> getUserPrivsAll(@Param("userId") int userId);
    List<PrivilegeModel> getUserPrivsByModuleIds(@Param("userId") int userId, @Param("moduleIds") int[] moduleIds);
    List<PrivilegeModel> getUserPrivsByModuleCodes(@Param("userId") int userId, @Param("moduleCodes") String[] moduleCodes);
    List<PrivilegeModel> getUserPrivsByModuleNames(@Param("userId") int userId, @Param("moduleNames") String[] moduleNames);
    List<PrivilegeModel> getUserPrivsForRefresh(@Param("userId") int userId);

    void addUserPriv(PrivilegeModel privilege);

    int deleteUserPrivsByUserId(@Param("userId") int userId);
    int deleteUserPrivsByGroupId(@Param("groupId") int groupId);
    int deleteUserPrivsByRoleId(@Param("roleId") int roleId);

}
