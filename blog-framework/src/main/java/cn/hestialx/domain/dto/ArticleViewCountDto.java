package cn.hestialx.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-02-15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewCountDto {
    /**
     *
     */
    private Long id;

    /**
     * 访问量
     */
    private Long viewCount;
}
