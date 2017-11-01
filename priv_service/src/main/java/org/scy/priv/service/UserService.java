package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.User;
import org.scy.priv.model.UserModel;

import java.util.List;
import java.util.Map;

/**
 * 用户相关服务类
 * Created by shicy on 2017/9/3
 */
@SuppressWarnings("unused")
public interface UserService {

    /**
     * 根据用户编号获取用户信息
     */
    UserModel getById(int id);

    /**
     * 根据编码获取用户信息
     */
    UserModel getByCode(String code);

    /**
     * 根据用户名称获取用户信息
     */
    UserModel getByName(String name);

    /**
     * 根据手机号码获取用户信息
     */
    UserModel getByMobile(String mobile);

    /**
     * 根据邮箱获取用户信息
     */
    UserModel getByEmail(String email);

    /**
     * 根据用户编号集获取用户信息
     */
    List<UserModel> getByIds(int[] ids);

    /**
     * 保存用户信息，保存或修改
     * @param user 想要保存的用户信息
     * @return 返回新用户信息
     */
    UserModel save(User user);

    /**
     * 删除用户信息，同时删除该用户的关联信息
     * @param id 想要删除的用户编号
     * @return 返回被删除的用户信息
     */
    UserModel delete(int id);

    /**
     * 查询用户信息
     * @param params 参数
     *      -param code 按编码查询
     *      -param codeLike 按编码模糊查询
     *      -param name 按名称查询
     *      -param nameLike 按名称模糊查询
     *      -param mobile 按手机号码查询
     *      -param email 按邮箱地址查询
     *      -param type|types 按用户类型查询
     *      -param groupId|groupIds 按用户组查询
     *      -param roleId|roleIds 按角色查询
     * @param pageInfo 分页信息
     * @return 返回用户列表
     */
    List<UserModel> find(Map<String, Object> params, PageInfo pageInfo);

    /**
     * 更新用户状态
     * @return 返回用户新状态
     */
    short setUserState(int userId, short state);

    /**
     * 更新用户密码
     */
    void setUserPassword(int userId, String password);

    /**
     * 添加用户所属用户组
     */
    void addToGroup(int userId, int groupId);

    /**
     * 添加用户所属用户组
     */
    void addToGroups(int userId, int[] groupIds);

    /**
     * 删除某用户的一个用户组信息
     */
    void deleteFromGroup(int userId, int groupId);

    /**
     * 删除某用户的一些用户组信息
     */
    void deleteFromGroups(int userId, int[] groupIds);

    /**
     * 删除某用户所有用户组信息
     */
    void deleteFromAllGroups(int userId);

    /**
     * 添加用户角色
     */
    void addRole(int userId, int roleId);

    /**
     * 添加用户角色
     */
    void addRoles(int userId, int[] roleIds);

    /**
     * 删除某用户的一个角色
     */
    void deleteRole(int userId, int roleId);

    /**
     * 删除某用户的一些角色
     */
    void deleteRoles(int userId, int[] roleIds);

    /**
     * 删除某用户所有角色信息
     */
    void deleteAllRoles(int userId);

}
