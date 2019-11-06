package org.scy.priv;

import feign.RequestInterceptor;
import org.scy.common.configs.BaseFeignConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * PrivilegeClient拦截器
 */
public class PrivilegeClientConfiguration extends BaseFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return getAccessTokenInterceptor(null);
    }

}
