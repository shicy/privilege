package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.BaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.ModuleMapper;
import org.scy.priv.model.Module;
import org.scy.priv.model.ModuleModel;
import org.scy.priv.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 模块相关服务类
 * Created by shicy on 2017/10/19.
 */
@Service
@SuppressWarnings("unused")
public class ModuleServiceImpl extends BaseService implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public ModuleModel getById(int id) {
        ModuleModel moduleModel = moduleMapper.getById(id);
        if (moduleModel != null && !SessionManager.isPlatform()) {
            if (moduleModel.getPaasId() != SessionManager.getAccountId())
                return null;
        }
        return moduleModel;
    }

    @Override
    public ModuleModel getByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return moduleMapper.getByName(name, SessionManager.getAccountId());
        }
        return null;
    }

    @Override
    public List<ModuleModel> getByNameLike(String name) {
        return null;
    }

    @Override
    public ModuleModel getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            return moduleMapper.getByCode(code, SessionManager.getAccountId());
        }
        return null;
    }

    @Override
    public List<ModuleModel> getByCodeLike(String code) {
        return null;
    }

    @Override
    public ModuleModel save(Module module) {
        if (module == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING, "对象不能为空");

        if (module.getId() > 0)
            return this.update(module);

        return this.add(module);
    }

    @Override
    public ModuleModel delete(int id) {
        return null;
    }

    @Override
    public List<ModuleModel> find(Map<String, Object> params, PageInfo pageInfo) {
        return null;
    }

    /**
     * 新增模块
     */
    private ModuleModel add(Module module) {
        ModuleModel moduleModel = new ModuleModel();

        moduleModel.setCode(StringUtils.trimToEmpty(module.getCode()));
        if (getByCode(moduleModel.getCode()) != null)
            throw new ResultException(10001, "模块编码已存在");

        moduleModel.setName(StringUtils.trimToEmpty(module.getName()));
        if (getByName(moduleModel.getName()) != null)
            throw new ResultException(10002, "模块名称已存在");

        moduleModel.setRemark(module.getRemark());
        moduleModel.setParentId(module.getParentId());
        moduleModel.setState(Const.ENABLED);
        moduleModel.setCreatorId(SessionManager.getUserId());
        moduleModel.setCreateDate(new Date());
        moduleModel.setPaasId(SessionManager.getAccountId());

        moduleMapper.add(moduleModel);

        return moduleModel;
    }

    /**
     * 修改模块
     */
    private ModuleModel update(Module module) {
        ModuleModel moduleModel = getById(module.getId());
        ModuleModel moduleTemp;

        if (moduleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "模块信息不存在");

        moduleModel.setCode(StringUtils.trimToEmpty(module.getCode()));
        moduleTemp = getByCode(moduleModel.getCode());
        if (moduleTemp != null && moduleTemp.getId() != module.getId())
            throw new ResultException(10001, "模块编码已存在");

        moduleModel.setName(StringUtils.trimToEmpty(module.getName()));
        moduleTemp = getByName(moduleModel.getName());
        if (moduleTemp != null && moduleTemp.getId() != module.getId())
            throw new ResultException(10002, "模块名称已存在");

        moduleModel.setRemark(module.getRemark());
        moduleModel.setParentId(module.getParentId());
        moduleModel.setState(module.getState());
        moduleModel.setUpdatorId(SessionManager.getUserId());
        moduleModel.setUpdateDate(new Date());

        moduleMapper.update(moduleModel);

        return null;
    }

}
