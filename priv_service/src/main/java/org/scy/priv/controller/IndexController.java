package org.scy.priv.controller;

import org.scy.common.annotation.Auth;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 首页
 * Created by shicy on 2017/8/31
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult version() {
        return HttpResult.ok(getAppVersion());
    }

    @Auth
    @RequestMapping("/test/auth")
    @ResponseBody
    public Object testAuth() {
        return HttpResult.ok("ok");
    }

    @RequestMapping(value = "/test/bean", method = RequestMethod.POST)
    @ResponseBody
    public Object testBean(@RequestBody User user,
            @RequestParam(value = "groupIds", required = false) String groupIds,
            @RequestParam(value = "roleIds", required = false) String roleIds) {
        System.out.println("TestBean: xxxxx，" + groupIds + roleIds);
        return HttpResult.ok("ok");
    }

}
