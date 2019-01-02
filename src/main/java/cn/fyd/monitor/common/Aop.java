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
     * 验证用户是否登录
     * 验证是否操作本人数据
     * @param pjp 连接点
     * @param @IsLogin 使用对应注解(@IsLogin)的方法才进行验证
     * @return 通用格式返回
     * @throws Throwable proceed方法抛出
     */
    @Around("@annotation(cn.fyd.annotation.IsLogin)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        int order=-1;
        for (int i=0; i<args.length; i++) {
            // 判断参数中是否存在HttpServletRequest类型的参数
            if (args[i] instanceof HttpServletRequest) {
                order=i;
            }
        }
        // 如果没有HttpServletRequest类型参数则不需要验证是否登录
        if (order==-1) {
            return Response.failed(WRONG_DATA).toString();
        }
        HttpServletRequest request = (HttpServletRequest) args[order];
        HttpSession session = request.getSession();
        if (session.getAttribute(USER_BEAN)==null) {
            return Response.failed(USER_NOT_LOGIN).toString();
        }
        // 获取session中的用户信息
        User userBean = (User) request.getSession().getAttribute(USER_BEAN);
        // 获取第一个参数
        Object param = args[0];
        String userId = null;
        if (param instanceof String) {
            userId = (String) param;
        } else if (param instanceof User) {
            userId = ((User) param).getUserId();
        }
        // 验证是否是登录用户操作本人数据
        if (!userBean.getUserId().equals(userId)) {
            return Response.failed(WRONG_USER).toString();
        }
        return pjp.proceed(args);
    }
}
