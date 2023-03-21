package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-15-16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdateDto {
    private Long id;
    /**
     * 标签名
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
}
