package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-20-9:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectVo {
    private Long id;
    /**
     * 菜单名称
     */
    private String label;
    /**
     * 父菜单ID
     */
    private Long parentId;

    private List<MenuTreeSelectVo> children;
}
