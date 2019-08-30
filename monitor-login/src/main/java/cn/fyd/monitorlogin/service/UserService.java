package cn.fyd.monitorlogin.service;

import cn.fyd.common.MonitorException;
import cn.fyd.model.ResetDto;
import cn.fyd.model.User;

import java.text.ParseException;

/**
 * User表相关的服务接口类
 * @author fanyidong
 * @date 2018-12-11
 */
public interface UserService {

    /**
     * 登录服务接口
     * @param account 账户
     * @param password 密码
     * @throws MonitorException
     * @return cn.fyd.model.User
     */
    User login(String account, String password) throws MonitorException;

    /**
     * 修改/新增 用户
     * @param user
     * @throws MonitorException
     */
    void applyUser(User user) throws MonitorException;

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return
     * @throws MonitorException
     */
    User getUserInfo(String userId) throws MonitorException;

    /**
     * 重设密码
     * @param dto
     * @throws MonitorException
     * @throws ParseException
     */
    void reset(ResetDto dto) throws MonitorException, ParseException;
}
