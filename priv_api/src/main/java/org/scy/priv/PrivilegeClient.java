package org.scy.priv;

import org.scy.common.web.controller.HttpResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 授权服务客户端
 * Create by shicy on 2017/9/4.
 */
@FeignClient(name = "priv-service", url = "${app.priv-service.url:/}")
public interface PrivilegeClient {

    @RequestMapping(value = "/account/info/{accountId}", method = RequestMethod.GET)
    HttpResult getAccountInfo(@PathVariable int accountId);

}
