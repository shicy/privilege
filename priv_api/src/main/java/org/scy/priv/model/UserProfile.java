package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 用户属性
 * Created by shicy 2020/01/22
 */
public class UserProfile extends BaseModel {

    private static final long serialVersionUID = 1002020012200000001L;

    // 用户编号
    private int userId;

    // 属性名称
    private String name;

    // 属性值
    private String value;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
