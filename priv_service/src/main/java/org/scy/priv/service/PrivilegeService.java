package org.scy.priv.service;

import org.scy.priv.model.PrivilegeModel;

/**
 * 权限相关服务
 * Created by shicy on 2017/10/19.
 */
public interface PrivilegeService {

    int deleteByUserId(int userId);
    int deleteByModuleId(int moduleId);

}
