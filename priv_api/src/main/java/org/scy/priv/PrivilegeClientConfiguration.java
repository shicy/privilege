package org.scy.priv;

import feign.RequestInterceptor;
import org.scy.common.configs.BaseFeignConfiguration;

/**
 * PrivilegeClient拦截器
 */
public class PrivilegeClientConfiguration extends BaseFeignConfiguration {

    public RequestInterceptor requestInterceptor() {
        return getAccessTokenInterceptor(null);
    }

}
