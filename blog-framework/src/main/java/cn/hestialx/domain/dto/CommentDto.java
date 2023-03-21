package cn.hestialx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-02-27-17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 根评论id
     */
    private Long rootId;
    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;
    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    private String type;

}
