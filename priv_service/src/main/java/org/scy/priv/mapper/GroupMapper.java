package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    GroupModel getByName(@Param("name") String name, @Param("paasId") int paasId);
    List<GroupModel> getByIds(@Param("ids") int[] ids, @Param("paasId") int paasId);

    List<GroupModel> findWithUser(Selector selector);
    int countFindWithUser(Selector selector);

    GroupUserModel getGroupUser(@Param("groupId") int groupId, @Param("userId") int userId);
    List<GroupUserModel> getGroupUsers(@Param("groupId") int groupId, @Param("userIds") int[] userIds);
    List<GroupUserModel> getAllGroupUsers(@Param("groupId") int groupId);
    List<GroupUserModel> getUserGroups(@Param("userId") int userId, @Param("groupIds") int[] groupIds);
    List<GroupUserModel> getAllUserGroups(int userId);

    void addGroupUser(GroupUserModel groupUser);

    int countGroupUser(@Param("groupId") int groupId);

    int deleteGroupUserById(@Param("id") int id);
    int deleteGroupUserByGroupId(@Param("groupId") int groupId);
    int deleteGroupUserByUserId(@Param("userId") int userId);
    int deleteGroupUserByGUId(@Param("groupId") int groupId, @Param("userId") int userId);
    int deleteGroupUserByGUIds(@Param("groupId") int groupId, @Param("userIds") int[] userIds);
    int deleteGroupUserByUGIds(@Param("userId") int userId, @Param("groupIds") int[] groupIds);

}
