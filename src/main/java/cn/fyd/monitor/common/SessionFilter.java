package cn.fyd.monitor.common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static cn.fyd.common.Constant.*;

/**
 * session过滤器验证是否登录
 * @author fanyidong
 * @date Created in 2019-01-21
 */
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    /**
     * 不需要过滤的路径
     */
    private String[] notFilterPage = new String[]{"/","/login.do","/regist.do","/forgot.do"};

    /**
     * 是否需要过滤
     * @param uri 请求地址
     * @return boolean
     */
    private boolean isNeedFilter(String uri) {
        // 如果请求地址不包含.do则不需要过滤，直接放行
        if (!uri.contains(DO)) {
            return false;
        }
        // 如果url是不需要过滤的路径直接放行
        for (String includeUrl : notFilterPage) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        // 获得请求地址
        String uri = request.getRequestURI();
        //是否需要过滤
        if (!isNeedFilter(uri)) {
            //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // session中包含user对象,则是登录状态
            if(session!=null&&session.getAttribute(USER_BEAN) != null){
                // 过滤器放行
                filterChain.doFilter(request, response);
            }else{
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if(requestType!=null && XMLHTTPREQUEST.equals(requestType)){
                    response.getWriter().write(USER_NOT_LOGIN);
                }else{
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    response.sendRedirect("/login.do");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
