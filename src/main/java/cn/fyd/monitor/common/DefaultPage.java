package cn.fyd.monitor.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 设置启动默认页面
 * @author fanyidong
 * @date Created in 2019-01-14
 */
@Configuration
public class DefaultPage extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 设置默认页面为index
        registry.addViewController("/").setViewName("index");
        // 设置ViewController的优先级
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
}
