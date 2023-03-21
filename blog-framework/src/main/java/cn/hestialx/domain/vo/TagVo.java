package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-15-17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;
    /**
     * 标签名
     */
    private String name;
}
