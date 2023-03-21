package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-20-9:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAddDto {

    private Long id;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    private List<Long> menuIds;
}
