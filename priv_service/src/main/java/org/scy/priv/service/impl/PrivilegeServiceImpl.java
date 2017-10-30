package org.scy.priv.service.impl;

import org.scy.common.web.service.MybatisBaseService;
import org.scy.priv.service.PrivilegeService;
import org.springframework.stereotype.Service;

/**
 * 权限相关服务类
 * Created by shicy on 2017/10/19.
 */
@Service
public class PrivilegeServiceImpl extends MybatisBaseService implements PrivilegeService {

    @Override
    public int deleteByUserId(int userId) {
        return 0;
    }

    @Override
    public int deleteByModuleId(int moduleId) {
        return 0;
    }

}
