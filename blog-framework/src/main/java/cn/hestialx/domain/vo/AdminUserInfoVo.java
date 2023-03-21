package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-10-23:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;
}
