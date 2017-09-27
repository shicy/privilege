package org.scy.priv.controller;

import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.annotation.AccessToken;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录验证
 * Created by shicy on 2017/9/2.
 */
@Controller
@SuppressWarnings("unused")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * 参数：
     * -param username 用户名称、手机号或邮箱
     * -param password 登录密码
     * -param expires 有效期（秒），大于零时有效，否则无限期
     * @return 登录成功将返回用户信息
     */
    @AccessToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getAttribute("access_token"));
        return HttpResult.ok(null);
    }

}
