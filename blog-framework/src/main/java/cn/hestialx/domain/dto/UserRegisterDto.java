package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author lixu
 * @create 2023-03-01-16:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class UserRegisterDto {
    /**
     * 用户名
     */
    @Pattern(regexp="^\\S+$",message = "用户名不能包含空白")
    @Size(min = 4,message = "用户名不能少于4位")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    @Pattern(regexp="^\\S+$",message = "用户昵称不能包含空白")
    @Size(min = 4,message = "用户昵称不能少于4位")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 密码
     */
    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
}
