package org.scy.priv.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scy.common.ds.mybatis.BaseMapper;
import org.scy.priv.model.LoginRecordModel;

/**
 * 登录记录
 * Created by shicy on 2017/10/18.
 */
@Mapper
public interface LoginRecordMapper extends BaseMapper<LoginRecordModel> {

}
