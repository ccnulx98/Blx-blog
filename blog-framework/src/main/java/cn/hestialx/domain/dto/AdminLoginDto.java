package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-10-11:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDto {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 验证码
     */
    private String verificationCode;
}
