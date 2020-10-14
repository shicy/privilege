package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 帐户信息
 * Created by shicy on 2017/8/31
 */
public class Account extends BaseModel {

    private static final long serialVersionUID = 1002017090200000000L;

    // 平台帐户
    public final static short PLATFORM = 0;
    // 个人帐户
    public final static short PERSONAL = 1;
    // 公司帐户
    public final static short COMPANY = 2;

    // 账户名称
    private String name;

    // 编码，第三方应用识别码
    private String code;

    // 密钥，用于第三方验证
    private String secret;

    // 绑定手机号
    private String mobile;

    // 绑定的邮箱
    private String email;

    // 备注信息
    private String remark;

    // 类型，0-平台 1-个人 2-企业
    private short type;

    // 所有者
    private Integer ownerId;

    // 登录密码
    private String password;

    /**
     * 获取名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取帐户密钥
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 设置帐户密钥，加密存储
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 获取帐户绑定手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置帐户绑定手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取帐户绑定邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置帐户绑定邮箱
     */
    public void setEmail(String email) {
        this.email = email;
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
     * 获取帐户类型
     */
    public short getType() {
        return type;
    }

    /**
     * 设置帐户类型
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * 获取所有者编号
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * 设置所有者编号
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 获取登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
