package cn.fyd.model;

import cn.fyd.annotation.IsEmpty;

/**
 * 重置密码dto
 * @author fanyidong
 * @date Created in 2018-12-15
 */
public class ResetDto {

    /**
     * 安全码
     */
    @IsEmpty(name = "安全码")
    private String secretKey;

    /**
     * 账号
     */
    @IsEmpty(name = "邮箱")
    private String email;

    /**
     * 密码
     */
    @IsEmpty(name = "密码")
    private String password;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ResetDto{" +
                "secretKey='" + secretKey + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
