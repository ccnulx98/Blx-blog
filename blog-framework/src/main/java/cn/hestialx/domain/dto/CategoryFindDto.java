package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-16-16:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFindDto {
    /**
     * 分类名
     */
    private String name;
    /**
     * 状态0:正常,1禁用
     */
    private String status;
}
