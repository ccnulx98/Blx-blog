package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-15-17:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 描述
     */
    private String description;
}
