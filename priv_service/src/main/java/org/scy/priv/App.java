package org.scy.priv;

import org.scy.common.BaseApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 授权系统服务
 * Created by shicy on 2017/8/28
 */
@SpringBootApplication(scanBasePackages = {"org.scy"})
@EnableFeignClients(basePackages = {"org.scy"})
public class App extends BaseApplication {

    /**
     * 主函数
     */
    public static void main(String[] args) throws Exception {
        BaseApplication.startup(App.class, args);
    }

    @Override
    protected String getDbScriptResource() {
        return "org/scy/priv/scripts";
    }

}
