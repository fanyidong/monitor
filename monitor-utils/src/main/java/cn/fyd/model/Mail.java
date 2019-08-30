package cn.fyd.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 发送邮件找回密码 邮件信息存储表 实体类
 * @author fanyidong
 * @date Created in 2018-12-13
 */
public class Mail {

    /**
     * mail表id
     */
    private String mailId;

    /**
     * user表id
     */
    private String userId;

    /**
     * md5加密后的值
     */
    private String validationCode;

    /**
     * 邮件过期时间
     */
    private Date outTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mailId='" + mailId + '\'' +
                ", userId='" + userId + '\'' +
                ", validationCode='" + validationCode + '\'' +
                ", outTime=" + outTime +
                ", createTime=" + createTime +
                '}';
    }
}
