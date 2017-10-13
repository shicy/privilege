package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.GroupModel;

/**
 * 用户组
 * Created by shicy on 2017/10/7.
 */
@Mapper
@SuppressWarnings("unused")
public interface GroupMapper extends BaseMapper<GroupModel> {

    GroupModel getByName(String name, int paasId);

}
