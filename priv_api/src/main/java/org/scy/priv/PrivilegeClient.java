package org.scy.priv;

import org.scy.common.web.controller.HttpResult;
import org.scy.priv.form.*;
import org.scy.priv.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 授权服务客户端
 * Create by shicy on 2017/9/4.
 */
@FeignClient(name = "priv-service", url = "${app.priv-service.url:/}")
public interface PrivilegeClient {

    /**
     * 获取账户信息
     * @param accountId 账户编号
     */
    @RequestMapping(value = "/account/info/{accountId}", method = RequestMethod.GET)
    HttpResult getAccountInfo(@PathVariable int accountId);

    /**
     * 查询账户信息
     * @param account 查询条件
     */
    @RequestMapping(value = "/account/list", method = RequestMethod.POST)
    HttpResult findAccount(@RequestBody AccountForm account);

    /**
     * 新增账户
     * @param account 账户信息
     */
    @RequestMapping(value = "/account/add", method = RequestMethod.POST)
    HttpResult addAccount(@RequestBody Account account);

    /**
     * 修改帐户
     * @param account 账户信息
     */
    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    HttpResult setAccount(@RequestBody Account account);

    /**
     * 删除账户
     * @param accountId 账户编号
     */
    @RequestMapping(value = "/account/delete/{accountId}", method = RequestMethod.POST)
    HttpResult deleteAccount(@PathVariable int accountId);

    /**
     * 重置账户密钥
     * @param accountId 账户编号
     */
    @RequestMapping(value = "/account/set/secret/{accountId}", method = RequestMethod.POST)
    HttpResult makeAccountSecret(@PathVariable int accountId);

    /**
     * 修改账户状态
     * @param accountId 账户编号
     * @param state 新的状态
     */
    @RequestMapping(value = "/account/set/state/{accountId}/{state}", method = RequestMethod.POST)
    HttpResult setAccountState(@PathVariable int accountId, @PathVariable short state);


    /**
     * 查询分组
     * @param group 查询条件
     */
    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    HttpResult findGroup(@RequestBody GroupForm group);

    /**
     * 获取用户的所属分组
     * @param userId 用户编号
     */
    @RequestMapping(value = "/group/list/user/{userId}", method = RequestMethod.GET)
    HttpResult getUserGroups(@PathVariable int userId);

    /**
     * 添加分组
     * @param group 分组信息
     */
    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    HttpResult addGroup(@RequestBody Group group);

    /**
     * 修改分组
     * @param group 分组信息
     */
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    HttpResult setGroup(@RequestBody Group group);

    /**
     * 删除分组
     * @param groupId 被删除的分组编号
     */
    @RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.POST)
    HttpResult deleteGroup(@PathVariable int groupId);

    /**
     * 将一个（或多个）用户添加到分组
     * @param groupId 所属分组编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    @RequestMapping(value = "/group/user/add", method = RequestMethod.POST)
    HttpResult addGroupUser(@RequestParam int groupId, @RequestParam String userIds);

    /**
     * 将一个（或多个）用户从分组中移除
     * @param groupId 所属分组编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    @RequestMapping(value = "/group/user/delete", method = RequestMethod.POST)
    HttpResult deleteGroupUser(@RequestParam int groupId, @RequestParam String userIds);

    /**
     * 将多有用户从分组中移除
     * @param groupId 所属分组编号
     */
    @RequestMapping(value = "/group/user/clear/{groupId}", method = RequestMethod.POST)
    HttpResult deleteAllGroupUsers(@PathVariable int groupId);


    /**
     * 查询角色
     * @param role 查询条件
     */
    @RequestMapping(value = "/role/list", method = RequestMethod.POST)
    HttpResult findRole(@RequestBody RoleForm role);

    /**
     * 获取用户角色
     * @param userId 用户编号
     */
    @RequestMapping(value = "/role/list/user/{userId}", method = RequestMethod.GET)
    HttpResult getUserRoles(@PathVariable int userId);

    /**
     * 添加角色
     * @param role 角色信息
     */
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    HttpResult addRole(@RequestBody Role role);

    /**
     * 修改角色
     * @param role 角色信息
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    HttpResult setRole(@RequestBody Role role);

    /**
     * 删除角色
     * @param roleId 被删除的角色编号
     */
    @RequestMapping(value = "/role/delete/{roleId}", method = RequestMethod.POST)
    HttpResult deleteRole(@PathVariable int roleId);

    /**
     * 将一个（或多个）用户赋予该角色
     * @param roleId 角色编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    @RequestMapping(value = "/role/user/add", method = RequestMethod.POST)
    HttpResult addRoleUser(@RequestParam int roleId, @RequestParam String userIds);

    /**
     * 将一个（或多个）用户移除该角色
     * @param roleId 角色编号
     * @param userIds 用户编号，多个用户逗号分隔
     */
    @RequestMapping(value = "/role/user/delete", method = RequestMethod.POST)
    HttpResult deleteRoleUser(@RequestParam int roleId, @RequestParam String userIds);

    /**
     * 移除该角色的所有用户信息
     * @param roleId 角色编号
     */
    @RequestMapping(value = "/role/user/clear/{roleId}", method = RequestMethod.POST)
    HttpResult deleteAllRoleUsers(@PathVariable int roleId);


    /**
     * 查询用户
     * @param user 查询条件
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    HttpResult findUser(@RequestBody UserForm user);

    /**
     * 添加用户
     * @param user 用户信息
     * @param groupIds 所属分组
     * @param roleIds 赋予角色
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    HttpResult addUser(@RequestBody User user, @RequestParam String groupIds, @RequestParam String roleIds);

    /**
     * 修改用户
     * @param user 用户信息
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    HttpResult setUser(@RequestBody User user);

    /**
     * 删除用户
     * @param userId 被删除的用户编号
     */
    @RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.POST)
    HttpResult deleteUser(@PathVariable int userId);

    /**
     * 修改用户密码
     * @param userId 用户编号
     * @param password 新密码
     * @param oldPassword 旧密码
     */
    @RequestMapping(value = "/user/set/password", method = RequestMethod.POST)
    HttpResult setUserPassword(@RequestParam int userId, @RequestParam String password, @RequestParam String oldPassword);

    /**
     * 修改用户状态
     * @param userId 用户编号
     * @param state 新用户状态
     */
    @RequestMapping(value = "/user/set/state/{userId}/{state}", method = RequestMethod.POST)
    HttpResult setUserState(@PathVariable int userId, @PathVariable short state);

    /**
     * 设置用户分组（将删除用户原分组）
     * @param userId 用户编号
     * @param groupIds 新的分组编号，多个值逗号分隔
     */
    @RequestMapping(value = "/user/set/groups", method = RequestMethod.POST)
    HttpResult setUserGroups(@RequestParam int userId, @RequestParam String groupIds);

    /**
     * 设置用户角色（将删除用户原角色）
     * @param userId 用户编号
     * @param roleIds 新的角色编号，多个值逗号分隔
     */
    @RequestMapping(value = "/user/set/roles", method = RequestMethod.POST)
    HttpResult setUserRoles(@RequestParam int userId, @RequestParam String roleIds);


    /**
     * 查询模块
     * @param module 查询条件
     */
    @RequestMapping(value = "/module/list", method = RequestMethod.POST)
    HttpResult findModule(@RequestBody ModuleForm module);

    /**
     * 添加模块
     * @param module 模块信息
     */
    @RequestMapping(value = "/module/add", method = RequestMethod.POST)
    HttpResult addModule(@RequestBody Module module);

    /**
     * 修改模块
     * @param module 模块信息
     */
    @RequestMapping(value = "/module/update", method = RequestMethod.POST)
    HttpResult setModule(@RequestBody Module module);

    /**
     * 删除模块
     * @param moduleId 模块编号
     * @param force 是否强制删除，如果存在子模块需强制才能删除
     */
    @RequestMapping(value = "/module/delete/{moduleId}", method = RequestMethod.POST)
    HttpResult deleteModule(@PathVariable int moduleId, @RequestParam String force);


    @RequestMapping(value = "/privs/list/user/{userId}", method = RequestMethod.GET)
    HttpResult getUserPrivileges(@PathVariable int userId);

    @RequestMapping(value = "/privs/getallbyuser/{userId}", method = RequestMethod.GET)
    HttpResult getUserPrivilegesAll(@PathVariable int userId);

    @RequestMapping(value = "/privs/list/group/{groupId}", method = RequestMethod.GET)
    HttpResult getGroupPrivileges(@PathVariable int groupId);

    @RequestMapping(value = "/privs/list/role/{roleId}", method = RequestMethod.GET)
    HttpResult getRolePrivileges(@PathVariable int roleId);

    @RequestMapping(value = "/privs/add", method = RequestMethod.POST)
    HttpResult addPrivilege(@RequestBody Privilege privilege);

    @RequestMapping(value = "/privs/addbatch", method = RequestMethod.POST)
    HttpResult addPrivileges(@RequestParam String items);

    @RequestMapping(value = "/privs/delete", method = RequestMethod.POST)
    HttpResult deletePrivileges(@RequestBody Privilege privilege);

    @RequestMapping(value = "/privs/deletebatch", method = RequestMethod.POST)
    HttpResult deletePrivileges(@RequestParam String items);

    @RequestMapping(value = "/privs/set/user", method = RequestMethod.POST)
    HttpResult setUserPrivileges(@RequestParam int userId, @RequestParam String items);

    @RequestMapping(value = "/privs/set/group", method = RequestMethod.POST)
    HttpResult setGroupPrivileges(@RequestParam int groupId, @RequestParam String items);

    @RequestMapping(value = "/privs/set/role", method = RequestMethod.POST)
    HttpResult setRolePrivileges(@RequestParam int roleId, @RequestParam String items);

    @RequestMapping(value = "/privs/check/{userId}", method = RequestMethod.POST)
    HttpResult checkUserModules(@PathVariable int userId, @RequestParam String moduleIds,
        @RequestParam String moduleCodes, @RequestParam String moduleNames);

    @RequestMapping(value = "/privs/check/{userId}/{module}", method = RequestMethod.POST)
    HttpResult getUserGrantType(@PathVariable int userId, @PathVariable String module);

}
