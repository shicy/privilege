package org.scy.priv.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Privilege;
import org.scy.priv.model.PrivilegeModel;
import org.scy.priv.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限
 * Created by shicy on 2017/10/19.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class PrivilegeController extends BaseController {

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 获取某个用户的权限配置信息
     */
    @RequestMapping(value = "/privs/list/user/{userId}", method = RequestMethod.GET)
    public Object getUserPrivs(@PathVariable int userId) {
        List<PrivilegeModel> privilegeModels = privilegeService.getByUserId(userId);
        return HttpResult.ok(privilegeModels);
    }

    /**
     * 获取某个用户组的权限配置信息
     */
    @RequestMapping(value = "/privs/list/group/{groupId}", method = RequestMethod.GET)
    public Object getGroupPrivs(@PathVariable int groupId) {
        List<PrivilegeModel> privilegeModels = privilegeService.getByGroupId(groupId);
        return HttpResult.ok(privilegeModels);
    }

    /**
     * 获取某个角色的权限配置信息
     */
    @RequestMapping(value = "/privs/list/role/{roleId}", method = RequestMethod.GET)
    public Object getRolePrivs(@PathVariable int roleId) {
        List<PrivilegeModel> privilegeModels = privilegeService.getByRoleId(roleId);
        return HttpResult.ok(privilegeModels);
    }

    /**
     * 获取某用户的所有权限信息，包括子模块的配置信息
     */
    @RequestMapping(value = "/privs/getallbyuser/{userId}", method = RequestMethod.GET)
    public Object getUserAllPrivs(@PathVariable int userId) {
        List<PrivilegeModel> privilegeModels = privilegeService.getUserAllPrivileges(userId);
        return HttpResult.ok(privilegeModels);
    }

    /**
     * 添加权限
     * 参数：
     * -param moduleId 模块编号
     * -param userId 用户编号
     * -param groupId 用户组编号
     * -param roleId 用户角色编号
     * -param grantType 授权方式
     */
    @RequestMapping(value = "/privs/add", method = RequestMethod.POST)
    public Object addPrivilege(Privilege privilege) {
        if (privilege == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        privilegeService.addPrivilege(privilege);

        return HttpResult.ok("添加成功", (Object)null);
    }

    /**
     * 批量添加权限
     * 参数：
     * -param items 权限集，JSON数组格式
     */
    @RequestMapping(value = "/privs/addbatch", method = RequestMethod.POST)
    public Object addPrivileges(HttpServletRequest request) {
        String strItems = HttpUtilsEx.getStringValue(request, "items");
        if (StringUtils.isBlank(strItems))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        List<Privilege> privileges = JSON.parseArray(strItems, Privilege.class);
        privilegeService.addPrivileges(privileges);

        return HttpResult.ok("添加成功", (Object)null);
    }

    /**
     * 删除权限，其中moduleId, userId, groupId和roleId组合删除，不能同时无效（小于等于0无效）
     * 参数：
     * -param id 授权编号
     * -param moduleId 模块编号
     * -param userId 用户编号
     * -param groupId 用户组编号
     * -param roleId 角色编号
     */
    @RequestMapping(value = "/privs/delete", method = RequestMethod.POST)
    public Object deletePrivilege(Privilege privilege) {
        if (privilege == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);
        privilegeService.deletePrivileges(privilege);
        return HttpResult.ok("删除成功", (Object)null);
    }

    /**
     * 批量删除权限（上面的也可以批量删除）
     * 参数：
     * -param items 权限集，JSON数据格式
     */
    @RequestMapping(value = "/privs/deletebatch", method = RequestMethod.POST)
    public Object deletePrivileges(HttpServletRequest request) {
        String strItems = HttpUtilsEx.getStringValue(request, "items");
        if (StringUtils.isBlank(strItems))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        List<Privilege> privileges = JSON.parseArray(strItems, Privilege.class);
        privilegeService.deletePrivileges(privileges);

        return HttpResult.ok("删除成功", (Object)null);
    }

    /**
     * 设置用户的权限，将替换该用户原有权限
     * 参数：
     * -param userId 用户编号
     * -param items 权限集，JSON数据格式
     */
    @RequestMapping(value = "/privs/set/user", method = RequestMethod.POST)
    public Object setUserPrivileges(HttpServletRequest request) {
        int userId = HttpUtilsEx.getIntValue(request, "userId", -1);
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        String strItems = HttpUtilsEx.getStringValue(request, "items");
        if (StringUtils.isBlank(strItems))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        List<Privilege> privileges = JSON.parseArray(strItems, Privilege.class);
        privilegeService.setUserPrivileges(userId, privileges);

        return HttpResult.ok();
    }

    /**
     * 设置用户组的权限，将替换该用户组原有权限
     * 参数：
     * -param groupId 用户组编号
     * -param items 权限集，JSON数据格式
     */
    @RequestMapping(value = "/privs/set/group", method = RequestMethod.POST)
    public Object setGroupPrivileges(HttpServletRequest request) {
        int groupId = HttpUtilsEx.getIntValue(request, "groupId", -1);
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        String strItems = HttpUtilsEx.getStringValue(request, "items");
        if (StringUtils.isBlank(strItems))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        List<Privilege> privileges = JSON.parseArray(strItems, Privilege.class);
        privilegeService.setGroupPrivileges(groupId, privileges);

        return HttpResult.ok();
    }

    /**
     * 设置角色的权限，将替换该角色原有权限
     * 参数：
     * -param roleId 角色编号
     * -param items 权限集，JSON数据格式
     */
    @RequestMapping(value = "/privs/set/role", method = RequestMethod.POST)
    public Object setRolePrivileges(HttpServletRequest request) {
        int roleId = HttpUtilsEx.getIntValue(request, "roleId", -1);
        if (roleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        String strItems = HttpUtilsEx.getStringValue(request, "items");
        if (StringUtils.isBlank(strItems))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        List<Privilege> privileges = JSON.parseArray(strItems, Privilege.class);
        privilegeService.setRolePrivileges(roleId, privileges);

        return HttpResult.ok();
    }

    /**
     * 检查用户权限
     * 参数：
     * -param userId 用户编号
     * -param moduleIds 模块编号（逗号分隔）
     * -param moduleCodes 模块编码（逗号分隔）
     * -param moduleNames 模块名称（逗号分隔）
     * @return 返回该用户已授权的模块以及权限值
     */
    @RequestMapping(value = "/privs/check/{userId}", method = RequestMethod.POST)
    public Object checkPrivileges(HttpServletRequest request, @PathVariable int userId) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "用户编号无效");

        List<PrivilegeModel> privilegeModels = new ArrayList<PrivilegeModel>();

        String moduleIds = HttpUtilsEx.getStringValue(request, "moduleIds");
        if (StringUtils.isNotBlank(moduleIds)) {
            int[] module_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(moduleIds, ','));
            List<PrivilegeModel> models = privilegeService.checkByModuleIds(userId, module_ids);
            if (models != null && models.size() > 0)
                privilegeModels.addAll(models);
        }

        String moduleCodes = HttpUtilsEx.getStringValue(request, "moduleCodes");
        if (StringUtils.isNotBlank(moduleCodes)) {
            String[] module_codes = StringUtils.split(moduleCodes, ',');
            List<PrivilegeModel> models = privilegeService.checkByModuleCodes(userId, module_codes);
            if (models != null && models.size() > 0)
                privilegeModels.addAll(models);
        }

        String moduleNames = HttpUtilsEx.getStringValue(request, "moduleNames");
        if (StringUtils.isNotBlank(moduleNames)) {
            String[] module_names = StringUtils.split(moduleNames, ',');
            List<PrivilegeModel> models = privilegeService.checkByModuleNames(userId, module_names);
            if (models != null && models.size() > 0)
                privilegeModels.addAll(models);
        }

        for (int i = privilegeModels.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (privilegeModels.get(i).getModuleId() == privilegeModels.get(j).getModuleId()) {
                    privilegeModels.remove(i);
                    break;
                }
            }
        }

        return HttpResult.ok(privilegeModels);
    }

    /**
     * 检查用户权限（单个模块）
     * 参数：
     * -param userId 用户编号
     * -param module 模块编码（仅支持编码）
     * @return 返回该模块的权限值
     */
    @RequestMapping(value = "/privs/check/{userId}/{module}", method = RequestMethod.POST)
    public Object checkPrivilege(HttpServletRequest request, @PathVariable int userId, @PathVariable String module) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "用户编号无效");

        if (StringUtils.isBlank(module))
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "模块编码无效");

        List<PrivilegeModel> models = privilegeService.checkByModuleCodes(userId, new String[]{module});
        int grantType = models != null && models.size() > 0 ? models.get(0).getGrantType() : 0;

        return HttpResult.ok(grantType);
    }

}
