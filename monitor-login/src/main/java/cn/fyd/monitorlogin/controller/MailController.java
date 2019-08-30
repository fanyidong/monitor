package cn.fyd.monitorlogin.controller;

import cn.fyd.annotation.Log;
import cn.fyd.common.Response;
import cn.fyd.monitorlogin.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.fyd.common.Constant.*;

/**
 * @author fanyidong
 * @date Created in 2018-12-14
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送邮件
     * @param email
     * @return cn.fyd.common.Response
     * @throws Exception
     */
    @Log(name = "发送找回密码安全码邮件")
    @PostMapping("/sendEmail")
    @Transactional(rollbackFor = Exception.class)
    public Response sendEmail(String email) throws Exception {
        mailService.sendEmail(email, FIND_PASSWORD, MAIL_MESS_RESET_PASSWORD);
        return Response.success(SEND_MAIL_SUCCESS);
    }
}
