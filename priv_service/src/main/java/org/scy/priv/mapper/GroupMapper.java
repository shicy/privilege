package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.common.ds.query.Selector;
import org.scy.priv.model.GroupModel;
import org.scy.priv.model.GroupUser;
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

    GroupUserModel addGroupUser(GroupUser groupUser);

    int deleteGroupUserById(int id);
    int deleteGroupUserByGroupId(int groupId);
    int deleteGroupUserByUserId(int userId);
    int deleteGroupUserByGUId(int groupId, int userId);

}
