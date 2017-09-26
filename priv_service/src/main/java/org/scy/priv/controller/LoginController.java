package org.scy.priv.controller;

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
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request) {
        return HttpResult.ok(null);
    }

}
