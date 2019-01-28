package cn.fyd.monitor.controller;

import cn.fyd.annotation.IsLogin;
import cn.fyd.annotation.Log;
import cn.fyd.common.Response;
import cn.fyd.model.ResetDto;
import cn.fyd.model.User;
import cn.fyd.monitor.remote.LoginRemote;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginRemote loginRemote;

    @Log(name = "login", type = "query")
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
        log.info(USER_BEAN + "sessionId ==> " + session.getId() + " ==> " + user.toString());
        // 将用户密码置空
        user.setPassword(null);
        // 登录成功后返回用户信息
        res.setData(user);
        return res.toString();
    }

    @Log(name = "userInfo", type = "query")
    @IsLogin
    @PostMapping("/userInfo")
    public String userInfo(String userId, HttpServletRequest request) {
        Response res = loginRemote.userInfo(userId);
        if (res.getData() == null) {
            return Response.failed(RESPONSE_EMPTY).toString();
        }
        return res.toString();
    }

    @Log(name = "regist", type = "add")
    @PostMapping("/regist")
    public String regist(User newUser) {
        Response res = loginRemote.regist(newUser);
        return res.toString();
    }

    @Log(name = "edit", type = "modify")
    @IsLogin
    @PostMapping("/edit")
    public String edit(User newUser, HttpServletRequest request) {
        // 验证部分参数是否为空
        if (StringUtils.isEmpty(newUser.getUserId())) {
            return Response.failed(EMPTY_PARAMS).toString();
        }
        Response res = loginRemote.edit(newUser);
        return res.toString();
    }

    @Log(name = "sendEmail", type = "query")
    @PostMapping("/sendEmail")
    public String sendEmail(String email) {
        return loginRemote.sendEmail(email).toString();
    }

    @Log(name = "reset", type = "modify")
    @PostMapping("/reset")
    public String reset(ResetDto dto) {
        return loginRemote.reset(dto).toString();
    }
}
