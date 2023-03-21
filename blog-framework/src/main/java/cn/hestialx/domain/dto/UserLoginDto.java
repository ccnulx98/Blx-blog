package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-02-21-16:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    /**
     * 用户名
     */
    private String username;
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
