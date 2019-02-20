package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.Group;
import org.scy.priv.model.GroupModel;
import org.scy.priv.model.GroupUserModel;
import org.scy.priv.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Controller
@AccessToken
@ResponseBody
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
     * -param userId 某用户所在的用户组信息
     * -param page 当前分页页码
     * -param limit 每页大小，默认20
     */
    @RequestMapping(value = "/group/list")
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));
        params.put("userId", HttpUtilsEx.getIntValue(request, "userId", 0));

        PageInfo pageInfo = PageInfo.create(request);
        List<GroupModel> groupList = groupService.find(params, pageInfo);

        return HttpResult.ok(groupList, pageInfo);
    }

    /**
     * 获取某用户的所属用户组信息，不分页
     */
    @RequestMapping(value = "/group/list/user/{userId}", method = RequestMethod.GET)
    public Object getByUser(@PathVariable int userId) {
        List<GroupModel> groupModels = groupService.getByUserId(userId);
        return HttpResult.ok(groupModels);
    }

    /**
     * 添加用户组
     * 参数：
     * -param name 用户组名称
     * -param remark 备注信息
     * @return 返回新建用户组信息
     */
    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    public Object addGroup(Group group) {
        if (group == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        group.setId(0);
        GroupModel groupModel = groupService.save(group);

        return HttpResult.ok("新建成功", groupModel);
    }

    /**
     * 更新用户组
     * @return 返回用户信息
     */
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    public Object updateGroup(Group group) {
        if (group == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);

        if (group.getId() <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "用户组编号无效");

        GroupModel groupModel = groupService.save(group);
        if (groupModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "用户组不存在");

        return HttpResult.ok("修改成功", groupModel);
    }

    /**
     * 删除用户组
     * 参数：
     * -param id 想要删除的用户组编号
     */
    @RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.POST)
    public Object deleteGroup(@PathVariable int groupId) {
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户组编号无效");

        GroupModel groupModel = groupService.delete(groupId);
        if (groupModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "不存在的用户组编号");

        return HttpResult.ok("删除成功", groupModel);
    }

    /**
     * 添加用户
     * 参数：
     * -param groupId 用户组编号
     * -param userIds 想要添加的用户编号集
     */
    @RequestMapping(value = "/group/user/add", method = RequestMethod.POST)
    public Object addUsers(HttpServletRequest request) {
        int groupId = HttpUtilsEx.getIntValue(request, "groupId", -1);
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户组信息");

        String userIds = HttpUtilsEx.getStringValue(request, "userIds");
        if (StringUtils.isBlank(userIds))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        int[] ids = ArrayUtilsEx.transStrToInt(StringUtils.split(userIds, ","));
        List<GroupUserModel> models = groupService.addGroupUsers(groupId, ids);
        return HttpResult.ok("添加成功", models != null ? models.size() : 0);
    }

    /**
     * 删除用户
     * 参数：
     * -param groupId 用户组编号
     * -param userIds 想要删除的用户编号集
     */
    @RequestMapping(value = "/group/user/delete", method = RequestMethod.POST)
    public Object deleteUsers(HttpServletRequest request) {
        int groupId = HttpUtilsEx.getIntValue(request, "groupId", -1);
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户组信息");

        String userIds = HttpUtilsEx.getStringValue(request, "userIds");
        if (StringUtils.isBlank(userIds))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        int[] ids = ArrayUtilsEx.transStrToInt(StringUtils.split(userIds, ","));
        int count = groupService.deleteGroupUsers(groupId, ids);
        return HttpResult.ok("删除成功", count);
    }

    /**
     * 删除用户组下的所有用户信息
     */
    @RequestMapping(value = "/group/user/clear/{groupId}", method = RequestMethod.POST)
    public Object clearUsers(@PathVariable int groupId) {
        if (groupId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户组信息");
        int count = groupService.clearGroupUsers(groupId);
        return HttpResult.ok("删除成功", count);
    }

}
