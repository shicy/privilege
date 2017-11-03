package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.priv.model.PrivilegeModel;

import java.util.List;

/**
 * 权限
 * Created by shicy on 2017/10/18.
 */
@Mapper
@SuppressWarnings("unused")
public interface PrivilegeMapper {

    List<PrivilegeModel> getByUserId(int userId);
    List<PrivilegeModel> getByGroupId(int groupId);
    List<PrivilegeModel> getByRoleId(int roleId);
    List<PrivilegeModel> getByModuleId(int moduleId);
    List<PrivilegeModel> getByUserReference(int userId);

    void add(PrivilegeModel privilege);

    int deleteById(int id);
    int deleteByIds(int[] ids);
    int deleteByUserId(int userId);
    int deleteByGroupId(int groupId);
    int deleteByRoleId(int roleId);
    int deleteByModuleId(int moduleId);

    List<PrivilegeModel> getUserPrivsAll(int userId);
    List<PrivilegeModel> getUserPrivsByModuleIds(int userId, int[] moduleIds);
    List<PrivilegeModel> getUserPrivsByModuleCodes(int userId, String[] moduleCodes);
    List<PrivilegeModel> getUserPrivsByModuleNames(int userId, String[] moduleNames);
    List<PrivilegeModel> getUserPrivsForRefresh(int userId);

    void addUserPriv(PrivilegeModel privilege);

    int deleteUserPrivsByUserId(int userId);
    int deleteUserPrivsByGroupId(int groupId);
    int deleteUserPrivsByRoleId(int roleId);

}
