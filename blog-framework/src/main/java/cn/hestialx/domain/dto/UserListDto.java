package cn.hestialx.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-20-10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phonenumber;
}
