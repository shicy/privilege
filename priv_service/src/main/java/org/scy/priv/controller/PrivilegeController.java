package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Privilege;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限
 * Created by shicy on 2017/10/19.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class PrivilegeController extends BaseController {

    /**
     * 获取某个用户的权限信息
     * @return 返回该用户的所有授权信息
     */
    @RequestMapping(value = "/privs/list/user/{userId}", method = RequestMethod.GET)
    public Object getUserPrivs(int userId) {
        return HttpResult.ok();
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
        return HttpResult.ok();
    }

    /**
     * 批量添加权限
     * 参数：
     * -param items 权限集，JSON数组格式
     */
    @RequestMapping(value = "/privs/addBatch", method = RequestMethod.POST)
    public Object addPrivileges(HttpServletRequest request) {
        return HttpResult.ok();
    }

    /**
     * 删除权限
     * 参数：
     * -param moduleId 模块编号
     * -param userId 用户编号
     * -param groupId 用户组编号
     * -param roleId 角色编号
     */
    @RequestMapping(value = "/privs/delete", method = RequestMethod.POST)
    public Object deletePrivileges(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
