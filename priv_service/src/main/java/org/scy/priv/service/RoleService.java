package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Role;
import org.scy.priv.model.RoleModel;

import java.util.List;
import java.util.Map;

/**
 * 角色相关服务接口
 * Created by shicy on 2017/10/15.
 */
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
     * @param pageInfo 分页信息
     * @return 返回角色列表
     */
    List<RoleModel> find(Map<String, Object> params, PageInfo pageInfo);

}
