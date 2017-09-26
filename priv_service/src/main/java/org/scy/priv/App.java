package org.scy.priv;

import org.scy.common.BaseApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 授权系统服务
 * Created by shicy on 2017/8/28
 */
@SpringBootApplication(scanBasePackages = {"org.scy"})
public class App extends BaseApplication {

    /**
     * 主函数
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    protected String getDbScriptResource() {
        return "org/scy/priv/scripts";
    }

}
