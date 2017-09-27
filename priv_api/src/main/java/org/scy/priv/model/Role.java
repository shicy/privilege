package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 角色
 * Created by shicy on 2017/9/4.
 */
public class Role extends BaseModel {

    private static final long serialVersionUID = 1002017090400000002L;

    // 角色名称
    private String name;

    // 备注信息
    private String remark;

    // 角色类型：0-默认
    private short type;

    /**
     * 获取角色名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取备注信息
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取角色类型
     * @return
     */
    public short getType() {
        return type;
    }

    /**
     * 设置角色类型
     * @param type
     */
    public void setType(short type) {
        this.type = type;
    }

}
