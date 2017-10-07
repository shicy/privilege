package org.scy.priv.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.exception.ResultException;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.web.service.BaseService;
import org.scy.priv.mapper.AccountMapper;
import org.scy.priv.model.Account;
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

    private char[] codeChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountModel getById(int id) {
        return accountMapper.getById(id);
    }

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
    public AccountModel save(Account account) {
        if (account == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        // 帐户名称和手机号码不能为空
        if (StringUtils.isBlank(account.getName()) || StringUtils.isBlank(account.getMobile()))
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        if (account.getId() > 0)
            return this.update(account);

        return this.add(account);
    }

    /**
     * 添加帐户
     */
    private AccountModel add(Account account) {
        AccountModel accountModel = new AccountModel();

        accountModel.setName(StringUtils.trimToEmpty(account.getName()));
        if (accountMapper.getByName(accountModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        accountModel.setMobile(StringUtils.trimToEmpty(account.getMobile()));
        if (accountMapper.getByMobile(accountModel.getMobile()) != null)
            throw new ResultException(10002, "手机号码已存在");

        accountModel.setEmail(StringUtils.trimToEmpty(account.getEmail()));
        if (StringUtils.isNotEmpty(accountModel.getEmail())) {
            if (accountMapper.getByEmail(accountModel.getEmail()) != null)
                throw new ResultException(10003, "邮箱已存在");
        }

        accountModel.setCode(getValidateCode());
        accountModel.setSecret(makeSecret());
        accountModel.setType(account.getType());
        accountModel.setState(Const.ENABLED);
        accountModel.setCreateDate(new Date());

        if (!ArrayUtils.contains(new int[]{Account.PERSONAL, Account.COMPANY}, accountModel.getType()))
            accountModel.setType(Account.PERSONAL);

        accountMapper.add(accountModel);

        return accountModel;
    }

    /**
     * 更新帐户
     */
    private AccountModel update(Account account) {
        AccountModel accountModel = getById(account.getId());
        AccountModel tempAccount = null;

        accountModel.setCode(StringUtils.trimToEmpty(account.getCode()));
        tempAccount = accountMapper.getByCode(accountModel.getCode());
        if (tempAccount != null && tempAccount.getId() == account.getId())
            throw new ResultException(10001, "编码已存在");

        accountModel.setName(StringUtils.trimToEmpty(account.getName()));
        tempAccount = accountMapper.getByName(accountModel.getName());
        if (tempAccount != null && tempAccount.getId() == account.getId())
            throw new ResultException(10002, "名称已存在");

        accountModel.setMobile(StringUtils.trimToEmpty(account.getMobile()));
        tempAccount = accountMapper.getByMobile(accountModel.getMobile());
        if (tempAccount != null && tempAccount.getId() == account.getId())
            throw new ResultException(10003, "手机号码已存在");

        accountModel.setEmail(StringUtils.trimToEmpty(account.getEmail()));
        if (StringUtils.isNotEmpty(accountModel.getEmail())) {
            tempAccount = accountMapper.getByEmail(accountModel.getEmail());
            if (tempAccount != null && tempAccount.getId() == account.getId())
                throw new ResultException(1004, "邮箱已存在");
        }

        accountModel.setType(account.getType());
        accountModel.setState(account.getState() == Const.ENABLED ? Const.ENABLED : Const.DISABLED);
        accountModel.setUpdateDate(new Date());

        if (!ArrayUtils.contains(new int[]{Account.PERSONAL, Account.COMPANY}, accountModel.getType()))
            accountModel.setType(Account.PERSONAL);

        accountMapper.update(accountModel);

        return accountModel;
    }

    /**
     * 获取有效的帐户编码，不重复
     */
    private String getValidateCode() {
        String code = StringUtilsEx.getRandomString(16, codeChars);
        if (accountMapper.getByCode(code) == null)
            return code;
        return getValidateCode();
    }

    /**
     * 生成一个密钥
     */
    private String makeSecret() {
        return StringUtilsEx.getRandomString(16);
    }

}
