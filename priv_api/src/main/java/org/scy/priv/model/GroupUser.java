package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 用户-组关联信息
 * Created by shicy on 2017/9/4.
 */
public class GroupUser extends BaseModel {

    private static final long serialVersionUID = 1002017090400000005L;

    // 用户组编号
    private int groupId;

    // 用户编号
    private int userId;

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

}
