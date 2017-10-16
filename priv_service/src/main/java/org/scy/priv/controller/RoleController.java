package org.scy.priv.controller;

import org.scy.common.web.controller.BaseController;
import org.scy.priv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 角色
 * Created by shicy on 2017/10/15.
 */
@Controller
@SuppressWarnings("unused")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

}
