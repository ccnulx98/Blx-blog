package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lixu
 * @create 2023-02-20-16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;

    /**
     * 标题
     */
    private String title;


    /**
     * 文章内容
     */
    private String content;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 所属分类id
     */
    private String categoryName;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     *
     */
    private Date createTime;

}
