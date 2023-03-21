package cn.hestialx.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author lixu
 * @create 2023-02-17-16:24
 */
@Data
public class CategoryHomePageVo {
    private Long id;

    /**
     * 分类名
     */
    private String name;
}
