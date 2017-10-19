package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Role;
import org.scy.priv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        return HttpResult.ok();
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
        return HttpResult.ok();
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
        return HttpResult.ok();
    }

    /**
     * 删除角色信息
     * 参数：
     * -param id 想要删除的角色编号
     */
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
    public Object deleteRole(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
