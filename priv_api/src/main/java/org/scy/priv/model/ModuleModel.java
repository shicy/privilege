package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 模块信息
 * Created by shicy on 2017/9/4.
 */
public class ModuleModel extends BaseModel {

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
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模块名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取模块编码
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置模块编码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
     * 获取上级模块编号
     * @return
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * 设置上级模块编号
     * @param parentId
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
