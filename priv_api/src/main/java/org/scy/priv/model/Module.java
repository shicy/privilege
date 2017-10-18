package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 模块信息
 * Created by shicy on 2017/9/4.
 */
public class Module extends BaseModel {

    private static final long serialVersionUID = 1002017090400000003L;

    // 模块名称
    private String name;

    // 模块编码
    private String code;

    // 备注信息
    private String remark;

    // 上级编号
    private int parentId;

    /**
     * 获取模块名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模块名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取模块编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置模块编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取上级模块编号
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * 设置上级模块编号
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
