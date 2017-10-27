package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Group;
import org.scy.priv.model.GroupModel;
import org.scy.priv.model.GroupUserModel;

import java.util.List;
import java.util.Map;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@SuppressWarnings("unused")
public interface GroupService {

    /**
     * 根据编号获取用户组信息
     */
    GroupModel getById(int id);

    /**
     * 根据名称获取用户组信息
     */
    GroupModel getByName(String name);

    /**
     * 根据用户编号，获取该用户的所属用户组信息
     */
    List<GroupModel> getByUserId(int userId);

    /**
     * 保存用户组，新建或修改用户组
     * @param group 需要保存的用户组信息
     * @return 返回用户组信息
     */
    GroupModel save(Group group);

    /**
     * 删除用户组，如果组内包含用户信息，不允许删除
     * @param id 想要删除的用户组编号
     * @return 返回被删除的用户信息
     */
    GroupModel delete(int id);

    /**
     * 查询用户组信息
     * @param params 查询参数：
     *      -name 按名称查询
     *      -nameLike 按名称模糊查询
     *      -userId 按用户编号查询
     * @param pageInfo 分页信息
     * @return 返回用户组列表
     */
    List<GroupModel> find(Map<String, Object> params, PageInfo pageInfo);

    /**
     * 在某个用户组下添加用户信息
     * @return 返回该用户组-用户关系对象
     */
    GroupUserModel addGroupUser(int groupId, int userId);

    /**
     * 在某个用户组下添加用户信息（去重）
     * @param userIds 想要添加的用户编号集
     * @return 返回新增的用户组-用户关系对象
     */
    List<GroupUserModel> addGroupUsers(int groupId, int[] userIds);

    /**
     * 删除某个用户组下的某个用户信息
     * @return 返回是否删除成功
     */
    boolean deleteGroupUser(int groupId, int userId);

    /**
     * 删除某个用户组下的某些用户信息
     * @param userIds 想要删除的用户编号
     * @return 返回被删除的记录数
     */
    int deleteGroupUsers(int groupId, int[] userIds);

    /**
     * 删除某个用户组下的所有用户信息
     * @return 返回被删除的记录数
     */
    int clearGroupUsers(int groupId);

}
