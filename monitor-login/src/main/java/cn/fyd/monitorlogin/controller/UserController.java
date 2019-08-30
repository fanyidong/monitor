package cn.fyd.monitorlogin.controller;

import cn.fyd.annotation.Log;
import cn.fyd.common.MonitorException;
import cn.fyd.common.Response;
import cn.fyd.common.ValidFileds;
import cn.fyd.model.ResetDto;
import cn.fyd.model.User;
import cn.fyd.monitorlogin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static cn.fyd.common.Constant.EMPTY_PARAMS;

/**
 * User控制层
 * @author fanyidong
 * @date Created in 2018-12-11
 */
@RestController
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param account 账户名
     * @param password 密码
     * @return cn.fyd.common.Response {"data":User,"message":null,"result":"success"}
     */
    @Log(name = "登录")
    @PostMapping("/login")
    public Response login(String account, String password) throws Exception {
        // 验证参数是否为空
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            throw new MonitorException(EMPTY_PARAMS);
        }
        return Response.success(userService.login(account, password));
    }

    /**
     * 查询用户个人信息
     * @param userId 用户id {"userId":"1"}
     * @return cn.fyd.common.Response
     * @throws Exception
     */
    @Log(name = "查询用户个人信息")
    @PostMapping("/userInfo")
    public Response info(String userId) throws Exception {
        // 验证参数是否为空
        if (StringUtils.isEmpty(userId)) {
            return Response.failed(EMPTY_PARAMS);
        }
        return Response.success(userService.getUserInfo(userId));

    }


    /**
     * 注册 or 修改个人信息
     * @param newUser (注册){"account":"dd","password":"1","email":"123@qq.com","mobile":"10086"}
     *               (修改个人信息){"userId":"bf6eb812-4b38-4965-a121-bf297d58447c","account":"dd","password":"1","email":"123@qq.com","mobile":"10086"}
     * @return cn.fyd.common.Response
     * @throws Exception
     */
    @Log(name = "注册or修改个人信息")
    @PostMapping("/apply")
    @Transactional(rollbackFor = Exception.class)
    public Response apply(@RequestBody User newUser) throws Exception {
        userService.applyUser(newUser);
        return Response.success();
    }

    /**
     * 重置密码
     * @param dto {"secretKey":"60ba","email":"fanyidong@biosan.cn","password":"1"}
     * @return
     * @throws Exception
     */
    @Log(name = "重置密码")
    @PostMapping("/reset")
    @Transactional(rollbackFor = Exception.class)
    public Response reset(@RequestBody ResetDto dto) throws Exception {
        // 验证参数是否为空
        ValidFileds.verificationoColumn(dto);
        userService.reset(dto);
        log.info("日志信息 ==> 修改密码成功");
        return Response.success();
    }
}
