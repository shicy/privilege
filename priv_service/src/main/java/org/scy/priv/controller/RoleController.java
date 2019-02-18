package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Role;
import org.scy.priv.model.RoleModel;
import org.scy.priv.model.RoleUserModel;
import org.scy.priv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 * Created by shicy on 2017/10/15.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色信息
     * 参数：
     * -param name 按名称查询角色信息
     * -param nameLike 按名称模糊查询角色信息
     * -param userId 某用户的角色信息
     * -param page 当前分页页码
     * -param limit 分页大小，默认20
     */
    @RequestMapping(value = "/role/list")
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));
        params.put("userId", HttpUtilsEx.getIntValue(request, "userId", 0));

        PageInfo pageInfo = PageInfo.create(request);
        List<RoleModel> roleModels = roleService.find(params, pageInfo);

        return HttpResult.ok(roleModels, pageInfo);
    }

    /**
     * 获取某用户的角色信息，不分页
     */
    @RequestMapping(value = "/role/list/user/{userId}", method = RequestMethod.GET)
    public Object listByUser(@PathVariable int userId) {
        List<RoleModel> roleModels = roleService.getByUserId(userId);
        return HttpResult.ok(roleModels);
    }

    /**
     * 添加角色信息
     * 参数：
     * -param name 角色名称
     * -param remark 备注信息
     * -param type 角色类型（自定义）
     * @return 返回新增的角色信息
     */
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public Object addRole(Role role) {
        if (role == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        role.setId(0);
        RoleModel roleModel = roleService.save(role);

        return HttpResult.ok("新建成功", roleModel);
    }

    /**
     * 修改角色信息
     * 参数：
     * -param name 角色名称
     * -param remark 备注信息
     * -param type 角色类型（自定义）
     * @return 返回角色信息
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    public Object updateRole(Role role) {
        if (role == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        if (role.getId() < 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "角色编号无效");

        RoleModel roleModel = roleService.save(role);
        if (roleModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "角色不存在");

        return HttpResult.ok("修改成功", roleModel);
    }

    /**
     * 删除角色信息
     * 参数：
     * -param id 想要删除的角色编号
     */
    @RequestMapping(value = "/role/delete/{roleId}", method = RequestMethod.POST)
    public Object deleteRole(@PathVariable int roleId) {
        if (roleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "角色编号无效");

        RoleModel roleModel = roleService.delete(roleId);
        if (roleModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "角色不存在");

        return HttpResult.ok("删除成功");
    }

    /**
     * 添加用户
     * 参数：
     * -param roleId 角色编号
     * -param userIds 添加的用户编号集
     */
    @RequestMapping(value = "/role/user/add", method = RequestMethod.POST)
    public Object addUsers(HttpServletRequest request) {
        int roleId = HttpUtilsEx.getIntValue(request, "roleId");
        if (roleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少角色信息");

        String userIds = HttpUtilsEx.getStringValue(request, "userIds");
        if (StringUtils.isBlank(userIds))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        int[] ids = ArrayUtilsEx.transStrToInt(StringUtils.split(userIds, ","));
        List<RoleUserModel> models = roleService.addRoleUsers(roleId, ids);
        return HttpResult.ok("添加成功", models != null ? models.size() : 0);
    }

    /**
     * 删除角色下的某些用户信息
     * 参数：
     * -param roleId 角色编号
     * -param userIds 想要删除的用户编号集
     */
    @RequestMapping(value = "/role/user/delete", method = RequestMethod.POST)
    public Object deleteUsers(HttpServletRequest request) {
        int roleId = HttpUtilsEx.getIntValue(request, "roleId");
        if (roleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少角色信息");

        String userIds = HttpUtilsEx.getStringValue(request, "userIds");
        if (StringUtils.isBlank(userIds))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        int[] ids = ArrayUtilsEx.transStrToInt(StringUtils.split(userIds, ","));
        int count = roleService.deleteRoleUsers(roleId, ids);
        return HttpResult.ok("删除成功", count);
    }

    /**
     * 删除角色的所有用户信息
     */
    @RequestMapping(value = "/role/user/clear/{roleId}", method = RequestMethod.POST)
    public Object clearUsers(@PathVariable int roleId) {
        if (roleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少角色信息");
        int count = roleService.clearRoleUsers(roleId);
        return HttpResult.ok("删除成功", count);
    }

}
