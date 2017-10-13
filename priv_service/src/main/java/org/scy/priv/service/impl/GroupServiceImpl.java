package org.scy.priv.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.GroupMapper;
import org.scy.priv.model.Group;
import org.scy.priv.model.GroupModel;
import org.scy.priv.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Service
@SuppressWarnings("unused")
public class GroupServiceImpl extends MybatisBaseService implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public GroupModel getById(int id) {
        GroupModel groupModel = groupMapper.getById(id);
        if (groupModel != null) {
            if (groupModel.getPaasId() != SessionManager.getAccountId())
                return null;
        }
        return groupModel;
    }

    @Override
    public GroupModel getByName(String name) {
        return groupMapper.getByName(StringUtils.trimToEmpty(name), SessionManager.getAccountId());
    }

    @Override
    public GroupModel save(Group group) {
        if (group == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        // 名称不能为空
        if (StringUtils.isBlank(group.getName()))
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        if (group.getId() > 0)
            return this.update(group);

        return this.add(group);
    }

    @Override
    public GroupModel delete(int id) {
        GroupModel groupModel = getById(id);
        if (groupModel != null) {
            if (groupModel.getPaasId() != SessionManager.getAccountId())
                throw new ResultException(Const.MSG_CODE_NOPERMISSION);
            groupModel.setState(Const.DISABLED);
            groupMapper.delete(groupModel);
        }
        return groupModel;
    }

    @Override
    public List<GroupModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);
        selector.addFilter("state", 0, Oper.GT);
        selector.addFilter("paasId", SessionManager.getAccountId());
        if (params != null) {
            selector.addFilterNotBlank("name", params.get("name"));
            selector.addFilterNotBlank("name", params.get("nameLike"), Oper.LIKE);
        }

        pageInfo.setTotal(groupMapper.countFind(selector));

        return groupMapper.find(selector);
    }

    private GroupModel add(Group group) {
        GroupModel groupModel = new GroupModel();

        groupModel.setName(StringUtils.trimToEmpty(group.getName()));
        if (getByName(groupModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        groupModel.setRemark(StringUtils.trimToEmpty(group.getName()));
        groupModel.setState(Const.ENABLED);
        groupModel.setCreatorId(SessionManager.getUserId());
        groupModel.setCreateDate(new Date());
        groupModel.setPaasId(SessionManager.getAccountId());

        groupMapper.add(groupModel);

        return groupModel;
    }

    private GroupModel update(Group group) {
        GroupModel groupModel = getById(group.getId());
        GroupModel groupTemp;

        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户组不存在");

        groupModel.setName(StringUtils.trimToEmpty(group.getName()));
        groupTemp = getByName(groupModel.getName());
        if (groupTemp != null && groupTemp.getId() == group.getId())
            throw new ResultException(10001, "名称已存在");

        groupModel.setRemark(group.getRemark());
        groupModel.setUpdatorId(SessionManager.getUserId());
        groupModel.setUpdateDate(new Date());
        groupModel.setState(group.getState());
        groupModel.setPaasId(group.getPaasId());

        if (groupModel.getState() != Const.ENABLED)
            groupModel.setState(Const.DISABLED);

        groupMapper.update(groupModel);

        return groupModel;
    }

}
