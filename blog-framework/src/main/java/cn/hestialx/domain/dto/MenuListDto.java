package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-19-11:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListDto {
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;
}
