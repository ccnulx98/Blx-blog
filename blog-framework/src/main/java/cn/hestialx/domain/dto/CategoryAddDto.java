package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-16-17:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddDto {
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态0:正常,1禁用
     */
    private String status;
}
