package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 角色-用户信息
 * Created by shicy on 2017/9/4.
 */
public class RoleUser extends BaseModel {

    private static final long serialVersionUID = 1002017090400000006L;

    // 角色编号
    private int roleId;

    // 用户编号
    private int userId;

    /**
     * 获取角色编号
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户编号
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
