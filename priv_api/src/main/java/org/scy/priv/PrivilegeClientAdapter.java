package org.scy.priv;

import com.alibaba.fastjson.JSON;
import org.scy.common.exception.ResultException;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.form.*;
import org.scy.priv.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
        checkResult(result);
        return result.getData(Account.class);
    }

    /**
     * 查询账户信息
     * @param account 查询条件
     */
    public static Account[] findAccount(AccountForm account) {
        HttpResult result = privilegeClient.findAccount(account);
        checkResult(result);
        return result.getDatas(Account.class);
    }

    /**
     * 添加账户
     * @param account 账户信息
     */
    public static Account addAccount(Account account) {
        HttpResult result = privilegeClient.addAccount(account);
        checkResult(result);
        return result.getData(Account.class);
    }

    /**
     * 修改账户
     * @param account 账户信息
     */
    public static Account setAccount(Account account) {
        HttpResult result = privilegeClient.setAccount(account);
        checkResult(result);
        return result.getData(Account.class);
    }

    /**
     * 删除账户
     * @param accountId 账户编号
     */
    public static Account deleteAccount(int accountId) {
        HttpResult result = privilegeClient.deleteAccount(accountId);
        checkResult(result);
        return result.getData(Account.class);
    }

    /**
     * 重置账户密钥
     * @param accountId 账户编号
     */
    public static String makeAccountSecret(int accountId) {
        HttpResult result = privilegeClient.makeAccountSecret(accountId);
        checkResult(result);
        return result.getData(String.class);
    }

    /**
     * 修改账户状态
     * @param accountId 账户编号
     * @param state 新状态
     */
    public static short setAccountState(int accountId, short state) {
        HttpResult result = privilegeClient.setAccountState(accountId, state);
        checkResult(result);
        return result.getData(Short.class);
    }

    /**
     * 查询分组
     * @param group 查询条件
     */
    public static Group[] findGroup(GroupForm group) {
        HttpResult result = privilegeClient.findGroup(group);
        checkResult(result);
        return result.getDatas(Group.class);
    }

    /**
     * 获取用户的所属分组
     * @param userId 用户编号
     */
    public static Group[] getUserGroups(int userId) {
        HttpResult result = privilegeClient.getUserGroups(userId);
        checkResult(result);
        return result.getDatas(Group.class);
    }

    /**
     * 添加分组
     * @param group 分组信息
     */
    public static Group addGroup(Group group) {
        HttpResult result = privilegeClient.addGroup(group);
        checkResult(result);
        return result.getData(Group.class);
    }

    /**
     * 修改分组
     * @param group 分组信息
     */
    public static Group setGroup(Group group) {
        HttpResult result = privilegeClient.setGroup(group);
        checkResult(result);
        return result.getData(Group.class);
    }

    /**
     * 删除分组
     * @param groupId 分组编号
     */
    public static Group deleteGroup(int groupId) {
        HttpResult result = privilegeClient.deleteGroup(groupId);
        checkResult(result);
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
        checkResult(result);
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
        checkResult(result);
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
        checkResult(result);
        return result.getData(Integer.class);
    }

    /**
     * 查询角色
     * @param role 查询条件
     */
    public static Role[] findRole(RoleForm role) {
        HttpResult result = privilegeClient.findRole(role);
        checkResult(result);
        return result.getDatas(Role.class);
    }

    /**
     * 获取用户角色
     * @param userId 用户编号
     */
    public static Role[] getUserRoles(int userId) {
        HttpResult result = privilegeClient.getUserRoles(userId);
        checkResult(result);
        return result.getDatas(Role.class);
    }

    /**
     * 添加角色
     * @param role 角色信息
     */
    public static Role addRole(Role role) {
        HttpResult result = privilegeClient.addRole(role);
        checkResult(result);
        return result.getData(Role.class);
    }

    /**
     * 修改角色
     * @param role 角色信息
     */
    public static Role setRole(Role role) {
        HttpResult result = privilegeClient.setRole(role);
        checkResult(result);
        return result.getData(Role.class);
    }

    /**
     * 删除角色
     * @param roleId 角色编号
     */
    public static Role deleteRole(int roleId) {
        HttpResult result = privilegeClient.deleteRole(roleId);
        checkResult(result);
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
        checkResult(result);
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
        checkResult(result);
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
        checkResult(result);
        return result.getData(Integer.class);
    }

    /**
     * 查询用户
     * @param user 查询条件
     */
    public static User[] findUser(UserForm user) {
        HttpResult result = privilegeClient.findUser(user);
        checkResult(result);
        return result.getDatas(User.class);
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
        checkResult(result);
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
        checkResult(result);
        return result.getData(User.class);
    }

    /**
     * 删除用户
     * @param userId 用户编号
     */
    public static User deleteUser(int userId) {
        HttpResult result = privilegeClient.deleteUser(userId);
        checkResult(result);
        return result.getData(User.class);
    }

    /**
     * 修改用户密码
     * @param userId 用户编号
     * @param password 新密码
     * @param oldPassword 旧密码
     */
    public static void setUserPassword(int userId, String password, String oldPassword) {
        HttpResult result = privilegeClient.setUserPassword(userId, password, oldPassword);
        checkResult(result);
    }

    /**
     * 修改用户状态
     * @param userId 用户编号
     * @param state 新状态
     */
    public static short setUserState(int userId, short state) {
        HttpResult result = privilegeClient.setUserState(userId, state);
        checkResult(result);
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
        checkResult(result);
        return result.getDatas(Group.class);
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
        checkResult(result);
        return result.getDatas(Role.class);
    }

    /**
     * 查询模块
     * @param module 查询条件
     */
    public static Module[] findModule(ModuleForm module) {
        HttpResult result = privilegeClient.findModule(module);
        checkResult(result);
        return result.getDatas(Module.class);
    }

    /**
     * 获取用户模块信息
     * @param userId 模块编号
     */
    public static Module[] getUserModules(int userId) {
        HttpResult result = privilegeClient.getUserModules(userId);
        checkResult(result);
        return result.getDatas(Module.class);
    }

    /**
     * 添加模块
     * @param module 模块信息
     */
    public static Module addModule(Module module) {
        HttpResult result = privilegeClient.addModule(module);
        checkResult(result);
        return  result.getData(Module.class);
    }

    /**
     * 修改模块
     * @param module 模块信息
     */
    public static Module setModule(Module module) {
        HttpResult result = privilegeClient.setModule(module);
        checkResult(result);
        return result.getData(Module.class);
    }

    /**
     * 删除模块
     * @param moduleId 模块编号
     * @param force 是否强制删除，如果存在子模块需强制才能删除
     */
    public static Module deleteModule(int moduleId, boolean force) {
        HttpResult result = privilegeClient.deleteModule(moduleId, force ? "1" : "0");
        checkResult(result);
        return result.getData(Module.class);
    }

    /**
     * 获取用户配置的授权信息
     * @param userId 用户编号
     */
    public static Privilege[] getUserPrivileges(int userId) {
        HttpResult result = privilegeClient.getUserPrivileges(userId);
        checkResult(result);
        return result.getDatas(Privilege.class);
    }

    /**
     * 获取用户所有授权信息，包括该用户的分组、角色的授权信息（包含子模块）
     * @param userId 用户编号
     */
    public static Privilege[] getUserPrivilegesAll(int userId) {
        HttpResult result = privilegeClient.getUserPrivilegesAll(userId);
        checkResult(result);
        return result.getDatas(Privilege.class);
    }

    /**
     * 获取分组配置的授权信息
     * @param groupId 分组编号
     */
    public static Privilege[] getGroupPrivileges(int groupId) {
        HttpResult result = privilegeClient.getGroupPrivileges(groupId);
        checkResult(result);
        return result.getDatas(Privilege.class);
    }

    /**
     * 获取角色配置的授权信息
     * @param roleId 角色编号
     */
    public static Privilege[] getRolePrivileges(int roleId) {
        HttpResult result = privilegeClient.getRolePrivileges(roleId);
        checkResult(result);
        return result.getDatas(Privilege.class);
    }

    /**
     * 添加授权
     * @param privilege 授权信息
     */
    public static boolean addPrivilege(Privilege privilege) {
        HttpResult result = privilegeClient.addPrivilege(privilege);
        checkResult(result);
        return result.getCode() == HttpResult.OK;
    }

    /**
     * 批量添加授权
     * @param privileges 授权信息集
     */
    public static void addPrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.addPrivileges(items);
        checkResult(result);
    }

    /**
     * 删除授权信息
     * @param privilege 删除条件
     */
    public static void deletePrivileges(Privilege privilege) {
        HttpResult result = privilegeClient.deletePrivileges(privilege);
        checkResult(result);
    }

    /**
     * 批量删除授权信息
     * @param privileges 删除条件
     */
    public static void deletePrivileges(Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.deletePrivileges(items);
        checkResult(result);
    }

    /**
     * 用户授权
     * @param userId 用户编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static void setUserPrivilege(int userId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setUserId(userId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        setUserPrivilege(userId, privilege);
    }

    /**
     * 用户授权
     * @param userId 用户编号
     * @param privilege 授权信息
     */
    public static void setUserPrivilege(int userId, Privilege privilege) {
        setUserPrivileges(userId, new Privilege[]{privilege});
    }

    /**
     * 用户批量授权
     * @param userId 用户编号
     * @param privileges 授权信息
     */
    public static void setUserPrivileges(int userId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setUserPrivileges(userId, items);
        checkResult(result);
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static void setGroupPrivilege(int groupId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setGroupId(groupId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        setGroupPrivilege(groupId, privilege);
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param privilege 授权信息
     */
    public static void setGroupPrivilege(int groupId, Privilege privilege) {
        setGroupPrivileges(groupId, new Privilege[]{privilege});
    }

    /**
     * 分组授权
     * @param groupId 分组编号
     * @param privileges 授权信息
     */
    public static void setGroupPrivileges(int groupId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setGroupPrivileges(groupId, items);
        checkResult(result);
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param moduleId 模块编号
     * @param grantType 授权类型
     */
    public static void setRolePrivilege(int roleId, int moduleId, int grantType) {
        Privilege privilege = new Privilege();
        privilege.setRoleId(roleId);
        privilege.setModuleId(moduleId);
        privilege.setGrantType(grantType);
        setRolePrivilege(roleId, privilege);
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param privilege 授权信息
     */
    public static void setRolePrivilege(int roleId, Privilege privilege) {
        setRolePrivileges(roleId, new Privilege[]{privilege});
    }

    /**
     * 角色授权
     * @param roleId 角色编号
     * @param privileges 授权信息
     */
    public static void setRolePrivileges(int roleId, Privilege[] privileges) {
        String items = JSON.toJSONString(privileges);
        HttpResult result = privilegeClient.setRolePrivileges(roleId, items);
        checkResult(result);
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
        checkResult(result);
        return result.getDatas(Privilege.class);
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
        checkResult(result);
        return result.getData(Integer.class);
    }

    /**
     * 测试对象接口
     */
    public static Object testBean(User user, String g, String u) {
        HttpResult result = privilegeClient.testBean(user, g, u);
        checkResult(result);
        return result.getData();
    }

    private static void checkResult(HttpResult result) {
        if (result.getCode() != HttpResult.OK)
            throw new ResultException(result);
    }

}
