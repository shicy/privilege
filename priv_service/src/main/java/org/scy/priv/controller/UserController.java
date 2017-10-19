package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.User;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录验证
 * Created by shicy on 2017/9/2.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     * 参数：
     * -param code 按用户代码查询
     * -param codeLike 按用户代码模糊查询
     * -param name 按用户名称查询
     * -param nameLike 按用户名称模糊查询
     * -param mobile 按用户手机号查询
     * -param email 按用户邮箱地址查询
     * -param type 按用户类型查询（支持多值，逗号分隔）
     * -param groupId 按用户组查询（支持多值，逗号分隔）
     * -param roleId 按角色查询（支持多值，逗号分隔）
     * -param page
     * -param size
     * @return 返回用户列表
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        return HttpResult.ok();
    }

    /**
     * 添加用户
     * 参数：
     * -param code 用户代码（自定义）
     * -param name 用户名称
     * -param mobile 手机号码
     * -param email 邮箱号
     * -param password 用户名或邮箱登录使用的密码
     * -param remark 备注信息
     * -param type 用户类型（自定义）
     * -param accept 允许登录类型
     * -param groupIds 用户所属分组（支持多值，逗号分隔）
     * -param roleIds 用户角色（支持多值，逗号分隔）
     * @return 返回新建用户信息
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public Object addUser(User user, String groupIds, String roleIds) {
        return HttpResult.ok();
    }

    /**
     * 更新用户
     * 参数：
     * -param code 用户代码（自定义）
     * -param name 用户名称
     * -param mobile 手机号码
     * -param email 邮箱号
     * -param password 用户名或邮箱登录使用的密码
     * -param remark 备注信息
     * -param type 用户类型（自定义）
     * -param accept 允许登录类型
     * -param groupIds 用户所属分组（支持多值，逗号分隔）
     * -param roleIds 用户角色（支持多值，逗号分隔）
     * @return 用户信息
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Object updateUser(User user, String groupIds, String roleIds) {
        return HttpResult.ok();
    }

    /**
     * 删除用户
     * 参数：
     * -param id 想要删除的用户编号
     */
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public Object deleteUser(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
