package org.scy.priv.service;

import org.scy.priv.model.Privilege;
import org.scy.priv.model.PrivilegeModel;

import java.util.List;

/**
 * 权限相关服务
 * Created by shicy on 2017/10/19.
 */
@SuppressWarnings("unused")
public interface PrivilegeService {

    List<PrivilegeModel> getByUserId(int userId);
    List<PrivilegeModel> getByGroupId(int groupId);
    List<PrivilegeModel> getByRoleId(int roleId);

    List<PrivilegeModel> getUserAllPrivileges(int userId);

    void addPrivilege(Privilege privilege);
    void addPrivileges(List<Privilege> privileges);

    void setUserPrivileges(int userId, List<Privilege> privileges);
    void setGroupPrivileges(int groupId, List<Privilege> privileges);
    void setRolePrivileges(int roleId, List<Privilege> privileges);

    void deletePrivileges(Privilege privilege);
    void deletePrivileges(List<Privilege> privileges);
    void deleteByUserId(int userId);
    void deleteByGroupId(int groupId);
    void deleteByRoleId(int roleId);
    void deleteByModuleId(int moduleId);

    List<PrivilegeModel> checkByModuleIds(int userId, int[] moduleIds);
    List<PrivilegeModel> checkByModuleCodes(int userId, String[] moduleCodes);
    List<PrivilegeModel> checkByModuleNames(int userId, String[] moduleNames);

}
