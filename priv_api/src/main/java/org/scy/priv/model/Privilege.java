package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 授权信息
 * Created by shicy on 2017/9/4.
 */
public class Privilege extends BaseModel {

    private static final long serialVersionUID = 1002017090400000004L;

    public final static int READONLY = 0;
    public final static int WRITEABLE = 1;

    // 模块编号
    private int moduleId;

    // 用户编号
    private int userId;

    // 用户组编号
    private int groupId;

    // 角色编号
    private int roleId;

    // 授权方式：0-只读 1-读写
    private int grantType;

    /**
     * 获取模块编号
     * @return
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * 设置模块编号
     * @param moduleId
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 获取用户编号
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取用户组编号
     * @return
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * 设置用户组编号
     * @param groupId
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取角色编号
     * @return
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     * @param roleId
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取授权类型
     * @return
     */
    public int getGrantType() {
        return grantType;
    }

    /**
     * 设置授权类型
     * @param grantType
     */
    public void setGrantType(int grantType) {
        this.grantType = grantType;
    }

}
