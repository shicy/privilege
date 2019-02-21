package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.ModuleModel;

import java.util.List;

/**
 * 模块
 * Created by shicy on 2017/10/18.
 */
@Mapper
public interface ModuleMapper extends BaseMapper<ModuleModel> {

    ModuleModel getByCode(@Param("code") String code, @Param("paasId") int paasId);
    ModuleModel getByName(@Param("name") String name, @Param("paasId") int paasId);

    List<ModuleModel> getByIds(@Param("ids") int[] ids, @Param("paasId") int paasId);
    List<ModuleModel> getByCodes(@Param("codes") String[] codes, @Param("paasId") int paasId);
    List<ModuleModel> getByNames(@Param("names") String[] names, @Param("paasId") int paasId);
    List<ModuleModel> getByParentId(@Param("parentId") int parentId, @Param("paasId") int paasId);

    List<ModuleModel> getByUserId(@Param("userId") int userId);

}
