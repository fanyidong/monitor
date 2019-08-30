package cn.fyd.monitorlogin.common;

import cn.fyd.common.MonitorException;
import cn.fyd.common.Response;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.fyd.common.Constant.*;

/**
 * 全局异常处理
 * @author fanyidong
 * @date Created in 2018-12-18
 */
@RestControllerAdvice
public class MonitorAdvice {

    private static Logger log = LoggerFactory.getLogger(MonitorAdvice.class);

    /**
     * 拦截捕捉异常 Exception.class
     * @param e 所有异常
     * @return java.lang.String
     */
    @ExceptionHandler(value = Exception.class)
    public Response monitorErrorHandler(Exception e) {
        if (e instanceof MonitorException) {
            log.error(LOGIN_SERVICE + e.getClass() + e.getMessage());
            return Response.failed(e.getMessage());
        } else if (e instanceof SchedulerException){
            log.error(JOB_SERVICE + e.getClass() + e.getMessage());
            return Response.failed(QUARTZ_ERROR);
        } else {
            log.error(LOGIN_SERVICE + e.getMessage(), e);
            return Response.failed(SYSTEM_ERROR);
        }
    }

}
