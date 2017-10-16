package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.RoleModel;

/**
 * 角色
 * Created by shicy on 2017/10/15.
 */
@Mapper
@SuppressWarnings("unused")
public interface RoleMapper extends BaseMapper<RoleModel> {

    RoleModel getByName(String name, int paasId);

}
