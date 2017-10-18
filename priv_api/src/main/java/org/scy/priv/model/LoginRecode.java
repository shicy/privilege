package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 登录记录
 * Created by shicy on 2017/9/5.
 */
public class LoginRecode extends BaseModel {

    private static final long serialVersionUID = 1002017090500000000L;

    // 用户编号
    private int userId;

    // 登录名称：用户名、手机号或邮箱
    private String loginName;

    // 登录方式：1-帐号 2-手机号 3-邮箱
    private short loginType;

    // 登录验证码
    private String validcode;

    // IP地址
    private String ip;

    // 网站域名
    private String domain;

    // 用户代理
    private String userAgent;

    // 客户端编号
    private String client;

    /**
     * 获取用户编号
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取登录用户名称
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录用户名称
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取登录方式
     */
    public short getLoginType() {
        return loginType;
    }

    /**
     * 设置登录方式
     */
    public void setLoginType(short loginType) {
        this.loginType = loginType;
    }

    /**
     * 获取登录验证码
     */
    public String getValidcode() {
        return validcode;
    }

    /**
     * 设置登录验证码
     */
    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    /**
     * 获取用户IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置用户IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取网站域名
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置网站域名
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取用户代理
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置用户代理
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取客户端编号
     */
    public String getClient() {
        return client;
    }

    /**
     * 设置客户端编号
     */
    public void setClient(String client) {
        this.client = client;
    }

}
