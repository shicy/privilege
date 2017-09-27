package org.scy.priv.service.impl;

import org.scy.common.web.service.BaseService;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * 账户相关服务实现类
 * Created by hykj on 2017/9/5.
 */
@Service
@SuppressWarnings("unused")
public class AccountServiceImpl extends BaseService implements AccountService {

    @Override
    public AccountModel getByCode(String code) {
        return null;
    }

    @Override
    public AccountModel getWithSecret(String code, String secret) {
        return null;
    }

}
