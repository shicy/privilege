package org.scy.priv;

import org.scy.common.web.controller.HttpResult;
import org.scy.priv.form.GroupForm;
import org.scy.priv.form.ModuleForm;
import org.scy.priv.form.RoleForm;
import org.scy.priv.model.Account;
import org.scy.priv.form.AccountForm;
import org.scy.priv.model.Group;
import org.scy.priv.model.Module;
import org.scy.priv.model.Role;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 授权服务客户端
 * Create by shicy on 2017/9/4.
 */
@FeignClient(name = "priv-service", url = "${app.priv-service.url:/}")
public interface PrivilegeClient {

    @RequestMapping(value = "/account/info/{accountId}", method = RequestMethod.GET)
    HttpResult getAccountInfo(@PathVariable int accountId);

    @RequestMapping(value = "/account/list", method = RequestMethod.POST)
    HttpResult findAccount(@RequestBody AccountForm account);

    @RequestMapping(value = "/account/add", method = RequestMethod.POST)
    HttpResult addAccount(@RequestBody Account account);

    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    HttpResult setAccount(@RequestBody Account account);

    @RequestMapping(value = "/account/delete/{accountId}", method = RequestMethod.POST)
    HttpResult deleteAccount(@PathVariable int accountId);

    @RequestMapping(value = "/account/set/secret/{accountId}", method = RequestMethod.POST)
    HttpResult makeAccountSecret(@PathVariable int accountId);

    @RequestMapping(value = "/account/set/state/{accountId}/{state}", method = RequestMethod.POST)
    HttpResult setAccountState(@PathVariable int accountId, @PathVariable short state);


    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    HttpResult findGroup(@RequestBody GroupForm group);

    @RequestMapping(value = "/group/list/user/{userId}", method = RequestMethod.GET)
    HttpResult getUserGroupAll(@PathVariable int userId);

    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    HttpResult addGroup(@RequestBody Group group);

    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    HttpResult setGroup(@RequestBody Group group);

    @RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.POST)
    HttpResult deleteGroup(@PathVariable int groupId);

    @RequestMapping(value = "/group/user/add", method = RequestMethod.POST)
    HttpResult addGroupUser(@RequestParam int groupId, @RequestParam String userIds);

    @RequestMapping(value = "/group/user/delete", method = RequestMethod.POST)
    HttpResult deleteGroupUser(@RequestParam int groupId, @RequestParam String userIds);

    @RequestMapping(value = "/group/user/clear/{groupId}", method = RequestMethod.POST)
    HttpResult deleteAllGroupUsers(@PathVariable int groupId);


    @RequestMapping(value = "/role/list", method = RequestMethod.POST)
    HttpResult findRole(@RequestBody RoleForm role);

    @RequestMapping(value = "/role/list/user/{userId}", method = RequestMethod.GET)
    HttpResult getUserRoleAll(@PathVariable int userId);

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    HttpResult addRole(@RequestBody Role role);

    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    HttpResult setRole(@RequestBody Role role);

    @RequestMapping(value = "/role/user/delete", method = RequestMethod.POST)
    HttpResult deleteRole(@RequestParam int roleId, @RequestParam String userIds);

    @RequestMapping(value = "/role/user/clear/{roleId}", method = RequestMethod.POST)
    HttpResult deleteAllRoleUsers(@PathVariable int roleId);


    @RequestMapping(value = "/module/list", method = RequestMethod.POST)
    HttpResult findModule(@RequestBody ModuleForm module);

    @RequestMapping(value = "/module/add", method = RequestMethod.POST)
    HttpResult addModule(@RequestBody Module module);

    @RequestMapping(value = "/module/update", method = RequestMethod.POST)
    HttpResult setModule(@RequestBody Module module);

    @RequestMapping(value = "/module/delete/{moduleId}", method = RequestMethod.POST)
    HttpResult deleteModule(@PathVariable int moduleId, @RequestParam String force);

}
