package cn.fyd.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 主程序入口
 * @author fanyidong
 * @date Created in 2018/12/27 22:41
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
public class MonitorApplication {

    private static Logger log = LoggerFactory.getLogger(MonitorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
        log.info("[Monitor主服务]**********启动成功");
    }

}

