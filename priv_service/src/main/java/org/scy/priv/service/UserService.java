package org.scy.priv.service;

import org.scy.priv.model.UserModel;

/**
 * 用户相关服务类
 * Created by shicy on 2017/9/3
 */
public interface UserService {

    /**
     * 根据用户名称获取用户信息
     * @param name
     * @return
     */
    UserModel findUserByName(String name);

}
