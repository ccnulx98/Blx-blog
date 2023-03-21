package cn.hestialx.domain.vo;

import cn.hestialx.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-20-14:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminUpdateVo {
    private UserAdminVo user;
    private List<Role> roles;
    private List<Long> roleIds;
}
