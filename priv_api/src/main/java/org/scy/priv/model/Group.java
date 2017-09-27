package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 用户组
 * Created by shicy on 2017/9/4.
 */
public class Group extends BaseModel {

    private static final long serialVersionUID = 1002017090400000001L;

    // 组名称
    private String name;

    // 备注信息
    private String remark;

    /**
     * 获取组名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置组名称
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

}
