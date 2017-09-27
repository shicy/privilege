package org.scy.priv.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.scy.common.web.model.BaseModel;

import java.util.Date;

/**
 * 用户信息
 * Created by shicy on 2017/9/4.
 */
public class User extends BaseModel {

    private static final long serialVersionUID = 1002017090400000000L;

    public final short LOGINTYPE_NAME = 1;
    public final short LOGINTYPE_MOBILE = 2;
    public final short LOGINTYPE_EMAIL = 3;

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
    private Long lastLoginDate;

    /**
     * 当前用户是否支持某种登录方式
     * @param loginType
     * @return
     */
    public boolean isLoginAccept(short loginType) {
        return (accept & loginType) != 0;
    }

    /**
     * 获取用户编码
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置用户编码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取用户名
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号码
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取登录密码
     * @return
     */
    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取用户类型
     * @return
     */
    public short getType() {
        return type;
    }

    /**
     * 设置用户类型
     * @param type
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * 获取允许登录方式
     * @return
     */
    public short getAccept() {
        return accept;
    }

    /**
     * 设置允许登录方式
     * @param accept
     */
    public void setAccept(short accept) {
        this.accept = accept;
    }

    /**
     * 获取最后登录方式
     * @return
     */
    public short getLastLoginType() {
        return lastLoginType;
    }

    /**
     * 设置最后登录方式
     * @param lastLoginType
     */
    public void setLastLoginType(short lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    /**
     * 获取最后登录时间，返回时间戳
     * @return
     */
    public Long getLastLoginTime() {
        return lastLoginDate;
    }

    /**
     * 设置最后登录时间
     * @param lastLoginDate
     */
    public void setLastLoginTime(Long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 获取最后登录时间
     * @return
     */
    @JSONField(serialize = false)
    public Date getLastLoginDate() {
        return lastLoginDate != null ? new Date(lastLoginDate) : null;
    }

    /**
     * 设置最后登录时间
     * @param lastLoginDate
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate != null ? lastLoginDate.getTime() : null;
    }

}
