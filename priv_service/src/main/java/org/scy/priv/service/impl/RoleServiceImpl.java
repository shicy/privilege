package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.RoleMapper;
import org.scy.priv.model.*;
import org.scy.priv.service.RoleService;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 角色相关服务
 * Created by shicy on 2017/10/15.
 */
@Service
@SuppressWarnings("unused")
public class RoleServiceImpl extends MybatisBaseService implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Override
    public RoleModel getById(int id) {
        RoleModel roleModel = roleMapper.getById(id);
        if (roleModel != null && !SessionManager.isPlatform()) {
            if (roleModel.getPaasId() != SessionManager.getAccountId())
                return null;
        }
        return roleModel;
    }

    @Override
    public RoleModel getByName(String name) {
        return roleMapper.getByName(name, SessionManager.getAccountId());
    }

    @Override
    public List<RoleModel> getByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return find(params, null);
    }

    @Override
    public RoleModel save(Role role) {
        if (role == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        if (StringUtils.isBlank(role.getName()))
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        if (role.getId() > 0)
            return this.update(role);
        return this.add(role);
    }

    private RoleModel add(Role role) {
        RoleModel roleModel = new RoleModel();

        roleModel.setName(StringUtils.trimToEmpty(role.getName()));
        if (getByName(roleModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        roleModel.setRemark(StringUtils.trimToEmpty(role.getRemark()));
        roleModel.setType(role.getType());
        roleModel.setState(Const.ENABLED);
        roleModel.setCreatorId(SessionManager.getUserId());
        roleModel.setCreateDate(new Date());
        roleModel.setPaasId(SessionManager.getAccountId());

        roleMapper.add(roleModel);

        return roleModel;
    }

    private RoleModel update(Role role) {
        RoleModel roleModel = getById(role.getId());
        RoleModel roleTemp;

        if (roleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "角色不存在");

        roleModel.setName(StringUtils.trimToEmpty(role.getName()));
        roleTemp = getByName(roleModel.getName());
        if (roleTemp != null && roleTemp.getPaasId() != roleModel.getPaasId())
            throw new ResultException(10001, "名称已存在");

        roleModel.setRemark(role.getRemark());
        roleModel.setType(role.getType());
        roleModel.setUpdatorId(SessionManager.getUserId());
        roleModel.setUpdateDate(new Date());

        roleMapper.update(roleModel);

        return roleModel;
    }

    @Override
    public RoleModel delete(int id) {
        RoleModel roleModel = getById(id);
        if (roleModel != null) {
            if (roleMapper.countRoleUser(id) > 0)
                throw new ResultException(10001, "该角色包含用户信息，不允许删除");
            roleModel.setState(Const.DISABLED);
            roleModel.setUpdatorId(SessionManager.getUserId());
            roleModel.setUpdateDate(new Date());
            roleMapper.delete(roleModel);
        }
        return roleModel;
    }

    @Override
    public List<RoleModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);

        if (params != null) {
            selector.addFilterNotBlank("r.name", params.get("name"));
            selector.addFilterNotBlank("r.name", params.get("nameLike"), Oper.LIKE);
        }

        selector.addFilter("r.state", 0, Oper.GT);
        if (!SessionManager.isPlatform())
            selector.addFilter("r.paasId", SessionManager.getAccountId());

        int userId = params != null ? (Integer)params.get("userId") : 0;
        if (userId > 0) {
            selector.addFilter("ru.userId", userId);
            selector.addFilter("ru..state", 0, Oper.GT);

            if (pageInfo != null)
                pageInfo.setTotal(roleMapper.countFindWithUser(selector));
            return roleMapper.findWithUser(selector);
        }

        if (pageInfo != null)
            pageInfo.setTotal(roleMapper.countFind(selector));
        return roleMapper.find(selector);
    }

    @Override
    public RoleUserModel addRoleUser(int roleId, int userId) {
        RoleModel role = getById(roleId);
        if (role == null)
            throw new ResultException(10001, "角色信息不存在");

        UserModel user = userService.getById(userId);
        if (user == null)
            throw new ResultException(10002, "用户信息不存在");

        RoleUserModel roleUser = roleMapper.getRoleUser(roleId, userId);
        if (roleUser == null)
            roleUser = addRoleUser(role, user);
        return roleUser;
    }

    @Override
    public List<RoleUserModel> addRoleUsers(int roleId, int[] userIds) {
        RoleModel role = getById(roleId);
        if (role == null)
            throw new ResultException(10001, "角色信息不存在");

        if (userIds == null || userIds.length == 0)
            throw new ResultException(10002, "缺少用户信息");

        List<UserModel> users = userService.getByIds(userIds);
        List<RoleUserModel> roleUsers = roleMapper.getRoleUsers(roleId, userIds);

        List<RoleUserModel> roleUserModels = new ArrayList<RoleUserModel>();
        if (users != null && users.size() > 0) {
            for (UserModel user: users) {
                RoleUserModel model = null;
                if (roleUsers != null && roleUsers.size() > 0) {
                    for (RoleUserModel temp: roleUsers) {
                        if (temp.getUserId() == user.getId()) {
                            model = temp;
                            break;
                        }
                    }
                }
                if (model == null) {
                    model = addRoleUser(role, user);
                    roleUserModels.add(model);
                }
            }
        }
        return roleUserModels;
    }

    /**
     * 添加角色和用户的关联关系（内部方法）
     */
    private RoleUserModel addRoleUser(Role role, User user) {
        RoleUserModel roleUserModel = new RoleUserModel();
        roleUserModel.setRoleId(role.getId());
        roleUserModel.setUserId(user.getId());
        roleUserModel.setState(Const.ENABLED);
        roleUserModel.setCreatorId(SessionManager.getUserId());
        roleUserModel.setCreateDate(new Date());
        roleUserModel.setPaasId(SessionManager.getAccountId());
        roleMapper.addRoleUser(roleUserModel);
        return roleUserModel;
    }

    @Override
    public boolean deleteRoleUser(int roleId, int userId) {
        RoleModel roleModel = getById(roleId);
        if (roleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        int count = roleMapper.deleteRoleUserByRUId(roleId, userId);
        return count > 0;
    }

    @Override
    public int deleteRoleUsers(int roleId, int[] userIds) {
        RoleModel roleModel = getById(roleId);
        if (roleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        return roleMapper.deleteRoleUserByRUIds(roleId, userIds);
    }

    @Override
    public int clearRoleUsers(int roleId) {
        RoleModel roleModel = getById(roleId);
        if (roleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        return roleMapper.deleteRoleUserByRoleId(roleId);
    }

}
