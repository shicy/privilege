package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.GroupMapper;
import org.scy.priv.model.*;
import org.scy.priv.service.GroupService;
import org.scy.priv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Service
@SuppressWarnings("unused")
public class GroupServiceImpl extends MybatisBaseService implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserService userService;

    @Override
    public GroupModel getById(int id) {
        GroupModel groupModel = groupMapper.getById(id);
        if (groupModel != null && !SessionManager.isPlatform()) {
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
    public List<GroupModel> getByIds(int[] ids) {
        if (ids == null || ids.length == 0)
            return new ArrayList<GroupModel>();
        return groupMapper.getByIds(ids, SessionManager.getAccountId());
    }

    @Override
    public List<GroupModel> getByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return find(params, null);
    }

    @Override
    public GroupModel save(Group group) {
        if (group.getId() > 0)
            return this.update(group);
        return this.add(group);
    }

    private GroupModel add(Group group) {
        if (group == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        // 名称不能为空
        if (StringUtils.isBlank(group.getName()))
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        GroupModel groupModel = new GroupModel();

        groupModel.setName(StringUtils.trimToEmpty(group.getName()));
        if (getByName(groupModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        groupModel.setRemark(StringUtils.trimToEmpty(group.getRemark()));
        groupModel.setState(Const.ENABLED);
        groupModel.setCreatorId(SessionManager.getUserId());
        groupModel.setCreateDate(new Date());
        groupModel.setPaasId(SessionManager.getAccountId());

        groupMapper.add(groupModel);

        return groupModel;
    }

    private GroupModel update(Group group) {
        if (group == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        GroupModel groupModel = getById(group.getId());
        GroupModel groupTemp;

        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "用户组不存在");

        if (StringUtils.isNotBlank(group.getName())) {
            groupModel.setName(StringUtils.trimToEmpty(group.getName()));
            groupTemp = getByName(groupModel.getName());
            if (groupTemp != null && groupTemp.getId() == group.getId())
                throw new ResultException(10001, "名称已存在");
        }

        if (group.getRemark() != null)
            groupModel.setRemark(StringUtils.trimToEmpty(group.getRemark()));

        groupModel.setUpdatorId(SessionManager.getUserId());
        groupModel.setUpdateDate(new Date());

        groupMapper.update(groupModel);

        return groupModel;
    }

    @Override
    public GroupModel delete(int id) {
        GroupModel groupModel = getById(id);
        if (groupModel != null) {
            int userCount = groupMapper.countGroupUser(id);
            if (userCount > 0)
                throw new ResultException(10001, "包含用户信息，不允许删除");

            groupModel.setState(Const.DISABLED);
            groupModel.setUpdatorId(SessionManager.getUserId());
            groupModel.setUpdateDate(new Date());
            groupMapper.delete(groupModel);
        }
        return groupModel;
    }

    @Override
    public List<GroupModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);

        if (params != null) {
            selector.addFilterNotBlank("g.name", params.get("name"));
            selector.addFilterNotBlank("g.name", params.get("nameLike"), Oper.LIKE);
        }

        selector.addFilter("g.state", 0, Oper.GT);
        if (!SessionManager.isPlatform())
            selector.addFilter("g.paasId", SessionManager.getAccountId());

        int userId = params != null && params.get("userId") != null ? (Integer)params.get("userId") : 0;
        if (userId > 0) {
            selector.addFilter("gu.userId", userId);
            selector.addFilter("gu.state", 0, Oper.GT);

            if (pageInfo != null)
                pageInfo.setTotal(groupMapper.countFindWithUser(selector));
            return groupMapper.findWithUser(selector);
        }

        if (pageInfo != null)
            pageInfo.setTotal(groupMapper.countFind(selector));
        return groupMapper.find(selector);
    }

    @Override
    public GroupUserModel addGroupUser(int groupId, int userId) {
        GroupModel group = getById(groupId);
        if (group == null)
            throw new ResultException(10001, "不存在的用户组：" + groupId);

        UserModel user = userService.getById(userId);
        if (user == null)
            throw new ResultException(10002, "不存在的用户：" + userId);

        GroupUserModel groupUser = groupMapper.getGroupUser(group.getId(), user.getId());
        if (groupUser == null)
            groupUser = addGroupUser(group, user);
        return groupUser;
    }

    @Override
    public List<GroupUserModel> addGroupUsers(int groupId, int[] userIds) {
        GroupModel group = getById(groupId);
        if (group == null)
            throw new ResultException(10001, "不存在的用户组：" + groupId);

        if (userIds == null || userIds.length <= 0)
            throw new ResultException(10002, "缺少用户信息");

        List<UserModel> users = userService.getByIds(userIds);

        List<GroupUserModel> groupUserModels = new ArrayList<GroupUserModel>();
        if (users != null && users.size() > 0) {
            List<GroupUserModel> groupUsers = groupMapper.getGroupUsers(groupId, userIds);
            for (UserModel user: users) {
                GroupUserModel model = null;
                if (groupUsers != null && groupUsers.size() > 0) {
                    for (GroupUserModel temp: groupUsers) {
                        if (temp.getUserId() == user.getId()) {
                            model = temp;
                            break;
                        }
                    }
                }
                if (model == null) {
                    model = addGroupUser(group, user);
                    groupUserModels.add(model);
                }
            }
        }
        return groupUserModels;
    }

    private GroupUserModel addGroupUser(Group group, User user) {
        GroupUserModel groupUser = new GroupUserModel();
        groupUser.setGroupId(group.getId());
        groupUser.setUserId(user.getId());
        groupUser.setState(Const.ENABLED);
        groupUser.setCreatorId(SessionManager.getUserId());
        groupUser.setCreateDate(new Date());
        groupUser.setPaasId(SessionManager.getAccountId());
        groupMapper.addGroupUser(groupUser);
        return groupUser;
    }

    @Override
    public boolean deleteGroupUser(int groupId, int userId) {
        GroupModel groupModel = getById(groupId);
        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        int count = groupMapper.deleteGroupUserByGUId(groupId, userId);
        return count > 0;
    }

    @Override
    public int deleteGroupUsers(int groupId, int[] userIds) {
        GroupModel groupModel = getById(groupId);
        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        return groupMapper.deleteGroupUserByGUIds(groupId, userIds);
    }

    @Override
    public int clearGroupUsers(int groupId) {
        GroupModel groupModel = getById(groupId);
        if (groupModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST);
        return groupMapper.deleteGroupUserByGroupId(groupId);
    }

}
