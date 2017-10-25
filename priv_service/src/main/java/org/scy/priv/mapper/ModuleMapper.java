package org.scy.priv.mapper;

import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.ModuleModel;

import java.util.List;

/**
 * 模块
 * Created by shicy on 2017/10/18.
 */
public interface ModuleMapper extends BaseMapper<ModuleModel> {

    ModuleModel getByCode(String code, int paasId);
    ModuleModel getByName(String name, int paasId);

    List<ModuleModel> getByParentId(int parentId, int paasId);
    List<ModuleModel> getByUserId(int userId, int paasId);
    List<ModuleModel> getByGroupId(int groupId, int paasId);
    List<ModuleModel> getByRoleId(int roleId, int paasId);

}
