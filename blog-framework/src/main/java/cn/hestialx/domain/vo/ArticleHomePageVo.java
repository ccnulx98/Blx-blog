package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lixu
 * @create 2023-02-17-17:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHomePageVo {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 所属分类id
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     *
     */
    private Date createTime;

}
