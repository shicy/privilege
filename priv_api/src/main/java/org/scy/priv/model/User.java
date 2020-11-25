package org.scy.priv.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;
import org.scy.common.web.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 * Created by shicy on 2017/9/4.
 */
public class User extends BaseModel {

    private static final long serialVersionUID = 1002017090400000000L;

    // 用户编码，用于业务系统识别用户
    private String code;

    // 用户名称
    private String name;

    // 手机号
    private String mobile;

    // 电子邮箱
    private String email;

    // 登录密码
    private String password;

    // 备注信息
    private String remark;

    // 用户类型：0-默认
    private short type;

    // 允许登录方式：按位运算[email, mobile, name]，默认7-允许全部登录方式
    private short accept = 7;

    // 最后一次登录方式：1-帐号 2-手机号 4-邮箱
    private short lastLoginType;

    // 最后一次登录时间
    private Long lastLoginTime;

    // 所在用户组
    private List<? extends Group> groups;

    // 用户角色信息
    private List<? extends Role> roles;

    /**
     * 转化为平台用户
     */
    public org.scy.common.web.session.User toPlatUser() {
        org.scy.common.web.session.User user = new org.scy.common.web.session.User();
        user.setId(this.getId());
        if (StringUtils.isBlank(name))
            user.setName(name);
        else if (StringUtils.isBlank(mobile))
            user.setName(mobile);
        else if (StringUtils.isBlank(email))
            user.setName(email);
        user.setRemark(this.getRemark());
        user.setType(this.getType());
        user.setState(this.getState());
        if (roles != null && roles.size() > 0) {
            int[] roleIds = new int[roles.size()];
            for (int i = 0; i < roles.size(); i++) {
                roleIds[i] = roles.get(i).getId();
            }
            user.setRoleIds(roleIds);
        }
        return user;
    }

    /**
     * 获取用户编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置用户编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取登录密码
     */
//    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取用户类型
     */
    public short getType() {
        return type;
    }

    /**
     * 设置用户类型
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * 获取允许登录方式
     */
    public short getAccept() {
        return accept;
    }

    /**
     * 设置允许登录方式
     */
    public void setAccept(short accept) {
        this.accept = accept;
    }

    /**
     * 获取最后登录方式
     */
    public short getLastLoginType() {
        return lastLoginType;
    }

    /**
     * 设置最后登录方式
     */
    public void setLastLoginType(short lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    /**
     * 获取最后登录时间，返回时间戳
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取最后登录时间
     */
    @JSONField(serialize = false)
    public Date getLastLoginDate() {
        return lastLoginTime != null ? new Date(lastLoginTime) : null;
    }

    /**
     * 设置最后登录时间
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginTime = lastLoginDate != null ? lastLoginDate.getTime() : null;
    }

    /**
     * 获取用户组信息
     */
    public List<? extends Group> getGroups() {
        return groups;
    }

    /**
     * 设置用户组信息
     */
    public void setGroups(List<? extends Group> groups) {
        this.groups = groups;
    }

    /**
     * 获取用户角色信息
     */
    public List<? extends Role> getRoles() {
        return roles;
    }

    /**
     * 设置用户角色信息
     */
    public void setRoles(List<? extends Role> roles) {
        this.roles = roles;
    }
}
