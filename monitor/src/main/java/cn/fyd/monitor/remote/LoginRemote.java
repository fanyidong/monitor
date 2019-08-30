package cn.fyd.monitor.remote;

import cn.fyd.common.Response;
import cn.fyd.model.ResetDto;
import cn.fyd.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用远程服务(login)接口类
 * @author fanyidong
 * @date Created in 2018-12-27
 */
@FeignClient("${microService.login}")
@Service
public interface LoginRemote {

    /**
     * 登录接口
     * @param account 账户名
     * @param password 密码
     * @return cn.fyd.common.Response
     */
    @PostMapping("/login")
    Response<User> login(@RequestParam("account") String account, @RequestParam("password") String password);

    /**
     * 查询用户个人信息
     * @param userId 59bbdc69-6acc-4828-a3fd-4f3228bd0312
     * @return cn.fyd.common.Response
     */
    @PostMapping("/userInfo")
    Response<User> userInfo(@RequestParam("userId") String userId);

    /**
     * 注册接口
     * @param newUser 注册信息{"account":"dd","password":"1","email":"123@qq.com","mobile":"10086"}
     * @return cn.fyd.common.Response
     */
    @PostMapping("/apply")
    Response regist(@RequestBody User newUser);

    /**
     * 修改信息接口
     * @param newUser 注册信息{"userId":"bf6eb812-4b38-4965-a121-bf297d58447c","account":"dd","password":"1","email":"123@qq.com","mobile":"10086"}
     * @return cn.fyd.common.Response
     */
    @PostMapping("/apply")
    Response edit(@RequestBody User newUser);

    /**
     * 发送邮件接口
     * @param email 123@qq.com
     * @return cn.fyd.common.Response
     */
    @PostMapping("/sendEmail")
    Response sendEmail(@RequestParam("email") String email);

    /**
     * 重置密码
     * @param dto {"secretKey":"60ba","email":"fanyidong@biosan.cn","password":"1"}
     * @return cn.fyd.common.Response
     */
    @PostMapping("/reset")
    Response reset(@RequestBody ResetDto dto);
}
