package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Filter;
import org.scy.common.ds.query.Logic;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.utils.CommonUtilsEx;
import org.scy.common.utils.StringUtilsEx;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.AccountMapper;
import org.scy.priv.model.Account;
import org.scy.priv.model.AccountModel;
import org.scy.priv.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账户相关服务实现类
 * Created by hykj on 2017/9/5.
 */
@Service
public class AccountServiceImpl extends MybatisBaseService implements AccountService {

//    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final char[] codeChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountModel getById(int id) {
        AccountModel accountModel = accountMapper.getById(id);
        if (accountModel != null && !SessionManager.isPlatform()) {
            if (!SessionManager.isAccountOwner(accountModel.getId()))
                return null;
        }
        return accountModel;
    }

    @Override
    public AccountModel getByIdAsPlat(int id) {
        return accountMapper.getById(id);
    }

    @Override
    public AccountModel getByName(String name) {
        if (StringUtils.isBlank(name))
            return null;
        AccountModel accountModel = accountMapper.getByName(name);
        if (accountModel != null && !SessionManager.isPlatform()) {
            if (!SessionManager.isAccountOwner(accountModel.getId()))
                return null;
        }
        return null;
    }

    @Override
    public AccountModel getByCode(String code) {
        if (StringUtils.isBlank(code))
            return null;
        AccountModel accountModel = accountMapper.getByCode(code);
        if (accountModel != null && !SessionManager.isPlatform()) {
            if (!SessionManager.isAccountOwner(accountModel.getId()))
                return null;
        }
        return accountModel;
    }

    @Override
    public AccountModel getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile))
            return null;
        AccountModel accountModel = accountMapper.getByMobile(mobile);
        if (accountModel != null && !SessionManager.isPlatform()) {
            if (!SessionManager.isAccountOwner(accountModel.getId()))
                return null;
        }
        return accountModel;
    }

    @Override
    public AccountModel getByEmail(String email) {
        if (StringUtils.isBlank(email))
            return null;
        AccountModel accountModel = accountMapper.getByEmail(email);
        if (accountModel != null && !SessionManager.isPlatform()) {
            if (!SessionManager.isAccountOwner(accountModel.getId()))
                return null;
        }
        return accountModel;
    }

    @Override
    public AccountModel getWithSecret(String code, String secret) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(secret))
            return null;

        AccountModel account = accountMapper.getByCode(code);
        if (account != null && secret.equals(account.getSecret()))
            return account;

        return null;
    }

    @Override
    public AccountModel save(Account account) {
        if (account.getId() > 0)
            return this.update(account);
        return this.add(account);
    }

    /**
     * 添加帐户
     */
    private AccountModel add(Account account) {
        if (account == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        // 帐户名称和手机号码不能为空
        if (StringUtils.isBlank(account.getName()) || StringUtils.isBlank(account.getMobile()))
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        AccountModel accountModel = new AccountModel();

        accountModel.setName(StringUtils.trimToEmpty(account.getName()));
        if (accountMapper.getByName(accountModel.getName()) != null)
            throw new ResultException(10001, "名称已存在");

        accountModel.setMobile(StringUtils.trimToEmpty(account.getMobile()));
        if (StringUtils.isNoneEmpty(accountModel.getMobile())) {
            if (!CommonUtilsEx.checkMobile(accountModel.getMobile()))
                throw new ResultException(1004, "手机号码格式不正确");
            if (accountMapper.getByMobile(accountModel.getMobile()) != null)
                throw new ResultException(10002, "手机号码已存在");
        }

        accountModel.setEmail(StringUtils.trimToEmpty(account.getEmail()));
        if (StringUtils.isNotEmpty(accountModel.getEmail())) {
            if (!CommonUtilsEx.checkEmail(accountModel.getEmail()))
                throw new ResultException(1005, "邮箱格式不正确");
            if (accountMapper.getByEmail(accountModel.getEmail()) != null)
                throw new ResultException(10003, "邮箱已存在");
        }

        accountModel.setRemark(StringUtils.trimToEmpty(account.getRemark()));
        accountModel.setPassword(getSecretPassword(account.getPassword()));
        accountModel.setCode(getValidateCode());
        accountModel.setSecret(makeSecret());
        accountModel.setType(account.getType());
        accountModel.setOwnerId(account.getOwnerId());
        accountModel.setState(Const.ENABLED);
        accountModel.setCreatorId(SessionManager.getUserId());
        accountModel.setCreateDate(new Date());

        if (accountModel.getType() != Account.COMPANY)
            accountModel.setType(Account.PERSONAL);

        accountMapper.add(accountModel);

        return accountModel;
    }

    /**
     * 更新帐户
     */
    private AccountModel update(Account account) {
        if (account == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        AccountModel accountModel = getById(account.getId());
        AccountModel tempAccount;

        if (accountModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "帐户不存在");

        if (StringUtils.isNotBlank(account.getName())) {
            accountModel.setName(StringUtils.trimToEmpty(account.getName()));
            tempAccount = accountMapper.getByName(accountModel.getName());
            if (tempAccount != null && tempAccount.getId() == account.getId())
                throw new ResultException(10001, "名称已存在");
        }

        if (StringUtils.isNotBlank(account.getMobile())) {
            accountModel.setMobile(StringUtils.trimToEmpty(account.getMobile()));
            if (!CommonUtilsEx.checkMobile(accountModel.getMobile()))
                throw new ResultException(1004, "手机号码格式不正确");
            tempAccount = accountMapper.getByMobile(accountModel.getMobile());
            if (tempAccount != null && tempAccount.getId() == account.getId())
                throw new ResultException(10002, "手机号码已存在");
        }

        if (StringUtils.isNotBlank(account.getEmail())) {
            accountModel.setEmail(StringUtils.trimToEmpty(account.getEmail()));
            if (!CommonUtilsEx.checkEmail(accountModel.getEmail()))
                throw new ResultException(1005, "邮箱格式不正确");
            tempAccount = accountMapper.getByEmail(accountModel.getEmail());
            if (tempAccount != null && tempAccount.getId() == account.getId())
                throw new ResultException(1003, "邮箱已存在");
        }

        if (account.getRemark() != null)
            accountModel.setRemark(StringUtils.trimToEmpty(account.getRemark()));

        if (StringUtils.isNotBlank(account.getPassword()))
            accountModel.setPassword(getSecretPassword(account.getPassword()));

        accountModel.setUpdatorId(SessionManager.getUserId());
        accountModel.setUpdateDate(new Date());

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

    @Override
    public AccountModel deleteById(int id) {
        AccountModel accountModel = getById(id);
        if (accountModel != null) {
            accountModel.setState(Const.DISABLED);
            accountModel.setUpdatorId(SessionManager.getUserId());
            accountModel.setUpdateDate(new Date());
            accountMapper.delete(accountModel);
        }
        return accountModel;
    }

    @Override
    public List<AccountModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);

        if (params != null) {
            selector.addFilterNotBlank("code", params.get("code"));
            selector.addFilterNotBlank("name", params.get("name"));
            selector.addFilterNotBlank("name", params.get("nameLike"), Oper.LIKE);
            selector.addFilterNotBlank("mobile", params.get("mobile"));
            selector.addFilterNotBlank("email", params.get("email"));

            String keyword = (String)params.get("keyword");
            if (StringUtils.isNotBlank(keyword)) {
                Filter[] keywordFilters = new Filter[3];
                keywordFilters[0] = new Filter("name", keyword, Oper.LIKE);
                keywordFilters[1] = new Filter("mobile", keyword, Oper.LIKE);
                keywordFilters[2] = new Filter("email", keyword, Oper.LIKE);
                selector.addFilter(keywordFilters, Logic.OR);
            }

            Integer type = (Integer)params.get("type");
            if (type != null)
                selector.addFilter("type", type);
            else if (params.get("types") != null) {
                int[] types = (int[]) params.get("types");
                if (types != null && types.length > 0)
                    selector.addFilter("type", types, Oper.IN);
            }

            Integer state = (Integer)params.get("state");
            if (state != null)
                selector.addFilter("state", state);
            else if (params.get("states") != null) {
                int[] states = (int[]) params.get("states");
                if (states != null && states.length > 0)
                    selector.addFilter("state", states, Oper.IN);
            }
        }

        selector.addFilter("state", 0, Oper.GT);

        if (pageInfo != null)
            pageInfo.setTotal(accountMapper.countFind(selector));
        return accountMapper.find(selector);
    }

    @Override
    public String refreshSecret(int accountId) {
        AccountModel accountModel = getById(accountId);
        if (accountModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "帐户不存在");

        accountModel.setSecret(makeSecret());
        accountModel.setUpdatorId(SessionManager.getUserId());
        accountModel.setUpdateDate(new Date());
        accountMapper.updateSecret(accountModel);

        return accountModel.getSecret();
    }

    @Override
    public short setAccountState(int accountId, short state) {
        AccountModel accountModel = getById(accountId);
        if (accountModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "帐户不存在");

        if (state <= 0)
            throw new ResultException(10001, "状态不支持");

        accountModel.setState(state);
        accountModel.setUpdatorId(SessionManager.getUserId());
        accountModel.setUpdateDate(new Date());
        accountMapper.updateState(accountModel);

        return accountModel.getState();
    }

    @Override
    public AccountModel validAccount(String username, String password) {
        password = getSecretPassword(password);

        AccountModel accountModel = accountMapper.getByMobile(username);
        if (accountModel != null) {
            if (password.equals(accountModel.getPassword()))
                return accountModel;
        }

        accountModel = accountMapper.getByEmail(username);
        if (accountModel != null) {
            if (password.equals(accountModel.getPassword()))
                return accountModel;
        }

        accountModel = accountMapper.getByName(username);
        if (accountModel != null) {
            if (password.equals(accountModel.getPassword()))
                return accountModel;
        }

        return null;
    }

    /**
     * 获取加密后密码
     * @param password 原密码
     */
    private String getSecretPassword(String password) {
        return StringUtilsEx.toMD5(password);
    }

}
