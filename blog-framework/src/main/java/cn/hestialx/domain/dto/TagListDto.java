package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-14-17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    /**
     * 标签名
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
}
