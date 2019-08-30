package cn.fyd.monitorlogin.service.impl;

import cn.fyd.common.MonitorException;
import cn.fyd.model.Mail;
import cn.fyd.model.User;
import cn.fyd.monitorlogin.dao.MailDao;
import cn.fyd.monitorlogin.dao.UserDao;
import cn.fyd.monitorlogin.service.MailService;
import cn.fyd.util.CheckUtils;
import cn.fyd.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;

import static cn.fyd.common.Constant.*;

/**
 * 邮件相关服务层实现类
 * @author fanyidong
 * @date Created in 2018-12-14
 */
@Service
public class MailServiceImpl implements MailService {

    private static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private MailDao mailDao;
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String email, String subject, String text) throws MonitorException {
        log.info("开始发送邮件 -> 邮箱地址:" + email);
        // 验证邮箱格式
        if (!StringUtils.isEmpty(email)&&!CheckUtils.checkEmail(email)) {
            throw new MonitorException(WRONG_MAIL);
        }
        // 验证邮箱是否存在
        User resUser = userDao.queryByEmail(email);
        if (resUser == null) {
            throw new MonitorException(MAIL_NOT_REGIST);
        }
        // 生成安全码
        String secretKey = new String(RandomUtils.randomCode());
        // 发送邮件，新建邮件对象
        SimpleMailMessage newMessage = new SimpleMailMessage();
        // 如果是找回密码
        if (FIND_PASSWORD.equals(subject)) {
            // 新建Mail对象
            Mail mail = new Mail();
            // 验证上一封邮件是否存在或过期
            Mail lastMail = mailDao.queryByUserIdOrderByOutTime(resUser.getUserId());
            if (lastMail != null) {
                // 未过期
                if (!CheckUtils.isEmailOutTime(lastMail.getOutTime())) {
                    throw new MonitorException(LINK_NOT_EXPIRED);
                }
            }
            // mail对象赋值
            mail.setUserId(resUser.getUserId());
            mail.setValidationCode(secretKey);
            Calendar now = Calendar.getInstance();
            // 设置邮件到期时间为半个小时
            now.add(Calendar.MINUTE, 30);
            mail.setOutTime(now.getTime());
            // 插入数据库
            mailDao.saveMail(mail);

            // 设置邮件主题
            newMessage.setSubject(MAIL_SUBJECT + " - " + FIND_PASSWORD);
            // 设置邮件内容
            newMessage.setText(text + secretKey);
        } else if (WORNING.equals(subject)){
            // 警告邮件
            newMessage.setSubject(MAIL_SUBJECT + " - " + WORNING);
            newMessage.setText(text);
        }
        // 设置发件人
        newMessage.setFrom(MONITOR + " <" + sender + ">");
        // 设置收件人
        newMessage.setTo(email);
        mailSender.send(newMessage);
        log.info("邮件发送成功 -> 邮件实体类内容：" + newMessage);
    }
}
