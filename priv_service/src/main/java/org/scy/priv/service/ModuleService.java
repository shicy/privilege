package org.scy.priv.service;

import org.scy.common.ds.PageInfo;
import org.scy.priv.model.Module;
import org.scy.priv.model.ModuleModel;

import java.util.List;
import java.util.Map;

/**
 * 模块相关服务
 * Created by shicy on 2017/10/19.
 */
@SuppressWarnings("unused")
public interface ModuleService {

    /**
     * 根据编号获取模块信息
     */
    ModuleModel getById(int id);

    /**
     * 根据名称获取模块信息，需要名称全匹配
     */
    ModuleModel getByName(String name);

    /**
     * 根据名称获取模块信息，按名称模糊匹配
     */
    List<ModuleModel> getByNameLike(String name);

    /**
     * 根据编码获取模块信息，需要编码全匹配
     */
    ModuleModel getByCode(String code);

    /**
     * 根据编码获取模块信息，按编码模糊匹配
     */
    List<ModuleModel> getByCodeLike(String code);

    /**
     * 根据上级编号获取子模块信息
     */
    List<ModuleModel> getByParentId(int parentId);

    /**
     * 获取所有模块信息
     */
    List<ModuleModel> getAll();

    /**
     * 保存模块信息，新增或修改模块
     * @param module 想要保存的模块信息
     * @return 返回当前模块信息
     */
    ModuleModel save(Module module);

    /**
     * 删除模块，如果存在子模块将删除失败
     * @param id 想要删除的模块编号
     * @return 返回被删除的模块信息
     */
    ModuleModel delete(int id);

    /**
     * 强制删除模块，同事删除子模块
     * @param id 想要删除的模块编号
     * @return 返回被删除的模块信息
     */
    ModuleModel forceDelete(int id);

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
    List<ModuleModel> find(Map<String, Object> params, PageInfo pageInfo);

}
