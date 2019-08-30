package cn.fyd.monitorlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 * EnableEurekaClient注解表明这是一个服务
 * @author fanyidong
 * @date 2018-12-11
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.fyd.monitorlogin.dao")
public class MonitorLoginApplication {

    private static Logger log = LoggerFactory.getLogger(MonitorLoginApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MonitorLoginApplication.class, args);
        log.info("[Monitor-Login子服务]**********启动成功");
    }
}
