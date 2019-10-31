package org.scy.priv.service;

import org.scy.common.web.model.ValidInfo;
import org.scy.priv.model.AccountModel;
import org.scy.priv.model.UserModel;

import java.util.Map;

/**
 * Token相关服务类
 * Created by shicy on 2017/10/30.
 */
public interface TokenService {

    /**
     * 生成或更新帐户的 AccessToken 信息
     * @param code 帐户编码
     * @param secret 帐户密钥
     * @return 返回 AccessToken 信息
     */
    String getAccountAccessToken(String code, String secret);

    /**
     * 获取某个 AccessToken 的帐户信息
     * @param token 帐户 AccessToken 信息
     * @return 返回帐户信息
     */
    AccountModel getAccountByToken(String token);

    /**
     * 获取某个 Token 的用户信息
     * @param token 登录时的 Token 信息
     * @return 返回用户信息
     */
    UserModel getUserByToken(String token);

    /**
     * 删除某个用户的 Token 信息
     * @param userId 用户编号
     */
    void deleteByUserId(int userId);

    /**
     * 用户登录
     * @param params 参数:
     *      -username 登录名称：用户名、手机号或邮箱
     *      -expires Token 有效期限（秒）
     *      -loginType 指定登录方式，1-用户名称 2-手机号码 3-邮箱 0-所有
     *      -password 登录密码
     *      -validCode 验证码
     *      -validCodeId 验证码编号
     *      -ip 用户 IP 地址
     *      -domain 域名
     *      -userAgent 浏览器信息
     *      -client 客户端编号 uuid
     * @return 返回该用户的 Token 信息
     */
    String doLogin(Map<String, Object> params);

    /**
     * 用户免密登录
     * @param params 参数：
     *      -username 登录名称：用户名、手机号或邮箱
     *      -expires Token 有效期限（秒）
     *      -loginType 指定登录方式，1-用户名称 2-手机号码 3-邮箱 0-所有
     *      -ip 用户 IP 地址
     *      -domain 域名
     *      -userAgent 浏览器信息
     *      -client 客户端编号 uuid
     * @return 返回该用户的 Token 信息
     */
    String doLoginWithoutPassword(Map<String, Object> params);

    /**
     * 用户退出登录
     * @param token 用户 Token 信息
     */
    void doLogout(String token);

    /**
     * 判断登录用户 Token 是否有效（即未过期）
     */
    boolean isUserTokenValidate(String token, boolean active);

    /**
     * 判断 AccessToken 是否有效（即未过期），一般是 15 分钟有效期
     */
    boolean isAccessTokenValidate(String token);

    /**
     * 获取登录验证码
     */
    ValidInfo getLoginValidateInfo();

    /**
     * 发送登录手机验证码
     * @param mobile 手机号码
     */
    void sendLoginCode(String mobile);

    /**
     * 激活一次登录用户的 Token，延迟失效时间
     */
    void doActive(String token);

}
