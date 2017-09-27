package org.scy.priv.model;

import org.scy.common.web.model.BaseModel;

/**
 * 用户登录状态信息
 * Created by shicy on 2017/9/5.
 */
public class Token extends BaseModel {

    private static final long serialVersionUID = 1002017090500000001L;

    // 用户编号
    private int userId;

    // 用户 Token 信息
    private String token;

    // Token 到期时间
    private long expires;

    // 登录时的域名
    private String domain;

    // 客户端编号
    private String client;

    // 用户代理
    private String userAgent;

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
     * 获取 Token 信息
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置 Token 信息
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取 Token 过期时间，时间戳
     * @return
     */
    public long getExpires() {
        return expires;
    }

    /**
     * 设置 Token 过期时间，时间戳
     * @param expires
     */
    public void setExpires(long expires) {
        this.expires = expires;
    }

    /**
     * 获取登录域名
     * @return
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置登录域名
     * @param domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
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

}
