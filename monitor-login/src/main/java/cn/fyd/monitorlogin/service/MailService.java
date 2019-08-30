package cn.fyd.monitorlogin.service;

import cn.fyd.common.MonitorException;

import java.text.ParseException;

/**
 * 邮件相关的服务层
 * @author fanyidong
 * @date Created in 2018-12-14
 */
public interface MailService {

    /**
     * 发送邮件
     * @param email 收件人邮箱地址
     * @param subject 邮件主题
     * @param text 邮件内容
     * @throws MonitorException
     * @throws ParseException
     */
    void sendEmail(String email, String subject, String text) throws MonitorException, ParseException;
}
