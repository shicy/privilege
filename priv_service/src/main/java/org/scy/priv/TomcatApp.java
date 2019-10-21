package org.scy.priv;

import org.scy.common.BaseApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

/**
 * 部署到 tomcat 时 SpringBoot 启动类
 * Created by shicy on 2017/11/13
 */
@SuppressWarnings("unused")
public class TomcatApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
    }

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        BaseApplication.setApplication(application);
        return super.run(application);
    }
}
