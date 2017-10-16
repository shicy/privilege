package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Group;
import org.scy.priv.model.GroupModel;

import java.util.List;
import java.util.Map;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
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
     * @param pageInfo 分页信息
     * @return 返回用户组列表
     */
    List<GroupModel> find(Map<String, Object> params, PageInfo pageInfo);

}
