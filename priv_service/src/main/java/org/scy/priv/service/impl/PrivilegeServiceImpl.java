package org.scy.priv.service.impl;

import org.scy.common.Const;
import org.scy.common.exception.ResultException;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.PrivilegeMapper;
import org.scy.priv.model.*;
import org.scy.priv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @Override
    public List<PrivilegeModel> getByUserId(int userId) {
        if (!SessionManager.isPlatform()) {
            UserModel userModel = userService.getById(userId);
            if (userModel == null)
                throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在");
        }
        return privilegeMapper.getByUserId(userId);
    }

    @Override
    public List<PrivilegeModel> getByGroupId(int groupId) {
        if (!SessionManager.isPlatform()) {
            GroupModel groupModel = groupService.getById(groupId);
            if (groupModel == null)
                throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户组不存在");
        }
        return privilegeMapper.getByGroupId(groupId);
    }

    @Override
    public List<PrivilegeModel> getByRoleId(int roleId) {
        if (!SessionManager.isPlatform()) {
            RoleModel roleModel = roleService.getById(roleId);
            if (roleModel == null)
                throw new ResultException(Const.MSG_CODE_NOTEXIST, "角色不存在");
        }
        return privilegeMapper.getByRoleId(roleId);
    }

    @Override
    public List<PrivilegeModel> getUserAllPrivileges(int userId) {
        if (!SessionManager.isPlatform()) {
            UserModel userModel = userService.getById(userId);
            if (userModel == null)
                throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在");
        }
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

        validPrivileges(privileges);

        // 所有模块信息
        List<ModuleModel> moduleList = moduleService.getAll();
        if (moduleList == null || moduleList.size() == 0)
            return ;

        Map<String, List<PrivilegeModel>> caches = new HashMap<String, List<PrivilegeModel>>();

        for (Privilege privilege: privileges) {
            if (privilege.getGrantType() <= 0 || privilege.getModuleId() <= 0)
                continue;
            // 添加用户权限
            if (privilege.getUserId() > 0) {
                List<PrivilegeModel> privilegeModels = caches.get("user_" + privilege.getUserId());
                if (privilegeModels == null) {
                    privilegeModels = privilegeMapper.getByUserId(privilege.getUserId());
                    caches.put("user_" + privilege.getUserId(), privilegeModels);
                }
                addPrivilegesInner(privilege, moduleList, privilegeModels);
            }
            // 添加用户组权限
            else if (privilege.getGroupId() > 0 && privilege.getRoleId() <= 0) {
                List<PrivilegeModel> privilegeModels = caches.get("group_" + privilege.getGroupId());
                if (privilegeModels == null) {
                    privilegeModels = privilegeMapper.getByGroupId(privilege.getGroupId());
                    caches.put("group_" + privilege.getGroupId(), privilegeModels);
                }
                addPrivilegesInner(privilege, moduleList, privilegeModels);
            }
            // 添加角色权限
            else if (privilege.getRoleId() > 0 && privilege.getGroupId() <= 0) {
                List<PrivilegeModel> privilegeModels = caches.get("role_" + privilege.getRoleId());
                if (privilegeModels == null) {
                    privilegeModels = privilegeMapper.getByRoleId(privilege.getRoleId());
                    caches.put("role_" + privilege.getRoleId(), privilegeModels);
                }
                addPrivilegesInner(privilege, moduleList, privilegeModels);
            }
        }
    }

    private void addPrivilegesInner(Privilege privilege, List<ModuleModel> modelList, List<PrivilegeModel> privilegeModels) {
        ModuleModel module = findModule(modelList, privilege.getModuleId());
        if (module == null)
            return ;

        if (privilegeModels == null)
            privilegeModels = new ArrayList<PrivilegeModel>();

        List<Integer> deletePrivIds = new ArrayList<Integer>();

        // 当前模块权限相同的话不用处理了，否则删除原配置信息
        for (int i = privilegeModels.size() - 1; i >= 0; i--) {
            PrivilegeModel privilegeModel = privilegeModels.get(i);
            if (isEqualPrivilege(privilege, privilegeModel)) {
                if (privilege.getGrantType() == privilegeModel.getGrantType())
                    return;
                deletePrivIds.add(privilegeModel.getId());
                privilegeModels.remove(i);
                break;
            }
        }

        // 删除权限相同的子模块
        List<ModuleModel> subModuleList = getSubModuleList(modelList, module.getId());
        for (ModuleModel moduleModel: subModuleList) {
            for (int i = privilegeModels.size() - 1; i >= 0; i--) {
                PrivilegeModel privilegeModel = privilegeModels.get(i);
                if (moduleModel.getId() != privilegeModel.getModuleId())
                    continue;
                if (privilege.getGrantType() != privilegeModel.getGrantType())
                    continue;
                if (isEqualPrivilege(privilege, privilegeModel)) {
                    deletePrivIds.add(privilegeModel.getId());
                    privilegeModels.remove(i);
                    break;
                }
            }
        }

        // 查找上级模块
        boolean isParentPrivSame = false;
        ModuleModel parentModule = findModule(modelList, module.getParentId());
        if (parentModule != null) {
            for (PrivilegeModel privilegeModel: privilegeModels) {
                if (privilegeModel.getModuleId() != parentModule.getId())
                    continue;
                if (isEqualPrivilege(privilege, privilegeModel)) {
                    if (privilege.getGrantType() == privilegeModel.getGrantType()) {
                        isParentPrivSame = true;
                        break;
                    }
                }
            }
        }

        if (deletePrivIds.size() > 0)
            privilegeMapper.deleteByIds(ArrayUtilsEx.toPrimitiveInt(deletePrivIds.toArray()));

        if (!isParentPrivSame) {
            PrivilegeModel privilegeModel = save(privilege);
            privilegeModels.add(privilegeModel);
        }
    }

    private ModuleModel findModule(List<ModuleModel> modelList, int moduleId) {
        if (moduleId > 0) {
            for (ModuleModel moduleModel: modelList) {
                if (moduleModel.getId() == moduleId)
                    return moduleModel;
            }
        }
        return null;
    }

    private List<ModuleModel> getSubModuleList(List<ModuleModel> modelList, int moduleId) {
        List<ModuleModel> moduleModels = new ArrayList<ModuleModel>();
        for (ModuleModel moduleModel: modelList) {
            if (moduleModel.getParentId() == moduleId) {
                moduleModels.add(moduleModel);
                moduleModels.addAll(getSubModuleList(modelList, moduleModel.getId()));
            }
        }
        return moduleModels;
    }

    private List<ModuleModel> getParentModuleList(List<ModuleModel> modelList, int moduleId) {
        List<ModuleModel> moduleModels = new ArrayList<ModuleModel>();
        for (ModuleModel moduleModel: modelList) {
            if (moduleModel.getId() == moduleId) {
                moduleModels.add(moduleModel);
                if (moduleModel.getParentId() > 0)
                    moduleModels.addAll(getParentModuleList(modelList, moduleModel.getParentId()));
            }
        }
        return moduleModels;
    }

    private boolean isEqualPrivilege(Privilege privilege, PrivilegeModel privilegeModel) {
        if (privilege.getUserId() > 0 || privilegeModel.getUserId() > 0)
            return privilege.getUserId() == privilegeModel.getUserId();
        else if (privilege.getGroupId() > 0 || privilegeModel.getGroupId() > 0)
            return privilege.getGroupId() == privilegeModel.getGroupId();
        else if (privilege.getRoleId() > 0 || privilegeModel.getRoleId() > 0)
            return privilege.getRoleId() == privilegeModel.getRoleId();
        return true;
    }

    private PrivilegeModel save(Privilege privilege) {
        PrivilegeModel privilegeModel = new PrivilegeModel();
        privilegeModel.setUserId(privilege.getUserId());
        privilegeModel.setGroupId(privilege.getGroupId());
        privilegeModel.setRoleId(privilege.getRoleId());
        privilegeModel.setModuleId(privilege.getModuleId());
        privilegeModel.setModuleId(privilege.getModuleId());
        privilegeModel.setState(Const.ENABLED);
        privilegeModel.setCreatorId(SessionManager.getUserId());
        privilegeModel.setCreateDate(new Date());
        privilegeModel.setPaasId(SessionManager.getAccountId());
        return privilegeModel;
    }

    @Override
    public void setUserPrivileges(int userId, List<Privilege> privileges) {
        if (userId > 0) {
            deleteByUserId(userId);
            if (privileges != null && privileges.size() > 0) {
                for (Privilege privilege: privileges) {
                    privilege.setUserId(userId);
                    privilege.setGroupId(0);
                    privilege.setRoleId(0);
                }
                addPrivileges(privileges);
            }
        }
    }

    @Override
    public void setGroupPrivileges(int groupId, List<Privilege> privileges) {
        if (groupId > 0) {
            deleteByGroupId(groupId);
            if (privileges != null && privileges.size() > 0) {
                for (Privilege privilege: privileges) {
                    privilege.setGroupId(groupId);
                    privilege.setUserId(0);
                    privilege.setRoleId(0);
                }
                addPrivileges(privileges);
            }
        }
    }

    @Override
    public void setRolePrivileges(int roleId, List<Privilege> privileges) {
        if (roleId > 0) {
            deleteByRoleId(roleId);
            if (privileges != null && privileges.size() > 0) {
                for (Privilege privilege: privileges) {
                    privilege.setRoleId(roleId);
                    privilege.setUserId(0);
                    privilege.setGroupId(0);
                }
                addPrivileges(privileges);
            }
        }
    }

    @Override
    public void deletePrivileges(Privilege privilege) {
        if (privilege != null) {
            if (privilege.getUserId() > 0)
                deleteByUserId(privilege.getUserId());
            else if (privilege.getGroupId() > 0 && privilege.getRoleId() <= 0)
                deleteByGroupId(privilege.getGroupId());
            else if (privilege.getRoleId() > 0 && privilege.getGroupId() <= 0)
                deleteByRoleId(privilege.getRoleId());
            else if (privilege.getGroupId() <= 0 && privilege.getRoleId() <= 0) {
                if (privilege.getModuleId() > 0)
                    deleteByModuleId(privilege.getModuleId());
            }
        }
    }

    @Override
    public void deletePrivileges(List<Privilege> privileges) {
        if (privileges != null && privileges.size() > 0) {
            for (Privilege privilege: privileges) {
                deletePrivileges(privilege);
            }
        }
    }

    @Override
    public void deleteByUserId(int userId) {
        UserModel userModel = userService.getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在：" + userId);
        privilegeMapper.deleteByUserId(userId);
    }

    @Override
    public void deleteByGroupId(int groupId) {
        GroupModel groupModel = groupService.getById(groupId);
        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户组不存在：" + groupId);
        privilegeMapper.deleteByGroupId(groupId);
    }

    @Override
    public void deleteByRoleId(int roleId) {
        RoleModel roleModel = roleService.getById(roleId);
        if (roleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "角色不存在：" + roleId);
        privilegeMapper.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByModuleId(int moduleId) {
        ModuleModel moduleModel = moduleService.getById(moduleId);
        if (moduleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "模块不存在：" + moduleId);
        privilegeMapper.deleteByModuleId(moduleId);
    }

    @Override
    public List<PrivilegeModel> checkByModuleIds(int userId, int[] moduleIds) {
        UserModel userModel = userService.getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在：" + userId);
        if (moduleIds == null || moduleIds.length == 0)
            return new ArrayList<PrivilegeModel>();
        return privilegeMapper.getUserPrivsByModuleIds(userId, moduleIds);
    }

    @Override
    public List<PrivilegeModel> checkByModuleCodes(int userId, String[] moduleCodes) {
        UserModel userModel = userService.getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在：" + userId);
        if (moduleCodes == null || moduleCodes.length == 0)
            return new ArrayList<PrivilegeModel>();
        return privilegeMapper.getUserPrivsByModuleCodes(userId, moduleCodes);
    }

    @Override
    public List<PrivilegeModel> checkByModuleNames(int userId, String[] moduleNames) {
        UserModel userModel = userService.getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在：" + userId);
        if (moduleNames == null || moduleNames.length == 0)
            return new ArrayList<PrivilegeModel>();
        return privilegeMapper.getUserPrivsByModuleNames(userId, moduleNames);
    }

    /**
     * 集中校验数据权限
     */
    private void validPrivileges(List<Privilege> privileges) {
        if (privileges == null || privileges.size() == 0)
            return ;
        Set<Integer> userIds = new HashSet<Integer>();
        Set<Integer> groupIds = new HashSet<Integer>();
        Set<Integer> roleIds = new HashSet<Integer>();
        Set<Integer> moduleIds = new HashSet<Integer>();
        for (Privilege privilege: privileges) {
            if (privilege.getUserId() > 0)
                userIds.add(privilege.getUserId());
            if (privilege.getGroupId() > 0)
                groupIds.add(privilege.getGroupId());
            if (privilege.getRoleId() > 0)
                roleIds.add(privilege.getRoleId());
            if (privilege.getModuleId() > 0)
                moduleIds.add(privilege.getModuleId());
        }

        if (userIds.size() > 0) {
            int[] user_ids = ArrayUtilsEx.toPrimitiveInt(userIds.toArray());
            List<UserModel> userModels = userService.getByIds(user_ids);
            for (int userId: user_ids) {
                if (ArrayUtilsEx.findObject(userModels, userId) == null)
                    throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在：" + userId);
            }
        }

        if (groupIds.size() > 0) {
            int[] group_ids = ArrayUtilsEx.toPrimitiveInt(groupIds.toArray());
            List<GroupModel> groupModels = groupService.getByIds(group_ids);
            for (int groupId: group_ids) {
                if (ArrayUtilsEx.findObject(groupModels, groupId) == null)
                    throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户组不存在：" + groupId);
            }
        }

        if (roleIds.size() > 0) {
            int[] role_ids = ArrayUtilsEx.toPrimitiveInt(roleIds.toArray());
            List<RoleModel> roleModels = roleService.getByIds(role_ids);
            for (int roleId: role_ids) {
                if (ArrayUtilsEx.findObject(roleModels, roleId) == null)
                    throw new ResultException(Const.MSG_CODE_NOTEXIST, "角色不存在：" + roleId);
            }
        }

        if (moduleIds.size() > 0) {
            int[] module_ids = ArrayUtilsEx.toPrimitiveInt(moduleIds.toArray());
            List<ModuleModel> moduleModels = moduleService.getByIds(module_ids);
            for (int moduleId: module_ids) {
                if (ArrayUtilsEx.findObject(moduleModels, moduleId) == null)
                    throw new ResultException(Const.MSG_CODE_NOTEXIST, "模块不存在：" + moduleId);
            }
        }
    }

}
