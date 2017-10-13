package org.scy.priv.controller;

import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Group;
import org.scy.priv.model.GroupModel;
import org.scy.priv.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Controller
@SuppressWarnings("unused")
public class GroupController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    /**
     * 查询用户组
     * 参数：
     * -param name 用户组名称
     * -param nameLike 用户组名称模糊查询
     * -param page 当前分页页码
     * -param limit 每页大小，默认20
     */
    @AccessToken
    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));

        PageInfo pageInfo = PageInfo.create(request);
        List<GroupModel> groupList = groupService.find(params, pageInfo);

        return HttpResult.ok(groupList, pageInfo);
    }

    /**
     * 添加用户组
     * 参数：
     * -param name 用户组名称
     * -param remark 备注信息
     * @return 返回新建用户组信息
     */
    @AccessToken
    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addGroup(Group group) {
        if (group == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        group.setId(0);
        GroupModel groupModel = groupService.save(group);

        return HttpResult.ok(groupModel);
    }

    /**
     * 更新用户组
     */
    @AccessToken
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateGroup(Group group) {
        if (group == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        if (group.getId() <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "用户组编号无效");

        GroupModel groupModel = groupService.save(group);
        if (groupModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "不存在的用户组信息");

        return HttpResult.ok(groupModel);
    }

    /**
     * 删除用户组
     * 参数：
     * -param id 想要删除的用户组编号
     */
    @AccessToken
    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteGroup(HttpServletRequest request) {
        int groupId = HttpUtilsEx.getIntValue(request, "id", -1);
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户组编号无效");

        GroupModel groupModel = groupService.delete(groupId);
        if (groupModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "不存在的用户组编号");

        return HttpResult.ok();
    }

}
