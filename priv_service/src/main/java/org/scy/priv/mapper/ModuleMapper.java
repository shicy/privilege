package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.ModuleModel;

import java.util.List;

/**
 * 模块
 * Created by shicy on 2017/10/18.
 */
@Mapper
public interface ModuleMapper extends BaseMapper<ModuleModel> {

    ModuleModel getByCode(String code, int paasId);
    ModuleModel getByName(String name, int paasId);

    List<ModuleModel> getByIds(int[] ids, int paasId);
    List<ModuleModel> getByCodes(String[] codes, int paasId);
    List<ModuleModel> getByNames(String[] names, int paasId);
    List<ModuleModel> getByParentId(int parentId, int paasId);

}
