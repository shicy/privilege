package org.scy.priv.controller;

import org.scy.common.annotation.AccessToken;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 模块
 * Created by shicy on 2017/10/18.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class ModuleController extends BaseController {

    /**
     * 查询模块信息
     * 参数：
     * -param name 按名称查询
     * -param nameLike 按名称模糊查询
     * -param code 按编码查询
     * -param codeLike 按编码模糊查询
     * -param parentId 上级模块编号
     * -param includeChildren 是否包含子模块，存在parentId时有效
     * -param userId 某用户的模块
     * -param groupId 某用户组的模块
     * -param roleId 某角色的模块
     * -param page
     * -param size
     */
    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        return HttpResult.ok();
    }

    /**
     * 新增模块
     * 参数：
     * -param code 模块编码
     * -param name 模块名称
     * -param remark 备注信息
     * -param parentId 上级模块编号
     * @return 返回新增的模块信息
     */
    @RequestMapping(value = "/module/add", method = RequestMethod.POST)
    public Object addModule(Module module) {
        return HttpResult.ok();
    }

    /**
     * 更新模块信息
     * 参数：
     * -param code 模块编码
     * -param name 模块名称
     * -param remark 备注信息
     * -param parentId 上级模块编号
     * @return 返回模块信息
     */
    @RequestMapping(value = "/module/update", method = RequestMethod.POST)
    public Object updateModule(Module module) {
        return HttpResult.ok();
    }

    /**
     * 删除模块
     * 参数：
     * -param id 根据编号删除模块
     */
    @RequestMapping(value = "/module/delete", method = RequestMethod.POST)
    public Object deleteModule(HttpServletRequest request) {
        return HttpResult.ok();
    }

}
