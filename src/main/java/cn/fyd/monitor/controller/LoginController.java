package cn.fyd.monitor.controller;

import cn.fyd.annotation.IsLogin;
import cn.fyd.common.Response;
import cn.fyd.model.ResetDto;
import cn.fyd.model.User;
import cn.fyd.monitor.remote.LoginRemote;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static cn.fyd.common.Constant.*;

/**
 * 登录服务控制层
 * @author fanyidong
 * @date Created in 2018-12-18
 */
@RestController
public class LoginController {

    private static Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    LoginRemote loginRemote;

    @PostMapping("/login")
    public String login(String account, String password, HttpServletRequest request) {
        // 从子服务获取返回内容
        Response<User> res = loginRemote.login(account, password);
        if (res == null) {
            return Response.failed(RESPONSE_EMPTY).toString();
        }
        if (!res.isSuccess()) {
            return res.toString();
        }
        // 获得登录后的用户信息
        User user = JSON.parseObject(JSON.toJSONString(res.getData()), User.class);
        if (user == null) {
            return Response.failed(WRONG_DATA).toString();
        }
        // 将用户信息存入session
        HttpSession session = request.getSession();
        session.setAttribute(USER_BEAN, user);
        logger.info(USER_BEAN + "已存入Session*****sessionId:" + session.getId() + user.toString());
        res.setData(null);
        return res.toString();
    }

    @IsLogin
    @PostMapping("/userInfo")
    public String login(String userId, HttpServletRequest request) {
        Response res = loginRemote.userInfo(userId);
        if (res.getData() == null) {
            return Response.failed(RESPONSE_EMPTY).toString();
        }
        return res.toString();
    }

    @PostMapping("/regist")
    public String regist(User newUser) {
        Response res = loginRemote.regist(newUser);
        return res.toString();
    }

    @IsLogin
    @PostMapping("/edit")
    public String edit(User newUser, HttpServletRequest request) {
        Response res = loginRemote.edit(newUser);
        return res.toString();
    }

    @PostMapping("/sendEmail")
    public String sendEmail(String email) {
        return loginRemote.sendEmail(email).toString();
    }

    @PostMapping("/reset")
    public String reset(ResetDto dto) {
        return loginRemote.reset(dto).toString();
    }
}
