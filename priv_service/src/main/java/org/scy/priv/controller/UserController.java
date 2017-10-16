package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
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
@SuppressWarnings("unused")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * -param code 用户代码（自定义）
     * -param name 用户名称
     * -param mobile 手机号码
     * -param email 邮箱号
     * -param password 用户名或邮箱登录使用的密码
     * -param remark 备注信息
     * -param type 用户类型（自定义）
     * -param accept 允许登录类型
     * -param groupIds 用户所属分组
     * -param roleIds 用户角色
     * @return 返回新建用户信息
     */
    @AccessToken
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
