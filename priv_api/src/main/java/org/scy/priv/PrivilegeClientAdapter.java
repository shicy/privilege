package org.scy.priv;

import com.alibaba.fastjson.JSON;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.form.*;
import org.scy.priv.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 授权服务客户端适配器
 * Created by shicy on 2017/11/13
 */
@Component
@SuppressWarnings("unused")
public class PrivilegeClientAdapter {

    private static PrivilegeClient privilegeClient;

    @Autowired
    private PrivilegeClient privilegeClientTemp;

    @PostConstruct
    public void init() {
        privilegeClient = privilegeClientTemp;
    }

    /**
     * 获取帐户信息
     */
    public static Account getAccountInfo(int accountId) {
        HttpResult result = privilegeClient.getAccountInfo(accountId);
        return result.getData(Account.class);
    }

    public static Account[] findAccount(AccountForm account) {
        HttpResult result = privilegeClient.findAccount(account);
        return getResultDatas(result, new Account[0]);
    }

    public static Account addAccount(Account account) {
        HttpResult result = privilegeClient.addAccount(account);
        return result.getData(Account.class);
    }

    public static Account setAccount(Account account) {
        HttpResult result = privilegeClient.setAccount(account);
        return result.getData(Account.class);
    }

    public static Account deleteAccount(int accountId) {
        HttpResult result = privilegeClient.deleteAccount(accountId);
        return result.getData(Account.class);
    }

    public static String makeAccountSecret(int accountId) {
        HttpResult result = privilegeClient.makeAccountSecret(accountId);
        return result.getData(String.class);
    }

    public static short setAccountState(int accountId, short state) {
        HttpResult result = privilegeClient.setAccountState(accountId, state);
        return result.getData(Short.class);
    }

    public static Group[] findGroup(GroupForm group) {
        HttpResult result = privilegeClient.findGroup(group);
        return getResultDatas(result, new Group[0]);
    }

    public static Group[] getUserGroups(int userId) {
        HttpResult result = privilegeClient.getUserGroups(userId);
        return getResultDatas(result, new Group[0]);
    }

    public static Group addGroup(Group group) {
        HttpResult result = privilegeClient.addGroup(group);
        return result.getData(Group.class);
    }

    public static Group setGroup(Group group) {
        HttpResult result = privilegeClient.setGroup(group);
        return result.getData(Group.class);
    }

    public static Group deleteGroup(int groupId) {
        HttpResult result = privilegeClient.deleteGroup(groupId);
        return result.getData(Group.class);
    }

    public static int addGroupUser(int groupId, int userId) {
        return addGroupUser(groupId, "" + userId);
    }

    public static int addGroupUser(int groupId, String userIds) {
        HttpResult result = privilegeClient.addGroupUser(groupId, userIds);
        return result.getData(Integer.class);
    }

    public static int addGroupUser(int groupId, int[] userIds) {
        return addGroupUser(groupId, ArrayUtilsEx.join(userIds, ","));
    }

    public static int deleteGroupUser(int groupId, int userId) {
        return deleteGroupUser(groupId, "" + userId);
    }

    public static int deleteGroupUser(int groupId, String userIds) {
        HttpResult result = privilegeClient.deleteGroupUser(groupId, userIds);
        return result.getData(Integer.class);
    }

    public static int deleteGroupUser(int groupId, int[] userIds) {
        return deleteGroupUser(groupId, ArrayUtilsEx.join(userIds, ","));
    }

    public static int deleteAllGroupUsers(int groupId) {
        HttpResult result = privilegeClient.deleteAllGroupUsers(groupId);
        return result.getData(Integer.class);
    }

    public static Role[] findRole(RoleForm role) {
        HttpResult result = privilegeClient.findRole(role);
        return getResultDatas(result, new Role[0]);
    }

    public static Role[] getUserRoles(int userId) {
        HttpResult result = privilegeClient.getUserRoles(userId);
        return getResultDatas(result, new Role[0]);
    }

    public static Role addRole(Role role) {
        HttpResult result = privilegeClient.addRole(role);
        return result.getData(Role.class);
    }

    public static Role setRole(Role role) {
        HttpResult result = privilegeClient.setRole(role);
        return result.getData(Role.class);
    }

    public static Role deleteRole(int roleId) {
        HttpResult result = privilegeClient.deleteRole(roleId);
        return result.getData(Role.class);
    }

    public static int addRoleUser(int roleId, int userId) {
        return addRoleUser(roleId, "" + userId);
    }

    public static int addRoleUser(int roleId, String userIds) {
        HttpResult result = privilegeClient.addRoleUser(roleId, userIds);
        return result.getData(Integer.class);
    }

    public static int addRoleUser(int roleId, int[] userIds) {
        return addRoleUser(roleId, ArrayUtilsEx.join(userIds, ","));
    }

    public static int deleteRoleUser(int roleId, int userId) {
        return deleteRoleUser(roleId, "" + userId);
    }

    public static int deleteRoleUser(int roleId, String userIds) {
        HttpResult result = privilegeClient.deleteRoleUser(roleId, userIds);
        return result.getData(Integer.class);
    }

    public static int deleteRoleUser(int roleId, int[] userIds) {
        return deleteRoleUser(roleId, ArrayUtilsEx.join(userIds, ","));
    }

    public static int deleteAllRoleUsers(int roleId) {
        HttpResult result = privilegeClient.deleteAllRoleUsers(roleId);
        return result.getData(Integer.class);
    }

    public static User[] findUser(UserForm user) {
        HttpResult result = privilegeClient.findUser(user);
        return getResultDatas(result, new User[0]);
    }

    public static User addUser(User user, int groupId, int roleId) {
        String _groupIds = groupId > 0 ? ("" + groupId) : null;
        String _roleIds = roleId > 0 ? ("" + roleId) : null;
        return addUser(user, _groupIds, _roleIds);
    }

    public static User addUser(User user, String groupIds, String roleIds) {
        HttpResult result = privilegeClient.addUser(user, groupIds, roleIds);
        return result.getData(User.class);
    }

    public static User addUser(User user, int[] groupIds, int[] roleIds) {
        String _groupIds = groupIds != null ? ArrayUtilsEx.join(groupIds, ",") : null;
        String _roleIds = roleIds != null ? ArrayUtilsEx.join(roleIds, ",") : null;
        return addUser(user, _groupIds, _roleIds);
    }

    public static User setUser(User user) {
        HttpResult result = privilegeClient.setUser(user);
        return result.getData(User.class);
    }

    public static User deleteUser(int userId) {
        HttpResult result = privilegeClient.deleteUser(userId);
        return result.getData(User.class);
    }

    public static boolean setUserPassword(int userId, String password, String oldPassword) {
        HttpResult result = privilegeClient.setUserPassword(userId, password, oldPassword);
        return result.getCode() == HttpResult.OK;
    }

    public static short setUserState(int userId, short state) {
        HttpResult result = privilegeClient.setUserState(userId, state);
        return result.getData(Short.class);
    }

    public static Group[] setUserGroups(int userId, int[] groupIds) {
        String _groupIds = groupIds != null ? ArrayUtilsEx.join(groupIds, ",") : null;
        return setUserGroups(userId, _groupIds);
    }

    public static Group[] setUserGroups(int userId, String groupIds) {
        HttpResult result = privilegeClient.setUserGroups(userId, groupIds);
        return getResultDatas(result, new Group[0]);
    }

    public static Role[] setUserRoles(int userId, int[] roleIds) {
        String _roleIds = roleIds != null ? ArrayUtilsEx.join(roleIds, ",") : null;
        return setUserRoles(userId, _roleIds);
    }

    public static Role[] setUserRoles(int userId, String roleIds) {
        HttpResult result = privilegeClient.setUserRoles(userId, roleIds);
        return getResultDatas(result, new Role[0]);
    }

    public static Module[] findModule(ModuleForm module) {
        HttpResult result = privilegeClient.findModule(module);
        return getResultDatas(result, new Module[0]);
    }

    public static Module addModule(Module module) {
        HttpResult result = privilegeClient.addModule(module);
        return  result.getData(Module.class);
    }

    public static Module setModule(Module module) {
        HttpResult result = privilegeClient.setModule(module);
        return result.getData(Module.class);
    }

    public static Module deleteModule(int moduleId, boolean force) {
        HttpResult result = privilegeClient.deleteModule(moduleId, force ? "1" : "0");
        return result.getData(Module.class);
    }

    public static Privilege[] getUserPrivileges(int userId) {
        HttpResult result = privilegeClient.getUserPrivileges(userId);
        return getResultDatas(result, new Privilege[0]);
    }

    public static Privilege[] getUserPrivilegesAll(int userId) {
        HttpResult result = privilegeClient.getUserPrivilegesAll(userId);
        return getResultDatas(result, new Privilege[0]);
    }

    public static Privilege[] getGroupPrivileges(int groupId) {
        HttpResult result = privilegeClient.getGroupPrivileges(groupId);
        return getResultDatas(result, new Privilege[0]);
    }

    public static Privilege[] getRolePrivileges(int roleId) {
        HttpResult result = privilegeClient.getRolePrivileges(roleId);
        return getResultDatas(result, new Privilege[0]);
    }

    public static boolean addPrivilege(Privilege privilege) {
        HttpResult result = privilegeClient.addPrivilege(privilege);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean addPrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.addPrivileges(items);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean deletePrivileges(Privilege privilege) {
        HttpResult result = privilegeClient.deletePrivileges(privilege);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean deletePrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.deletePrivileges(items);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean setUserPrivilege(int userId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setUserId(userId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setUserPrivilege(userId, privilege);
    }

    public static boolean setUserPrivilege(int userId, Privilege privilege) {
        return setUserPrivileges(userId, new Privilege[]{privilege});
    }

    public static boolean setUserPrivileges(int userId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setUserPrivileges(userId, items);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean setGroupPrivilege(int groupId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setGroupId(groupId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setGroupPrivilege(groupId, privilege);
    }

    public static boolean setGroupPrivilege(int groupId, Privilege privilege) {
        return setGroupPrivileges(groupId, new Privilege[]{privilege});
    }

    public static boolean setGroupPrivileges(int groupId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setGroupPrivileges(groupId, items);
        return result.getCode() == HttpResult.OK;
    }

    public static boolean setRolePrivilege(int roleId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setRoleId(roleId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setRolePrivilege(roleId, privilege);
    }

    public static boolean setRolePrivilege(int roleId, Privilege privilege) {
        return setRolePrivileges(roleId, new Privilege[]{privilege});
    }

    public static boolean setRolePrivileges(int roleId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setRolePrivileges(roleId, items);
        return result.getCode() == HttpResult.OK;
    }

    public static Privilege[] checkUserModules(int userId, String moduleIds, String moduleCodes, String moduleNames) {
        HttpResult result = privilegeClient.checkUserModules(userId, moduleIds, moduleCodes, moduleNames);
        return getResultDatas(result, new Privilege[0]);
    }

    public static Privilege[] checkUserModules(int userId, String[] moduleIds, String[] moduleCodes, String[] moduleNames) {
        return checkUserModules(userId, ArrayUtilsEx.join(moduleIds, ","),
                ArrayUtilsEx.join(moduleCodes, ","), ArrayUtilsEx.join(moduleNames, ","));
    }

    public static int getUserGrantType(int userId, String moduleCode) {
        HttpResult result = privilegeClient.getUserGrantType(userId, moduleCode);
        return result.getData(Integer.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] getResultDatas (HttpResult result, T[] t) {
        Object resultData = result.getData();
        if (resultData != null) {
            try {
                List<T> accounts = (List<T>)resultData;
                return accounts.toArray(t);
            }
            catch (Exception e) {
                //
            }
        }
        return null;
    }

}
