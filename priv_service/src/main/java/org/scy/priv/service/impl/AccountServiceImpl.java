package org.scy.priv.service.impl;

import org.apache.ibatis.annotations.Arg;
import org.scy.cache.CachedClient;
import org.scy.common.web.service.BaseService;
import org.scy.priv.mapper.AccountMapper;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户相关服务实现类
 * Created by hykj on 2017/9/5.
 */
@Service
@SuppressWarnings("unused")
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    private CachedClient cachedClient;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountModel getByCode(String code) {
        return accountMapper.getByCode(code);
    }

    @Override
    public AccountModel getWithSecret(String code, String secret) {
        if (code == null || secret == null)
            return null;

        AccountModel account = getByCode(code);
        if (account != null && secret.equals(account.getSecret()))
            return account;

        return null;
    }

    @Override
    public String getAccessToken(String code) {
//        try {
        Object cache = cachedClient.get(code);
            System.out.println(cache);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
