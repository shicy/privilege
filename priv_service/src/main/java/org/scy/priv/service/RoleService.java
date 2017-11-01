package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Role;
import org.scy.priv.model.RoleModel;
import org.scy.priv.model.RoleUserModel;

import java.util.List;
import java.util.Map;

/**
 * 角色相关服务接口
 * Created by shicy on 2017/10/15.
 */
@SuppressWarnings("unused")
public interface RoleService {

    /**
     * 根据编号获取角色信息
     */
    RoleModel getById(int id);

    /**
     * 根据名称获取角色信息
     */
    RoleModel getByName(String name);

    /**
     * 根据编号批量获取角色信息
     */
    List<RoleModel> getByIds(int[] ids);

    /**
     * 根据用户编号，获取该用户的角色信息
     */
    List<RoleModel> getByUserId(int userId);

    /**
     * 保存角色，新增或修改角色信息
     * @param role 需要保存的角色信息
     * @return 返回角色信息
     */
    RoleModel save(Role role);

    /**
     * 删除角色
     * @param id 想要删除的角色编号
     * @return 返回被删除角色信息
     */
    RoleModel delete(int id);

    /**
     * 查找角色信息
     * @param params 参数
     *      -name 按名称查询
     *      -nameLike 按名称模糊查询
     *      -userId 用户编号
     * @param pageInfo 分页信息
     * @return 返回角色列表
     */
    List<RoleModel> find(Map<String, Object> params, PageInfo pageInfo);

    /**
     * 在某角色中添加用户信息
     * @return 返回添加的角色-用户关系对象
     */
    RoleUserModel addRoleUser(int roleId, int userId);

    /**
     * 在某角色中添加用户信息
     * @param userIds 想要添加的用户编号集
     * @return 返回添加的角色-用户关系对象
     */
    List<RoleUserModel> addRoleUsers(int roleId, int[] userIds);

    /**
     * 删除某角色中的某个用户
     * @return 返回是否删除成功
     */
    boolean deleteRoleUser(int roleId, int userId);

    /**
     * 删除某角色的某些用户信息
     * @param userIds 想要删除的用户编号集
     * @return 返回删除的记录数
     */
    int deleteRoleUsers(int roleId, int[] userIds);

    /**
     * 删除某角色的所有用户信息
     * @return 返回删除的记录数
     */
    int clearRoleUsers(int roleId);

}
