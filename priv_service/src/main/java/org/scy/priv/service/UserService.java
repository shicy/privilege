package org.scy.priv.service;

import org.scy.priv.model.UserModel;

import java.util.List;

/**
 * 用户相关服务类
 * Created by shicy on 2017/9/3
 */
@SuppressWarnings("unused")
public interface UserService {

    /**
     * 根据用户编号获取用户信息
     */
    UserModel getById(int id);

    /**
     * 根据用户名称获取用户信息
     */
    UserModel getByName(String name);

    /**
     * 根据用户编号集获取用户信息
     */
    List<UserModel> getByIds(int[] ids);

}
