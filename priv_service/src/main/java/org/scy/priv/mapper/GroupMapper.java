package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.common.ds.query.Selector;
import org.scy.priv.model.GroupModel;
import org.scy.priv.model.GroupUserModel;

import java.util.List;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Mapper
@SuppressWarnings("unused")
public interface GroupMapper extends BaseMapper<GroupModel> {

    GroupModel getByName(String name, int paasId);
    List<GroupModel> getByIds(int[] ids, int paasId);

    List<GroupModel> findWithUser(Selector selector);
    int countFindWithUser(Selector selector);

    GroupUserModel getGroupUser(int groupId, int userId);
    List<GroupUserModel> getGroupUsers(int groupId, int[] userIds);
    List<GroupUserModel> getAllGroupUsers(int groupId);
    List<GroupUserModel> getUserGroups(int userId, int[] groupIds);
    List<GroupUserModel> getAllUserGroups(int userId);

    void addGroupUser(GroupUserModel groupUser);

    int countGroupUser(int groupId);

    int deleteGroupUserById(int id);
    int deleteGroupUserByGroupId(int groupId);
    int deleteGroupUserByUserId(int userId);
    int deleteGroupUserByGUId(int groupId, int userId);
    int deleteGroupUserByGUIds(int groupId, int[] userIds);
    int deleteGroupUserByUGIds(int userId, int[] groupIds);

}
