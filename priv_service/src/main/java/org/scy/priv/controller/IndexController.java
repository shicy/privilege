package org.scy.priv.controller;

import org.scy.common.annotation.Auth;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
 * Created by shicy on 2017/8/31
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/test/auth")
    @ResponseBody
    @Auth
    public Object authTest() {
        return HttpResult.ok("ok");
    }

}
