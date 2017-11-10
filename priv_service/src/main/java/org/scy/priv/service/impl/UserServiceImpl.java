package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.utils.ArrayUtilsEx;
import org.scy.common.utils.CommonUtilsEx;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.*;
import org.scy.priv.model.*;
import org.scy.priv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户相关服务实现类
 * Created by shicy on 2017/9/3.
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl extends MybatisBaseService implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserModel getById(int id) {
        UserModel userModel = userMapper.getById(id);
        if (userModel != null && !SessionManager.isPlatform()) {
            if (userModel.getPaasId() != SessionManager.getAccountId())
                return null;
        }
        return userModel;
    }

    @Override
    public UserModel getByCode(String code) {
        if (StringUtils.isBlank(code))
            return null;
        return userMapper.getByCode(code, SessionManager.getAccountId());
    }

    @Override
    public UserModel getByName(String name) {
        if (StringUtils.isBlank(name))
            return null;
        return userMapper.getByName(name, SessionManager.getAccountId());
    }

    @Override
    public UserModel getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile))
            return null;
        return userMapper.getByMobile(mobile, SessionManager.getAccountId());
    }

    @Override
    public UserModel getByEmail(String email) {
        if (StringUtils.isBlank(email))
            return null;
        return userMapper.getByEmail(email, SessionManager.getAccountId());
    }

    @Override
    public List<UserModel> getByIds(int[] ids) {
        if (ids == null || ids.length == 0)
            return new ArrayList<UserModel>();
        return userMapper.getByIds(ids, SessionManager.getAccountId());
    }

    @Override
    public UserModel save(User user) {
        if (user.getId() > 0)
            return update(user);
        return add(user);
    }

    private UserModel add(User user) {
        if (user == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        UserModel userModel = new UserModel();

        userModel.setName(StringUtils.trimToEmpty(user.getName()));
        if (getByName(userModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        userModel.setMobile(StringUtils.trimToEmpty(user.getMobile()));
        if (StringUtils.isNotBlank(userModel.getMobile())) {
            if (!CommonUtilsEx.checkMobile(userModel.getMobile()))
                throw new ResultException(10005, "手机号码格式不正确");
            if (getByMobile(userModel.getMobile()) != null)
                throw new ResultException(10002, "手机号码已存在");
        }

        userModel.setEmail(StringUtils.trimToEmpty(user.getEmail()));
        if (StringUtils.isNotBlank(userModel.getEmail())) {
            if (!CommonUtilsEx.checkEmail(userModel.getEmail()))
                throw new ResultException(10006, "邮箱格式不正确");
            if (getByEmail(userModel.getEmail()) != null)
                throw new ResultException(10003, "邮箱已存在");
        }

        userModel.setCode(StringUtils.trimToEmpty(user.getCode()));
        if (getByCode(userModel.getCode()) != null)
            throw new ResultException(10004, "编码已存在");

        if (StringUtils.isBlank(userModel.getName()) && StringUtils.isBlank(userModel.getMobile())
                && StringUtils.isBlank(userModel.getEmail())) {
            throw new ResultException(Const.MSG_CODE_PARAMINVALID, "用户名、手机号码、邮箱不能同时为空");
        }

        userModel.setPassword(getSecretPassword(user.getPassword()));
        userModel.setRemark(StringUtils.trimToEmpty(user.getRemark()));
        userModel.setAccept(user.getAccept());
        userModel.setType(user.getType());
        userModel.setState(Const.ENABLED);
        userModel.setCreatorId(SessionManager.getUserId());
        userModel.setCreateDate(new Date());
        userModel.setPaasId(SessionManager.getAccountId());

        if (userModel.getAccept() <= 0)
            userModel.setAccept((short)0xf);

        userMapper.add(userModel);

        return userModel;
    }

    private UserModel update(User user) {
        if (user == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        UserModel userModel = getById(user.getId());
        UserModel userTemp;

        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");

        if (user.getName() != null) {
            userModel.setName(StringUtils.trimToEmpty(user.getName()));
            userTemp = getByName(userModel.getName());
            if (userTemp != null && userTemp.getId() != user.getId())
                throw new ResultException(10001, "名称已存在");
        }

        if (user.getMobile() != null) {
            userModel.setMobile(StringUtils.trimToEmpty(user.getMobile()));
            if (!CommonUtilsEx.checkMobile(userModel.getMobile()))
                throw new ResultException(10005, "手机号码格式不正确");
            userTemp = getByMobile(userModel.getMobile());
            if (userTemp != null && userTemp.getId() != user.getId())
                throw new ResultException(10002, "手机号码已存在");
        }

        if (user.getEmail() != null) {
            userModel.setEmail(StringUtils.trimToEmpty(user.getEmail()));
            if (!CommonUtilsEx.checkEmail(userModel.getEmail()))
                throw new ResultException(10006, "邮箱格式不正确");
            userTemp = getByEmail(userModel.getEmail());
            if (userTemp != null && userTemp.getId() != user.getId())
                throw new ResultException(10003, "邮箱已存在");
        }

        if (user.getCode() != null) {
            userModel.setCode(StringUtils.trimToEmpty(user.getCode()));
            userTemp = getByCode(userModel.getCode());
            if (userTemp != null && userTemp.getId() != user.getId())
                throw new ResultException(10004, "编码已存在");
        }

        if (StringUtils.isBlank(userModel.getName()) && StringUtils.isBlank(userModel.getMobile())
                && StringUtils.isBlank(userModel.getEmail())) {
            throw new ResultException(Const.MSG_CODE_PARAMINVALID, "用户名、手机号码、邮箱不能同时为空");
        }

        if (user.getRemark() != null)
            userModel.setRemark(StringUtils.trimToEmpty(user.getRemark()));
        if (user.getAccept() > 0)
            userModel.setAccept(user.getAccept());
        if (user.getType() > 0)
            userModel.setType(user.getType());

        userModel.setUpdatorId(SessionManager.getUserId());
        userModel.setUpdateDate(new Date());

        userMapper.update(userModel);

        return userModel;
    }

    /**
     * 获取加密后密码
     * @param password 原密码
     */
    private String getSecretPassword(String password) {
        if (StringUtils.isBlank(password))
            return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MD5Encoder.encode(md.digest(password.getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    @Override
    public UserModel delete(int id) {
        UserModel userModel = getById(id);
        if (userModel != null) {
            // 删除权限
            privilegeMapper.deleteByUserId(id);
            privilegeMapper.deleteUserPrivsByUserId(id);
            // 删除角色信息
            roleMapper.deleteRoleUserByUserId(id);
            // 删除用户组信息
            groupMapper.deleteGroupUserByUserId(id);
            // 删除Token
            tokenMapper.deleteByUserId(id);
            // 删除用户
            userModel.setState(Const.DISABLED);
            userModel.setUpdatorId(SessionManager.getUserId());
            userModel.setUpdateDate(new Date());
            userMapper.delete(userModel);
        }
        return userModel;
    }

    @Override
    public List<UserModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);

        if (params != null) {
            selector.addFilterNotBlank("u.code", params.get("code"));
            selector.addFilterNotBlank("u.code", params.get("codeLike"), Oper.LIKE);
            selector.addFilterNotBlank("u.name", params.get("name"));
            selector.addFilterNotBlank("u.name", params.get("nameLike"), Oper.LIKE);
            selector.addFilterNotBlank("u.mobile", params.get("mobile"));
            selector.addFilterNotBlank("u.email", params.get("email"));

            Integer type = params.get("type") != null ? (Integer)params.get("type") : null;
            if (type != null)
                selector.addFilter("u.type", type);
            else {
                int[] types = ArrayUtilsEx.transStrToInt(StringUtils.split((String)params.get("types"), ','));
                if (types != null && types.length > 0)
                    selector.addFilter("u.type", types, Oper.IN);
            }

            Integer groupId = params.get("groupId") != null ? (Integer)params.get("groupId") : null;
            if (groupId != null)
                selector.addFilter("gu.groupId", groupId);
            else {
                int[] groupIds = ArrayUtilsEx.transStrToInt(StringUtils.split((String)params.get("groupIds"), ','));
                if (groupIds != null && groupIds.length > 0)
                    selector.addFilter("gu.groupId", groupIds, Oper.IN);
            }

            Integer roleId = params.get("roleId") != null ? (Integer)params.get("roleId") : null;
            if (roleId != null)
                selector.addFilter("ru.roleId", roleId);
            else {
                int[] roleIds = ArrayUtilsEx.transStrToInt(StringUtils.split((String)params.get("roleIds"), ','));
                if (roleIds != null && roleIds.length > 0)
                    selector.addFilter("ru.roleId", roleIds, Oper.IN);
            }
        }

        selector.addFilter("u.state", 0, Oper.GT);
        if (!SessionManager.isPlatform())
            selector.addFilter("u.paasId", SessionManager.getAccountId());

        if (pageInfo != null)
            pageInfo.setTotal(userMapper.countFind(selector));
        return userMapper.find(selector);
    }

    @Override
    public short setUserState(int userId, short state) {
        UserModel userModel = getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在");

        if (state <= 0)
            throw new ResultException(Const.MSG_CODE_PARAMINVALID, "状态不支持");

        userModel.setState(state);
        userModel.setUpdatorId(SessionManager.getUserId());
        userModel.setUpdateDate(new Date());
        userMapper.updateState(userModel);

        return userModel.getState();
    }

    @Override
    public void setUserPassword(int userId, String password, String oldPassword) {
        UserModel userModel = getById(userId);
        if (userModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户不存在");

        oldPassword = getSecretPassword(oldPassword);
        if (!oldPassword.equals(userModel.getPassword()))
            throw new ResultException(Const.MSG_CODE_NOPERMISSION, "原密码错误");

        userModel.setPassword(getSecretPassword(password));
        userModel.setUpdatorId(SessionManager.getUserId());
        userModel.setUpdateDate(new Date());
        userMapper.updatePassword(userModel);
    }

    @Override
    public void addToGroup(int userId, int groupId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(10001, "用户信息不存在");

        GroupModel group = groupService.getById(groupId);
        if (group == null)
            throw new ResultException(10002, "用户组不存在");

        addUserGroup(user, group);
    }

    @Override
    public void addToGroups(int userId, int[] groupIds) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(10001, "用户信息不存在");

        List<GroupModel> groups = groupService.getByIds(groupIds);

        if (groups != null && groups.size() > 0) {
            List<GroupUserModel> groupUsers = groupMapper.getUserGroups(userId, groupIds);
            for (GroupModel group: groups) {
                GroupUserModel model = null;
                if (groupUsers != null && groupUsers.size() > 0) {
                    for (GroupUserModel temp: groupUsers) {
                        if (temp.getGroupId() == group.getId()) {
                            model = temp;
                            break;
                        }
                    }
                }
                if (model == null) {
                    addUserGroup(user, group);
                }
            }
        }
    }

    /**
     * 添加用户和用户组关联关系
     */
    private void addUserGroup(User user, Group group) {
        GroupUserModel groupUser = new GroupUserModel();
        groupUser.setGroupId(group.getId());
        groupUser.setUserId(user.getId());
        groupUser.setState(Const.ENABLED);
        groupUser.setCreatorId(SessionManager.getUserId());
        groupUser.setCreateDate(new Date());
        groupUser.setPaasId(SessionManager.getAccountId());
        groupMapper.addGroupUser(groupUser);
    }

    @Override
    public void deleteFromGroup(int userId, int groupId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        groupMapper.deleteGroupUserByGUId(groupId, userId);
    }

    @Override
    public void deleteFromGroups(int userId, int[] groupIds) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        groupMapper.deleteGroupUserByUGIds(userId, groupIds);
    }

    @Override
    public void deleteFromAllGroups(int userId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        groupMapper.deleteGroupUserByUserId(userId);
    }

    @Override
    public void addRole(int userId, int roleId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(10001, "用户信息不存在");

        RoleModel role = roleService.getById(roleId);
        if (role == null)
            throw new ResultException(10002, "角色信息不存在");

        addUserRole(user, role);
    }

    @Override
    public void addRoles(int userId, int[] roleIds) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(10001, "用户信息不存在");

        List<RoleModel> roles = roleService.getByIds(roleIds);

        if (roles != null && roles.size() > 0) {
            List<RoleUserModel> roleUsers = roleMapper.getUserRoles(userId, roleIds);
            for (RoleModel role: roles) {
                RoleUserModel model = null;
                if (roleUsers != null && roleUsers.size() > 0) {
                    for (RoleUserModel temp: roleUsers) {
                        if (temp.getRoleId() == role.getId()) {
                            model = temp;
                            break;
                        }
                    }
                }
                if (model != null) {
                    addUserRole(user, role);
                }
            }
        }
    }

    private void addUserRole(User user, Role role) {
        RoleUserModel roleUserModel = new RoleUserModel();
        roleUserModel.setRoleId(role.getId());
        roleUserModel.setUserId(user.getId());
        roleUserModel.setState(Const.ENABLED);
        roleUserModel.setCreatorId(SessionManager.getUserId());
        roleUserModel.setCreateDate(new Date());
        roleUserModel.setPaasId(SessionManager.getAccountId());
        roleMapper.addRoleUser(roleUserModel);
    }

    @Override
    public void deleteRole(int userId, int roleId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        roleMapper.deleteRoleUserByRUId(roleId, userId);
    }

    @Override
    public void deleteRoles(int userId, int[] roleIds) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        roleMapper.deleteRoleUserByURIds(userId, roleIds);
    }

    @Override
    public void deleteAllRoles(int userId) {
        UserModel user = getById(userId);
        if (user == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户信息不存在");
        roleMapper.deleteRoleUserByUserId(userId);
    }

    @Override
    public UserModel validUser(String username, String password, short type) {
        UserModel userModel;
        password = getSecretPassword(password);

        if (type <= 0 || type == Const.LOGIN_TYPE_NAME) {
            userModel = getByName(username);
            if (userModel != null && password.equals(userModel.getPassword()))
                return userModel;
        }

        if (type <= 0 || type == Const.LOGIN_TYPE_MOBILE) {
            userModel = getByMobile(username);
            if (userModel != null && password.equals(userModel.getPassword()))
                return userModel;
        }

        if (type <= 0 || type == Const.LOGIN_TYPE_EMAIL) {
            userModel = getByEmail(username);
            if (userModel != null && password.equals(userModel.getPassword()))
                return userModel;
        }

        return null;
    }
}
