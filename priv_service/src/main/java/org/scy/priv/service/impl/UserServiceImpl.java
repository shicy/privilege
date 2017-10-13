package org.scy.priv.service.impl;

import org.scy.common.web.service.MybatisBaseService;
import org.scy.priv.model.User;
import org.scy.priv.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户相关服务实现类
 * Created by shicy on 2017/9/3.
 */
@Service
public class UserServiceImpl extends MybatisBaseService implements UserService {

    @Override
    public User findUserByName(String name) {
        return null;
    }

}
