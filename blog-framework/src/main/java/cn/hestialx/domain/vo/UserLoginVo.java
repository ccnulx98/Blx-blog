package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-02-21-17:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String token;
    private UserLoginInfo userInfo;
}
