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

    // 类型，0-平台 1-个人 2-企业
    private short type;

    /**
     * 获取名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编码
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取帐户密钥
     * @return
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 设置帐户密钥，加密存储
     * @param secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 获取帐户绑定手机
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置帐户绑定手机
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取帐户绑定邮箱
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置帐户绑定邮箱
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取帐户类型
     * @return
     */
    public short getType() {
        return type;
    }

    /**
     * 设置帐户类型
     * @param type
     */
    public void setType(short type) {
        this.type = type;
    }

}
