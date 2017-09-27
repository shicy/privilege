package org.scy.priv.config;

import org.scy.priv.interceptor.AccessTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置
 * Created by shicy on 2017/9/27.
 */
@Configuration
@SuppressWarnings("unused")
public class PrivMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        // AccessToken 拦截器
        registry.addInterceptor(new AccessTokenInterceptor()).addPathPatterns("/**");
    }

}
