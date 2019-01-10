package cn.fyd.monitor.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理跨域访问的拦截器
 * @author fanyidong
 * @date Created in 2018-12-26
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LogManager.getLogger(RequestInterceptor.class);

    /** options请求方式 */
    private static final String OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("**********处理跨域访问**********");
        System.out.println("测试输出编码");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("X-Powered-By","Jetty");
        String method= request.getMethod();
        if (OPTIONS.equals(method)){
            response.setStatus(200);
            return false;
        }
        return true;
    }
}
