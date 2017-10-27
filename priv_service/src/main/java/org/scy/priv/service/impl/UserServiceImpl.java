package org.scy.priv.service.impl;

import org.scy.common.web.service.MybatisBaseService;
import org.scy.priv.model.User;
import org.scy.priv.model.UserModel;
import org.scy.priv.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户相关服务实现类
 * Created by shicy on 2017/9/3.
 */
@Service
public class UserServiceImpl extends MybatisBaseService implements UserService {

    @Override
    public UserModel getById(int id) {
        return null;
    }

    @Override
    public UserModel getByName(String name) {
        return null;
    }

    @Override
    public List<UserModel> getByIds(int[] ids) {
        return null;
    }

}
