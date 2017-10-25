package org.scy.priv.controller;

import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Module;
import org.scy.priv.model.ModuleModel;
import org.scy.priv.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块
 * Created by shicy on 2017/10/18.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 查询模块信息，带分页，不分层次关系，parentId为0时查询顶层模块
     * 参数：
     * -param name 按名称查询
     * -param nameLike 按名称模糊查询
     * -param code 按编码查询
     * -param codeLike 按编码模糊查询
     * -param parentId 上级模块编号
     * -param page
     * -param size
     * @return 返回模块列表
     */
    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));
        params.put("code", HttpUtilsEx.getStringValue(request, "code"));
        params.put("codeLike", HttpUtilsEx.getStringValue(request, "codeLike"));
        params.put("parentId", HttpUtilsEx.getIntValue(request, "parentId"));

        PageInfo pageInfo = PageInfo.create(request);
        List<ModuleModel> moduleModels = moduleService.find(params, pageInfo);

        return HttpResult.ok(moduleModels, pageInfo);
    }

    /**
     * 获取某用户的模块配置，无分页
     */
    @RequestMapping(value = "/module/list/user/{userId}", method = RequestMethod.GET)
    public Object listByUser(int userId) {
        List<ModuleModel> moduleModels = moduleService.getByUserId(userId);
        return HttpResult.ok(moduleModels);
    }

    /**
     * 获取某用户组的模块配置，无分页
     */
    @RequestMapping(value = "/module/list/group/{groupId}", method = RequestMethod.GET)
    public Object listByGroup(int groupId) {
        List<ModuleModel> moduleModels = moduleService.getByGroupId(groupId);
        return HttpResult.ok(moduleModels);
    }

    /**
     * 获取某角色的模块配置，无分页
     */
    @RequestMapping(value = "/module/list/role/{roleId}", method = RequestMethod.GET)
    public Object listByRole(int roleId) {
        List<ModuleModel> moduleModels = moduleService.getByRoleId(roleId);
        return HttpResult.ok(moduleModels);
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
        if (module == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        module.setId(0);
        ModuleModel moduleModel = moduleService.save(module);

        return HttpResult.ok(moduleModel);
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
        if (module == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        if (module.getId() <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID);

        ModuleModel moduleModel = moduleService.save(module);

        return HttpResult.ok(moduleModel);
    }

    /**
     * 删除模块
     * 参数：
     * -param id 根据编号删除模块
     * -param force 是否强制删除，1为强制删除，同时删除子模块
     */
    @RequestMapping(value = "/module/delete/{moduleId}", method = RequestMethod.POST)
    public Object deleteModule(HttpServletRequest request, @PathVariable int moduleId) {
        if (moduleId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "模块编号无效");

        ModuleModel moduleModel;
        if (HttpUtilsEx.getIntValue(request, "force") == 1)
            moduleModel = moduleService.forceDelete(moduleId);
        else
            moduleModel = moduleService.delete(moduleId);

        if (moduleModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "模块不存在");

        return HttpResult.ok();
    }

}
