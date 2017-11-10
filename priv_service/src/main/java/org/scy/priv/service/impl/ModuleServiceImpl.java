package org.scy.priv.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.Const;
import org.scy.common.ds.PageInfo;
import org.scy.common.ds.query.Oper;
import org.scy.common.ds.query.Selector;
import org.scy.common.exception.ResultException;
import org.scy.common.web.service.MybatisBaseService;
import org.scy.common.web.session.SessionManager;
import org.scy.priv.mapper.ModuleMapper;
import org.scy.priv.mapper.PrivilegeMapper;
import org.scy.priv.model.Module;
import org.scy.priv.model.ModuleModel;
import org.scy.priv.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 模块相关服务类
 * Created by shicy on 2017/10/19.
 */
@Service
@SuppressWarnings("unused")
public class ModuleServiceImpl extends MybatisBaseService implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PrivilegeMapper privilegeMapper;

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
        if (StringUtils.isBlank(name))
            return null;
        return moduleMapper.getByName(name, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getByNameLike(String name) {
        if (StringUtils.isBlank(name))
            return new ArrayList<ModuleModel>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nameLike", name);
        return find(params, null);
    }

    @Override
    public ModuleModel getByCode(String code) {
        if (StringUtils.isBlank(code))
            return null;
        return moduleMapper.getByCode(code, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getByCodeLike(String code) {
        if (StringUtils.isBlank(code))
            return new ArrayList<ModuleModel>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("codeLike", code);
        return find(params, null);
    }

    @Override
    public List<ModuleModel> getByParentId(int parentId) {
        return moduleMapper.getByParentId(parentId, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getByIds(int[] ids) {
        if (ids == null || ids.length == 0)
            return new ArrayList<ModuleModel>();
        return moduleMapper.getByIds(ids, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getByCodes(String[] codes) {
        if (codes == null || codes.length == 0)
            return new ArrayList<ModuleModel>();
        return moduleMapper.getByCodes(codes, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getByNames(String[] names) {
        if (names == null || names.length == 0)
            return new ArrayList<ModuleModel>();
        return moduleMapper.getByNames(names, SessionManager.getAccountId());
    }

    @Override
    public List<ModuleModel> getAll() {
        return find(null, null);
    }

    @Override
    public ModuleModel save(Module module) {
        if (module.getId() > 0)
            return this.update(module);
        return this.add(module);
    }

    /**
     * 新增模块
     */
    private ModuleModel add(Module module) {
        if (module == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        if (StringUtils.isBlank(module.getCode()) && StringUtils.isBlank(module.getName()))
            throw new ResultException(Const.MSG_CODE_PARAMINVALID, "编码和名称不能同时为空");

        ModuleModel moduleModel = new ModuleModel();

        moduleModel.setCode(StringUtils.trimToEmpty(module.getCode()));
        if (getByCode(moduleModel.getCode()) != null)
            throw new ResultException(10001, "模块编码已存在");

        moduleModel.setName(StringUtils.trimToEmpty(module.getName()));
        if (getByName(moduleModel.getName()) != null)
            throw new ResultException(10002, "模块名称已存在");

        moduleModel.setRemark(StringUtils.trimToEmpty(module.getRemark()));
        moduleModel.setParentId(module.getParentId());
        moduleModel.setState(Const.ENABLED);
        moduleModel.setCreatorId(SessionManager.getUserId());
        moduleModel.setCreateDate(new Date());
        moduleModel.setPaasId(SessionManager.getAccountId());

        if (moduleModel.getParentId() < 0)
            moduleModel.setParentId(0);

        moduleMapper.add(moduleModel);

        return moduleModel;
    }

    /**
     * 修改模块
     */
    private ModuleModel update(Module module) {
        if (module == null)
            throw new ResultException(Const.MSG_CODE_PARAMMISSING);

        ModuleModel moduleModel = getById(module.getId());
        ModuleModel moduleTemp;

        if (moduleModel == null)
            throw new ResultException(Const.MSG_CODE_NOTEXIST, "模块信息不存在");

        if (module.getCode() != null) {
            moduleModel.setCode(StringUtils.trimToEmpty(module.getCode()));
            moduleTemp = getByCode(moduleModel.getCode());
            if (moduleTemp != null && moduleTemp.getId() != module.getId())
                throw new ResultException(10001, "模块编码已存在");
        }

        if (module.getName() != null) {
            moduleModel.setName(StringUtils.trimToEmpty(module.getName()));
            moduleTemp = getByName(moduleModel.getName());
            if (moduleTemp != null && moduleTemp.getId() != module.getId())
                throw new ResultException(10002, "模块名称已存在");
        }

        if (StringUtils.isBlank(module.getCode()) && StringUtils.isBlank(module.getName()))
            throw new ResultException(Const.MSG_CODE_PARAMINVALID, "编码和名称不能同时为空");

        if (module.getRemark() != null)
            moduleModel.setRemark(StringUtils.trimToEmpty(module.getRemark()));

        if (module.getParentId() != 0)
            moduleModel.setParentId(module.getParentId());

        moduleModel.setUpdatorId(SessionManager.getUserId());
        moduleModel.setUpdateDate(new Date());

        if (moduleModel.getParentId() < 0)
            moduleModel.setParentId(0);

        moduleMapper.update(moduleModel);

        return moduleModel;
    }

    @Override
    public ModuleModel delete(int id) {
        ModuleModel moduleModel = getById(id);
        if (moduleModel != null) {
            // 判断是否存在子模块
            List<ModuleModel> subModules = getByParentId(moduleModel.getId());
            if (subModules != null && subModules.size() > 0)
                throw new ResultException(10001, "存在子模块，不允许删除");
            // 删除
            deleteInner(moduleModel, false);
        }
        return moduleModel;
    }

    @Override
    public ModuleModel forceDelete(int id) {
        ModuleModel moduleModel = getById(id);
        if (moduleModel != null) {
            deleteInner(moduleModel, true);
        }
        return moduleModel;
    }

    /**
     * 删除模块（内部方法）
     * @param moduleModel 想要删除的模块
     * @param deep 是否删除子模块（递归删除子模块）
     */
    private void deleteInner(ModuleModel moduleModel, boolean deep) {
        if (deep) {
            List<ModuleModel> subModules = getByParentId(moduleModel.getId());
            if (subModules != null && subModules.size() > 0) {
                for (ModuleModel module: subModules) {
                    deleteInner(module, true);
                }
            }
        }

        // 删除授权信息
        privilegeMapper.deleteByModuleId(moduleModel.getId());

        // 删除模块
        moduleModel.setState(Const.DISABLED);
        moduleModel.setUpdatorId(SessionManager.getUserId());
        moduleModel.setUpdateDate(new Date());
        moduleMapper.delete(moduleModel);
    }

    /**
     * 查询
     * @param params 参数：
     *      -param name 按名称查询
     *      -param nameLike 按名称模糊查询
     *      -param code 按编码查询
     *      -param codeLike 按编码模糊查询
     *      -param parentId 上级模块编号
     * @param pageInfo 分页信息
     * @return 返回模块列表
     */
    @Override
    public List<ModuleModel> find(Map<String, Object> params, PageInfo pageInfo) {
        Selector selector = Selector.build(pageInfo);

        if (params != null) {
            selector.addFilterNotBlank("name", params.get("name"));
            selector.addFilterNotBlank("name", params.get("nameLike"), Oper.LIKE);
            selector.addFilterNotBlank("code", params.get("code"));
            selector.addFilterNotBlank("code", params.get("codeLike"), Oper.LIKE);

            Integer parentId = params.get("parentId") != null ? (Integer)params.get("parentId") : null;
            if (parentId != null && parentId >= 0)
                selector.addFilter("parentId", parentId);
        }

        selector.addFilter("state", 0, Oper.GT);
        if (!SessionManager.isPlatform())
            selector.addFilter("paasId", SessionManager.getAccountId());

        if (pageInfo != null)
            pageInfo.setTotal(moduleMapper.countFind(selector));

        return moduleMapper.find(selector);
    }

}
