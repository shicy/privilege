package org.scy.priv.controller;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.annotation.AccessToken;
import org.scy.common.ds.PageInfo;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.BaseController;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.model.User;
import org.scy.priv.model.UserModel;
import org.scy.priv.model.UserProfile;
import org.scy.priv.model.UserProfileModel;
import org.scy.priv.service.GroupService;
import org.scy.priv.service.RoleService;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录验证
 * Created by shicy on 2017/9/2.
 */
@Controller
@AccessToken
@ResponseBody
@SuppressWarnings("unused")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户信息
     * 参数：
     * -param code 按用户代码查询
     * -param codeLike 按用户代码模糊查询
     * -param name 按用户名称查询
     * -param nameLike 按用户名称模糊查询
     * -param mobile 按用户手机号查询
     * -param email 按用户邮箱地址查询
     * -param type 按用户类型查询（支持多值，逗号分隔）
     * -param groupId 按用户组查询（支持多值，逗号分隔）
     * -param roleId 按角色查询（支持多值，逗号分隔）
     * -param page
     * -param size
     * @return 返回用户列表
     */
    @RequestMapping(value = "/user/list")
    public Object list(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", HttpUtilsEx.getStringValue(request, "code"));
        params.put("codeLike", HttpUtilsEx.getStringValue(request, "codeLike"));
        params.put("name", HttpUtilsEx.getStringValue(request, "name"));
        params.put("nameLike", HttpUtilsEx.getStringValue(request, "nameLike"));
        params.put("mobile", HttpUtilsEx.getStringValue(request, "mobile"));
        params.put("email", HttpUtilsEx.getStringValue(request, "email"));

        String types = HttpUtilsEx.getStringValue(request, "type");
        if (StringUtils.isNotBlank(types)) {
            params.put("types", ArrayUtilsEx.transStrToInt(StringUtils.split(types, ',')));
        }

        String groupIds = HttpUtilsEx.getStringValue(request, "groupId");
        if (StringUtils.isNotBlank(groupIds)) {
            params.put("groupIds", ArrayUtilsEx.transStrToInt(StringUtils.split(groupIds, ',')));
        }

        String roleIds = HttpUtilsEx.getStringValue(request, "roleId");
        if (StringUtils.isNotBlank(roleIds)) {
            params.put("roleIds", ArrayUtilsEx.transStrToInt(StringUtils.split(roleIds, ',')));
        }

        PageInfo pageInfo = PageInfo.create(request);
        List<UserModel> userModels = userService.find(params, pageInfo);
        for (UserModel userModel: userModels) {
            userModel.setPassword(null);
        }

        return HttpResult.ok(userModels, pageInfo);
    }

    @RequestMapping(value = "/user/info/{userId}", method = RequestMethod.GET)
    public Object getUser(@PathVariable("userId") int userId) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING);
        UserModel userModel = userService.getById(userId);
        if (userModel != null)
            userModel.setPassword(null);
        return HttpResult.ok(userModel);
    }

    /**
     * 添加用户
     * 参数：
     * -param code 用户代码（自定义）
     * -param name 用户名称
     * -param mobile 手机号码
     * -param email 邮箱号
     * -param password 用户名或邮箱登录使用的密码
     * -param remark 备注信息
     * -param type 用户类型（自定义）
     * -param accept 允许登录类型
     * -param groupIds 用户所属分组（支持多值，逗号分隔）
     * -param roleIds 用户角色（支持多值，逗号分隔）
     * @return 返回新建用户信息
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public Object addUser(User user, @RequestParam(value = "groupIds", required = false) String groupIds,
            @RequestParam(value = "roleIds", required = false) String roleIds) {
        if (user == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        user.setId(0);
        UserModel userModel = userService.save(user);
        if (userModel != null) {
            if (StringUtils.isNotBlank(groupIds)) {
                int[] group_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(groupIds, ','));
                userService.addToGroups(userModel.getId(), group_ids);
            }
            if (StringUtils.isNotBlank(roleIds)) {
                int[] role_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(roleIds, ','));
                userService.addRoles(userModel.getId(), role_ids);
            }
            userModel.setPassword(null);
        }

        return HttpResult.ok("新建成功", userModel);
    }

    /**
     * 更新用户
     * 参数：
     * -param code 用户代码（自定义）
     * -param name 用户名称
     * -param mobile 手机号码
     * -param email 邮箱号
     * -param remark 备注信息
     * -param type 用户类型（自定义）
     * -param accept 允许登录类型
     * -param groupIds 用户所属分组（支持多值，逗号分隔）
     * -param roleIds 用户角色（支持多值，逗号分隔）
     * @return 用户信息
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Object updateUser(User user, @RequestParam(value = "groupIds", required = false) String groupIds,
            @RequestParam(value = "roleIds", required = false) String roleIds) {
        if (user == null)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "缺少用户信息");

        if (user.getId() <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "无效的用户信息");

        UserModel userModel = userService.save(user);
        if (userModel != null) {
            if (groupIds != null) {
                userService.deleteFromAllGroups(userModel.getId());
                if (StringUtils.isNotBlank(groupIds)) {
                    int[] group_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(groupIds, ','));
                    userService.addToGroups(userModel.getId(), group_ids);
                }
            }
            if (roleIds != null) {
                userService.deleteAllRoles(userModel.getId());
                if (StringUtils.isNotBlank(roleIds)) {
                    int[] role_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(roleIds, ','));
                    userService.addRoles(userModel.getId(), role_ids);
                }
            }
            userModel.setPassword(null);
        }
        else {
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "用户不存在");
        }

        return HttpResult.ok("修改成功", userModel);
    }

    /**
     * 设置用户分组
     * 参数：
     * -param userId 用户编号
     * -param groupIds 分组编号（逗号分隔）
     * @return 用户信息
     */
    @RequestMapping(value = "/user/set/groups", method = RequestMethod.POST)
    public Object updateUserGroups(@RequestParam("userId") int userId, @RequestParam("groupIds") String groupIds) {
        UserModel user = userService.getById(userId);
        if (user == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "用户不存在");

        if (groupIds != null) {
            userService.deleteFromAllGroups(userId);
            if (StringUtils.isNotBlank(groupIds)) {
                int[] group_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(groupIds, ","));
                userService.addToGroups(userId, group_ids);
            }
        }

        return HttpResult.ok("修改成功", groupService.getByUserId(userId));
    }

    /**
     * 设置用户角色
     * -param userId 用户编号
     * -param roleIds 角色编号（逗号分隔）
     * @return 用户信息
     */
    @RequestMapping(value = "/user/set/roles", method = RequestMethod.POST)
    public Object updateUserRoles(@RequestParam("userId") int userId, @RequestParam("roleIds") String roleIds) {
        UserModel user = userService.getById(userId);
        if (user == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "用户不存在");

        if (roleIds != null) {
            userService.deleteAllRoles(userId);
            if (StringUtils.isNotBlank(roleIds)) {
                int[] role_ids = ArrayUtilsEx.transStrToInt(StringUtils.split(roleIds, ","));
                userService.addRoles(userId, role_ids);
            }
        }

        return HttpResult.ok("修改成功", roleService.getByUserId(userId));
    }

    /**
     * 删除用户
     * 参数：
     * -param userId 想要删除的用户编号
     */
    @RequestMapping(value = "/user/delete/{userId}", method = RequestMethod.POST)
    public Object deleteUser(@PathVariable("userId") int userId) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");

        UserModel userModel = userService.delete(userId);
        if (userModel == null)
            return HttpResult.error(Const.MSG_CODE_NOTEXIST, "用户不存在");
        userModel.setPassword(null);

        return HttpResult.ok("删除成功", userModel);
    }

    /**
     * 修改用户密码
     * 参数：
     * -param userId 用户编号
     * -param password 新密码
     * -param oldPassword 原密码
     */
    @RequestMapping(value = "/user/set/password", method = RequestMethod.POST)
    public Object changePassword(HttpServletRequest request) {
        int userId = HttpUtilsEx.getIntValue(request, "userId", -1);
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "用户编号无效");

        String password = HttpUtilsEx.getStringValue(request, "password");
        if (StringUtils.isBlank(password))
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "密码不能为空");

        String oldPassword = HttpUtilsEx.getStringValue(request, "oldPassword");
        userService.setUserPassword(userId, password, oldPassword);

        return HttpResult.ok("修改成功");
    }

    /**
     * 更改用户状态
     * 参数：
     * -param userId 用户编号
     * -param state 新用户状态
     */
    @RequestMapping(value = "/user/set/state/{userId}/{state}", method = RequestMethod.POST)
    public Object changeState(@PathVariable("userId") int userId, @PathVariable("state") short state) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        int newState = userService.setUserState(userId, state);
        return HttpResult.ok("修改成功", newState);
    }

    /**
     * 获取用户属性列表
     * 参数：
     * -param userId 用户编号
     */
    @RequestMapping(value = "/user/profile/{userId}", method = RequestMethod.GET)
    public Object getProfilesAll(@PathVariable("userId") int userId) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        List<UserProfileModel> profiles = userService.getProfiles(userId);
        return HttpResult.ok(profiles);
    }

    /**
     * 获取用户属性
     * 参数：
     * -param userId 用户编号
     * -param name 属性名称，多值用逗号分隔
     */
    @RequestMapping(value = "/user/profile/{userId}/{name}", method = RequestMethod.GET)
    public Object getProfile(@PathVariable("userId") int userId, @PathVariable("name") String name) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        if (StringUtils.isBlank(name))
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "属性名称不能为空");
        UserProfile profile = userService.getProfile(userId, name);
        return HttpResult.ok(profile);
    }

    /**
     * 查找用户属性信息
     * 参数：
     * -param userId 用户编号
     * -param nameLike 按属性名称模糊查询
     */
    @RequestMapping(value = "/user/profile/find/{userId}", method = RequestMethod.GET)
    public Object findProfiles(HttpServletRequest request, @PathVariable("userId") int userId) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        // 按名称模糊查询
        String nameLike = HttpUtilsEx.getStringValue(request, "nameLike");
        if (StringUtils.isNotBlank(nameLike)) {
            List<UserProfileModel> profiles = userService.getProfilesLike(userId, nameLike);
            return HttpResult.ok(profiles);
        }
        // 按名称集批量查询
        String names = HttpUtilsEx.getStringValue(request, "names");
        if (StringUtils.isNotBlank(names)) {
            List<UserProfileModel> profiles = userService.getProfiles(userId, names.split(","));
            return HttpResult.ok(profiles);
        }
        return HttpResult.ok(new ArrayList<UserProfileModel>());
    }

    /**
     * 设置用户属性
     * 参数：
     * -param userId 用户编号
     * -param name 属性名称
     * -param value 属性值
     */
    @RequestMapping(value = "/user/profile/set/{userId}/{name}/{value}", method = RequestMethod.POST)
    public Object setProfile(@PathVariable("userId") int userId, @PathVariable("name") String name,
            @PathVariable("value") String value) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        if (StringUtils.isBlank(name))
            return HttpResult.error(Const.MSG_CODE_PARAMINVALID, "属性名称不能为空");
        UserProfile profile = new UserProfile();
        profile.setName(name);
        profile.setValue(value);
        return HttpResult.ok(userService.saveProfile(userId, profile));
    }

    /**
     * 设置用户属性
     * 参数：
     * -param userId 用户编号
     */
    @RequestMapping(value = "/user/profile/set_batch/{userId}", method = RequestMethod.POST)
    public Object setProfile(@PathVariable("userId") int userId, UserProfile[] profiles) {
        if (userId <= 0)
            return HttpResult.error(Const.MSG_CODE_PARAMMISSING, "用户编号无效");
        return HttpResult.ok(userService.saveProfiles(userId, profiles));
    }

    /**
     * 删除用户属性
     * 参数：
     * -param userId 用户编号
     */
    @RequestMapping(value = "/user/profile/delete/{userId}", method = RequestMethod.POST)
    public Object deleteProfile(@PathVariable("userId") int userId) {
        userService.deleteProfile(userId);
        return HttpResult.ok("删除成功");
    }

    /**
     * 删除用户属性
     * @param userId 用户编号
     * @param name 属性名称
     */
    @RequestMapping(value = "/user/profile/delete/{userId}/{name}", method = RequestMethod.POST)
    public Object deleteProfile(@PathVariable("userId") int userId, @PathVariable("name") String name) {
        userService.deleteProfile(userId, name);
        return HttpResult.ok("删除成功");
    }

    /**
     * 批量删除用户属性
     * 参数：
     * -param userId 用户编号
     * -param nameLike 属性名称（模糊匹配）
     * -param names 属性名称集
     */
    @RequestMapping("/user/profile/delete_batch/{userId}")
    public Object deleteProfile(HttpServletRequest request, @PathVariable("userId") int userId) {
        if (userId >= 0) {
            String nameLike = HttpUtilsEx.getStringValue(request, "nameLike");
            if (StringUtils.isNotBlank(nameLike)) {
                userService.deleteProfileLike(userId, nameLike);
            }
            String names = HttpUtilsEx.getStringValue(request, "names");
            if (StringUtils.isNotBlank(names)) {
                userService.deleteProfile(userId, names.split(","));
            }
        }
        return HttpResult.ok("删除成功");
    }

}
