package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-19-11:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListDto {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
}
