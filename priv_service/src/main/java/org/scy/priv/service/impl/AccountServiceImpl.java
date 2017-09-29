package org.scy.priv.service.impl;

import org.scy.cache.CachedClient;
import org.scy.cache.model.CachedVO;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.web.controller.HttpResult;
import org.scy.common.web.service.BaseService;
import org.scy.priv.mapper.AccountMapper;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 账户相关服务实现类
 * Created by hykj on 2017/9/5.
 */
@Service
@SuppressWarnings("unused")
public class AccountServiceImpl extends BaseService implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

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
        String tokenKey = "access_token-" + code;

        HttpResult result = cachedClient.get(tokenKey);
        CachedVO cachedVO = result.getData(CachedVO.class);
        if (cachedVO != null && cachedVO.getValue() != null) {
            // 系统内有效期5分钟，对外15分钟
            if ((new Date().getTime()) / 1000 - cachedVO.getFlags() < 5 * 60)
                return cachedVO.getValue();
            cachedClient.delete("access_token-" + cachedVO.getValue());
        }

        String token = StringUtilsEx.getRandomString(32);
        result = cachedClient.set(tokenKey, token, 15 * 60 * 1000, (int)((new Date().getTime()) / 1000)); // 15分钟有效期
        if (CachedVO.STORED.equals(result.getData())) {
            // 需要通过 AccessToken 获取帐户信息
            cachedClient.set("access_token-" + token, code, 15 * 60 * 1000);
            return token;
        }
        else {
            logger.error("AccessToken 缓存设置失败：" + result.getMsg());
        }

        return null;
    }

}
