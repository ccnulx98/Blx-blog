package cn.hestialx.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-02-20-17:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String name;

    /**
     *
     */
    private String logo;

    /**
     *
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;
}
