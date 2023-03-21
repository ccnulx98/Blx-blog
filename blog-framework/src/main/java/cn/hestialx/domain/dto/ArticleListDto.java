package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-18-18:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    //标题
    private String title;
    //文章摘要
    private String summary;
}
