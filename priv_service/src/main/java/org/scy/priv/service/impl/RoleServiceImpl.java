package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.BaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.RoleMapper;
import org.scy.priv.model.Role;
import org.scy.priv.model.RoleModel;
import org.scy.priv.model.RoleUserModel;
import org.scy.priv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色相关服务
 * Created by shicy on 2017/10/15.
 */
@Service
@SuppressWarnings("unused")
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

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
        return null;
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

    @Override
    public RoleModel delete(int id) {
        RoleModel roleModel = getById(id);
        if (roleModel != null) {
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
        selector.addFilter("state", 0, Oper.GT);
        selector.addFilter("paasId", SessionManager.getAccountId());
        if (params != null) {
            selector.addFilterNotBlank("name", params.get("name"));
            selector.addFilterNotBlank("name", params.get("nameLike"), Oper.LIKE);
        }

        pageInfo.setTotal(roleMapper.countFind(selector));

        return roleMapper.find(selector);
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
        roleModel.setState(role.getState());
        roleModel.setPaasId(role.getPaasId());

        if (roleModel.getState() != Const.ENABLED)
            roleModel.setState(Const.DISABLED);

        roleMapper.update(roleModel);

        return roleModel;
    }

    @Override
    public RoleUserModel addRoleUser(int roleId, int userId) {
        return null;
    }

    @Override
    public List<RoleUserModel> addRoleUsers(int roleId, int[] userIds) {
        return null;
    }

    @Override
    public boolean deleteRoleUser(int roleId, int userId) {
        return false;
    }

    @Override
    public int deleteRoleUsers(int roleId, int[] userIds) {
        return 0;
    }

    @Override
    public int clearRoleUsers(int roleId) {
        return 0;
    }

}
