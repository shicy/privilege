package org.scy.priv.service.impl;

import org.scy.common.web.service.MybatisBaseService;
import org.scy.priv.service.TokenService;
import org.springframework.stereotype.Service;

/**
 * 用户Token服务类
 * Created by shicy on 2017/10/30.
 */
@Service
@SuppressWarnings("unused")
public class TokenServiceImpl extends MybatisBaseService implements TokenService {

    @Override
    public void deleteByUserId(int userId) {

    }

}
