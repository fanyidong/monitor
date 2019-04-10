package cn.fyd.monitor.common;

import cn.fyd.common.Response;
import cn.fyd.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static cn.fyd.common.Constant.*;

/**
 * aop
 * @author fanyidong
 * @date 2018-12-11
 */

@Aspect
@Component
public class Aop {

    /**
     * 使用IsLogin注解需要做的处理
     * 功能点:1、验证用户是否登录
     *       2、验证是否操作本人数据
     * @param pjp 连接点
     * @param @IsLogin 使用对应注解(@IsLogin)的方法才进行验证
     * @return 通用格式返回
     * @throws Throwable proceed方法抛出
     */
    @Around("@annotation(cn.fyd.annotation.IsLogin)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        int orderOfHttpServletRequest=-1;
        for (int i=0; i<args.length; i++) {
            // 判断参数中是否存在HttpServletRequest类型的参数
            if (args[i] instanceof HttpServletRequest) {
                orderOfHttpServletRequest = i;
            }
        }
        // 如果没有HttpServletRequest类型参数则不需要验证是否登录
        if (orderOfHttpServletRequest==-1) {
            return Response.failed(WRONG_DATA).toString();
        }
        HttpServletRequest request = (HttpServletRequest) args[orderOfHttpServletRequest];
        // 获取session
        HttpSession session = request.getSession();
        // 若session中没有userBean变量则报用户未登录的错
        User userBean = (User) session.getAttribute(USER_BEAN);
        if (userBean == null) {
            return Response.failed(USER_NOT_LOGIN).toString();
        }
        return pjp.proceed(args);
    }
}
