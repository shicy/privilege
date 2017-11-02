package org.scy.priv.service.impl;

import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.priv.mapper.PrivilegeMapper;
import org.scy.priv.model.ModuleModel;
import org.scy.priv.model.Privilege;
import org.scy.priv.model.PrivilegeModel;
import org.scy.priv.service.ModuleService;
import org.scy.priv.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限相关服务类
 * Created by shicy on 2017/10/19.
 */
@Service
@SuppressWarnings("unused")
public class PrivilegeServiceImpl extends MybatisBaseService implements PrivilegeService {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private ModuleService moduleService;

    @Override
    public List<PrivilegeModel> getByUserId(int userId) {
        return privilegeMapper.getByUserId(userId);
    }

    @Override
    public List<PrivilegeModel> getByGroupId(int groupId) {
        return privilegeMapper.getByGroupId(groupId);
    }

    @Override
    public List<PrivilegeModel> getByRoleId(int roleId) {
        return privilegeMapper.getByRoleId(roleId);
    }

    @Override
    public List<PrivilegeModel> getUserAllPrivileges(int userId) {
        return privilegeMapper.getUserPrivsAll(userId);
    }

    @Override
    public void addPrivilege(Privilege privilege) {
        if (privilege == null)
            return ;

        List<Privilege> privileges = new ArrayList<Privilege>();
        privileges.add(privilege);
        addPrivileges(privileges);
    }

    @Override
    public void addPrivileges(List<Privilege> privileges) {
        if (privileges == null || privileges.size() == 0)
            return ;

        List<Integer> moduleIds = new ArrayList<Integer>();
        for (Privilege privilege: privileges) {
            if (privilege.getModuleId() > 0)
                moduleIds.add(privilege.getModuleId());
        }

        if (moduleIds.size() <= 0)
            return ;

        int[] module_ids = ArrayUtilsEx.toPrimitiveInt(moduleIds.toArray());

        List<ModuleModel> moduleModels = moduleService.getAll();
//        List<PrivilegeModel> privilegeModels = privilegeMapper.getByModuleId()

        for (Privilege privilege: privileges) {
        }
    }

    @Override
    public void setUserPrivileges(int userId, List<Privilege> privileges) {

    }

    @Override
    public void setGroupPrivileges(int groupId, List<Privilege> privileges) {

    }

    @Override
    public void setRolePrivileges(int roleId, List<Privilege> privileges) {

    }

    @Override
    public void deletePrivileges(Privilege privilege) {

    }

    @Override
    public void deletePrivileges(List<Privilege> privileges) {

    }

    @Override
    public void deleteByUserId(int userId) {

    }

    @Override
    public void deleteByModuleId(int moduleId) {

    }

    @Override
    public List<PrivilegeModel> checkByModuleIds(int userId, int[] moduleIds) {
        return null;
    }

    @Override
    public List<PrivilegeModel> checkByModuleCodes(int userId, String[] moduleCodes) {
        return null;
    }

    @Override
    public List<PrivilegeModel> checkByModuleNames(int userId, String[] moduleNames) {
        return null;
    }

}
