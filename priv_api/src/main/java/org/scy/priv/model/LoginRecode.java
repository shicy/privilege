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
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取登录用户名称
     * @return
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录用户名称
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取登录方式
     * @return
     */
    public short getLoginType() {
        return loginType;
    }

    /**
     * 设置登录方式
     * @param loginType
     */
    public void setLoginType(short loginType) {
        this.loginType = loginType;
    }

    /**
     * 获取登录验证码
     * @return
     */
    public String getValidcode() {
        return validcode;
    }

    /**
     * 设置登录验证码
     * @param validcode
     */
    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    /**
     * 获取用户IP地址
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置用户IP地址
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取网站域名
     * @return
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置网站域名
     * @param domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取用户代理
     * @return
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置用户代理
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取客户端编号
     * @return
     */
    public String getClient() {
        return client;
    }

    /**
     * 设置客户端编号
     * @param client
     */
    public void setClient(String client) {
        this.client = client;
    }

}
