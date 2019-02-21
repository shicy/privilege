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
     * 获取账户信息
     * @param accountId 账户编号
     */
    public static Account getAccountInfo(int accountId) {
        HttpResult result = privilegeClient.getAccountInfo(accountId);
        return result.getData(Account.class);
    }

    /**
     * 查询账户信息
     * @param account 查询条件
     */
    public static Account[] findAccount(AccountForm account) {
        HttpResult result = privilegeClient.findAccount(account);
        return getResultDatas(result, new Account[0]);
    }

    /**
     * 添加账户
     * @param account 账户信息
     */
    public static Account addAccount(Account account) {
        HttpResult result = privilegeClient.addAccount(account);
        return result.getData(Account.class);
    }

    /**
     * 修改账户
     * @param account 账户信息
     */
    public static Account setAccount(Account account) {
        HttpResult result = privilegeClient.setAccount(account);
        return result.getData(Account.class);
    }

    /**
     * 删除账户
     * @param accountId 账户编号
     */
    public static Account deleteAccount(int accountId) {
        HttpResult result = privilegeClient.deleteAccount(accountId);
        return result.getData(Account.class);
    }

    /**
     * 重置账户密钥
     * @param accountId 账户编号
     */
    public static String makeAccountSecret(int accountId) {
        HttpResult result = privilegeClient.makeAccountSecret(accountId);
        return result.getData(String.class);
    }

    /**
     * 修改账户状态
     * @param accountId 账户编号
     * @param state 新状态
     */
    public static short setAccountState(int accountId, short state) {
        HttpResult result = privilegeClient.setAccountState(accountId, state);
        return result.getData(Short.class);
    }

    /**
     * 查询分组
     * @param group 查询条件
     */
    public static Group[] findGroup(GroupForm group) {
        HttpResult result = privilegeClient.findGroup(group);
        return getResultDatas(result, new Group[0]);
    }

    /**
     * 获取用户的所属分组
     * @param userId 用户编号
     */
    public static Group[] getUserGroups(int userId) {
        HttpResult result = privilegeClient.getUserGroups(userId);
        return getResultDatas(result, new Group[0]);
    }

    /**
     * 添加分组
     * @param group 分组信息
     */
    public static Group addGroup(Group group) {
        HttpResult result = privilegeClient.addGroup(group);
        return result.getData(Group.class);
    }

    /**
     * 修改分组
     * @param group 分组信息
     */
    public static Group setGroup(Group group) {
        HttpResult result = privilegeClient.setGroup(group);
        return result.getData(Group.class);
    }

    /**
     * 删除分组
     * @param groupId 分组编号
     */
    public static Group deleteGroup(int groupId) {
        HttpResult result = privilegeClient.deleteGroup(groupId);
        return result.getData(Group.class);
    }

    /**
     * 将用户添加到分组
     * @param groupId 所属分组编号
     * @param userId 用户编号
     */
    public static int addGroupUser(int groupId, int userId) {
        return addGroupUser(groupId, "" + userId);
    }

    /**
     * 将一个（或多个）用户添加到分组
     * @param groupId 所属分组编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    public static int addGroupUser(int groupId, String userIds) {
        HttpResult result = privilegeClient.addGroupUser(groupId, userIds);
        return result.getData(Integer.class);
    }

    /**
     * 将一个（或多个）用户添加到分组
     * @param groupId 所属分组编号
     * @param userIds 用户编号集
     */
    public static int addGroupUser(int groupId, int[] userIds) {
        return addGroupUser(groupId, ArrayUtilsEx.join(userIds, ","));
    }

    /**
     * 将用户从分组中移除
     * @param groupId 所属分组编号
     * @param userId 用户编号
     * @return
     */
    public static int deleteGroupUser(int groupId, int userId) {
        return deleteGroupUser(groupId, "" + userId);
    }

    /**
     * 将一个（或多个）用户从分组中移除
     * @param groupId 所属分组编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    public static int deleteGroupUser(int groupId, String userIds) {
        HttpResult result = privilegeClient.deleteGroupUser(groupId, userIds);
        return result.getData(Integer.class);
    }

    /**
     * 将一个（或多个）用户从分组中移除
     * @param groupId 所属分组编号
     * @param userIds 用户编号集
     */
    public static int deleteGroupUser(int groupId, int[] userIds) {
        return deleteGroupUser(groupId, ArrayUtilsEx.join(userIds, ","));
    }

    /**
     * 将多有用户从分组中移除
     * @param groupId 所属分组编号
     */
    public static int deleteAllGroupUsers(int groupId) {
        HttpResult result = privilegeClient.deleteAllGroupUsers(groupId);
        return result.getData(Integer.class);
    }

    /**
     * 查询角色
     * @param role 查询条件
     */
    public static Role[] findRole(RoleForm role) {
        HttpResult result = privilegeClient.findRole(role);
        return getResultDatas(result, new Role[0]);
    }

    /**
     * 获取用户角色
     * @param userId 用户编号
     */
    public static Role[] getUserRoles(int userId) {
        HttpResult result = privilegeClient.getUserRoles(userId);
        return getResultDatas(result, new Role[0]);
    }

    /**
     * 添加角色
     * @param role 角色信息
     */
    public static Role addRole(Role role) {
        HttpResult result = privilegeClient.addRole(role);
        return result.getData(Role.class);
    }

    /**
     * 修改角色
     * @param role 角色信息
     */
    public static Role setRole(Role role) {
        HttpResult result = privilegeClient.setRole(role);
        return result.getData(Role.class);
    }

    /**
     * 删除角色
     * @param roleId 角色编号
     */
    public static Role deleteRole(int roleId) {
        HttpResult result = privilegeClient.deleteRole(roleId);
        return result.getData(Role.class);
    }

    /**
     * 将用户赋予该角色
     * @param roleId 角色编号
     * @param userId 用户编号
     */
    public static int addRoleUser(int roleId, int userId) {
        return addRoleUser(roleId, "" + userId);
    }

    /**
     * 将一个（或多个）用户赋予该角色
     * @param roleId 角色编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    public static int addRoleUser(int roleId, String userIds) {
        HttpResult result = privilegeClient.addRoleUser(roleId, userIds);
        return result.getData(Integer.class);
    }

    /**
     * 将一个（或多个）用户赋予该角色
     * @param roleId 角色编号
     * @param userIds 用户编号集
     */
    public static int addRoleUser(int roleId, int[] userIds) {
        return addRoleUser(roleId, ArrayUtilsEx.join(userIds, ","));
    }

    /**
     * 将用户移除该角色
     * @param roleId 角色编号
     * @param userId 用户编号
     */
    public static int deleteRoleUser(int roleId, int userId) {
        return deleteRoleUser(roleId, "" + userId);
    }

    /**
     * 将一个（或多个）用户移除该角色
     * @param roleId 角色编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    public static int deleteRoleUser(int roleId, String userIds) {
        HttpResult result = privilegeClient.deleteRoleUser(roleId, userIds);
        return result.getData(Integer.class);
    }

    /**
     * 将一个（或多个）用户移除该角色
     * @param roleId 角色编号
     * @param userIds 用户编号集
     */
    public static int deleteRoleUser(int roleId, int[] userIds) {
        return deleteRoleUser(roleId, ArrayUtilsEx.join(userIds, ","));
    }

    /**
     * 移除该角色的所有用户信息
     * @param roleId 角色编号
     */
    public static int deleteAllRoleUsers(int roleId) {
        HttpResult result = privilegeClient.deleteAllRoleUsers(roleId);
        return result.getData(Integer.class);
    }

    /**
     * 查询用户
     * @param user 查询条件
     */
    public static User[] findUser(UserForm user) {
        HttpResult result = privilegeClient.findUser(user);
        return getResultDatas(result, new User[0]);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @param groupId 将用户添加到该分组
     * @param roleId 赋予用户该角色
     */
    public static User addUser(User user, int groupId, int roleId) {
        String _groupIds = groupId > 0 ? ("" + groupId) : null;
        String _roleIds = roleId > 0 ? ("" + roleId) : null;
        return addUser(user, _groupIds, _roleIds);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @param groupIds 添加用户分组，多个分组逗号分隔
     * @param roleIds 赋予用户角色，多个角色逗号分隔
     */
    public static User addUser(User user, String groupIds, String roleIds) {
        HttpResult result = privilegeClient.addUser(user, groupIds, roleIds);
        return result.getData(User.class);
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @param groupIds 添加用户分组
     * @param roleIds 赋予用户角色
     */
    public static User addUser(User user, int[] groupIds, int[] roleIds) {
        String _groupIds = groupIds != null ? ArrayUtilsEx.join(groupIds, ",") : null;
        String _roleIds = roleIds != null ? ArrayUtilsEx.join(roleIds, ",") : null;
        return addUser(user, _groupIds, _roleIds);
    }

    /**
     * 修改用户
     * @param user 用户信息
     */
    public static User setUser(User user) {
        HttpResult result = privilegeClient.setUser(user);
        return result.getData(User.class);
    }

    /**
     * 删除用户
     * @param userId 用户编号
     */
    public static User deleteUser(int userId) {
        HttpResult result = privilegeClient.deleteUser(userId);
        return result.getData(User.class);
    }

    /**
     * 修改用户密码
     * @param userId 用户编号
     * @param password 新密码
     * @param oldPassword 旧密码
     */
    public static boolean setUserPassword(int userId, String password, String oldPassword) {
        HttpResult result = privilegeClient.setUserPassword(userId, password, oldPassword);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 修改用户状态
     * @param userId 用户编号
     * @param state 新状态
     */
    public static short setUserState(int userId, short state) {
        HttpResult result = privilegeClient.setUserState(userId, state);
        return result.getData(Short.class);
    }

    /**
     * 设置用户分组（将删除用户原分组）
     * @param userId 用户编号
     * @param groupIds 新的分组编号集
     */
    public static Group[] setUserGroups(int userId, int[] groupIds) {
        String _groupIds = groupIds != null ? ArrayUtilsEx.join(groupIds, ",") : null;
        return setUserGroups(userId, _groupIds);
    }

    /**
     * 设置用户分组（将删除用户原分组）
     * @param userId 用户编号
     * @param groupIds 新的分组编号，多个值逗号分隔
     */
    public static Group[] setUserGroups(int userId, String groupIds) {
        HttpResult result = privilegeClient.setUserGroups(userId, groupIds);
        return getResultDatas(result, new Group[0]);
    }

    /**
     * 设置用户角色（将删除用户原角色）
     * @param userId 用户编号
     * @param roleIds 新的角色编号集
     */
    public static Role[] setUserRoles(int userId, int[] roleIds) {
        String _roleIds = roleIds != null ? ArrayUtilsEx.join(roleIds, ",") : null;
        return setUserRoles(userId, _roleIds);
    }

    /**
     * 设置用户角色（将删除用户原角色）
     * @param userId 用户编号
     * @param roleIds 新的角色编号，多个值逗号分隔
     */
    public static Role[] setUserRoles(int userId, String roleIds) {
        HttpResult result = privilegeClient.setUserRoles(userId, roleIds);
        return getResultDatas(result, new Role[0]);
    }

    /**
     * 查询模块
     * @param module 查询条件
     */
    public static Module[] findModule(ModuleForm module) {
        HttpResult result = privilegeClient.findModule(module);
        return getResultDatas(result, new Module[0]);
    }

    /**
     * 获取用户模块信息
     * @param userId 模块编号
     */
    public static Module[] getUserModules(int userId) {
        HttpResult result = privilegeClient.getUserModules(userId);
        return getResultDatas(result, new Module[0]);
    }

    /**
     * 添加模块
     * @param module 模块信息
     */
    public static Module addModule(Module module) {
        HttpResult result = privilegeClient.addModule(module);
        return  result.getData(Module.class);
    }

    /**
     * 修改模块
     * @param module 模块信息
     */
    public static Module setModule(Module module) {
        HttpResult result = privilegeClient.setModule(module);
        return result.getData(Module.class);
    }

    /**
     * 删除模块
     * @param moduleId 模块编号
     * @param force 是否强制删除，如果存在子模块需强制才能删除
     */
    public static Module deleteModule(int moduleId, boolean force) {
        HttpResult result = privilegeClient.deleteModule(moduleId, force ? "1" : "0");
        return result.getData(Module.class);
    }

    /**
     * 获取用户配置的授权信息
     * @param userId 用户编号
     */
    public static Privilege[] getUserPrivileges(int userId) {
        HttpResult result = privilegeClient.getUserPrivileges(userId);
        return getResultDatas(result, new Privilege[0]);
    }

    /**
     * 获取用户所有授权信息，包括该用户的分组、角色的授权信息（包含子模块）
     * @param userId 用户编号
     */
    public static Privilege[] getUserPrivilegesAll(int userId) {
        HttpResult result = privilegeClient.getUserPrivilegesAll(userId);
        return getResultDatas(result, new Privilege[0]);
    }

    /**
     * 获取分组配置的授权信息
     * @param groupId 分组编号
     */
    public static Privilege[] getGroupPrivileges(int groupId) {
        HttpResult result = privilegeClient.getGroupPrivileges(groupId);
        return getResultDatas(result, new Privilege[0]);
    }

    /**
     * 获取角色配置的授权信息
     * @param roleId 角色编号
     */
    public static Privilege[] getRolePrivileges(int roleId) {
        HttpResult result = privilegeClient.getRolePrivileges(roleId);
        return getResultDatas(result, new Privilege[0]);
    }

    /**
     * 添加授权
     * @param privilege 授权信息
     */
    public static boolean addPrivilege(Privilege privilege) {
        HttpResult result = privilegeClient.addPrivilege(privilege);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 批量添加授权
     * @param privileges 授权信息集
     */
    public static boolean addPrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.addPrivileges(items);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 删除授权信息
     * @param privilege 删除条件
     */
    public static boolean deletePrivileges(Privilege privilege) {
        HttpResult result = privilegeClient.deletePrivileges(privilege);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 批量删除授权信息
     * @param privileges 删除条件
     */
    public static boolean deletePrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.deletePrivileges(items);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 用户授权
     * @param userId 用户编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static boolean setUserPrivilege(int userId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setUserId(userId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setUserPrivilege(userId, privilege);
    }

    /**
     * 用户授权
     * @param userId 用户编号
     * @param privilege 授权信息
     */
    public static boolean setUserPrivilege(int userId, Privilege privilege) {
        return setUserPrivileges(userId, new Privilege[]{privilege});
    }

    /**
     * 用户批量授权
     * @param userId 用户编号
     * @param privileges 授权信息
     */
    public static boolean setUserPrivileges(int userId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setUserPrivileges(userId, items);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static boolean setGroupPrivilege(int groupId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setGroupId(groupId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setGroupPrivilege(groupId, privilege);
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param privilege 授权信息
     */
    public static boolean setGroupPrivilege(int groupId, Privilege privilege) {
        return setGroupPrivileges(groupId, new Privilege[]{privilege});
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param privileges 授权信息
     */
    public static boolean setGroupPrivileges(int groupId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setGroupPrivileges(groupId, items);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static boolean setRolePrivilege(int roleId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setRoleId(roleId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        return setRolePrivilege(roleId, privilege);
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param privilege 授权信息
     */
    public static boolean setRolePrivilege(int roleId, Privilege privilege) {
        return setRolePrivileges(roleId, new Privilege[]{privilege});
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param privileges 授权信息
     */
    public static boolean setRolePrivileges(int roleId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setRolePrivileges(roleId, items);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 检查用户的授权信息
     * @param userId 用户编号
     * @param moduleIds 模块编号（逗号分隔）
     * @param moduleCodes 模块编码（逗号分隔）
     * @param moduleNames 模块名称（逗号分隔）
     * @return 返回已授权信息
     */
    public static Privilege[] checkUserModules(int userId, String moduleIds, String moduleCodes, String moduleNames) {
        HttpResult result = privilegeClient.checkUserModules(userId, moduleIds, moduleCodes, moduleNames);
        return getResultDatas(result, new Privilege[0]);
    }

    /**
     * 检查用户的授权信息
     * @param userId 用户编号
     * @param moduleIds 模块编号集
     * @param moduleCodes 模块编码集
     * @param moduleNames 模块名称集
     * @return 返回已授权信息
     */
    public static Privilege[] checkUserModules(int userId, String[] moduleIds, String[] moduleCodes, String[] moduleNames) {
        return checkUserModules(userId, ArrayUtilsEx.join(moduleIds, ","),
                ArrayUtilsEx.join(moduleCodes, ","), ArrayUtilsEx.join(moduleNames, ","));
    }

    /**
     * 获取用户某个模块的授权值
     * @param userId 用户编号
     * @param moduleCode 模块编码
     */
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
