package cn.hestialx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author lixu
 * @create 2023-02-27-12:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 发起评论的用户名称
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;
    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;
    /**
     * 根评论id
     */
    private Long rootId;
    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 所回复的目标评论的userid
     */
    private String toCommentUserName;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;
    /**
     * 子评论
     */
    private List<CommentVo> children;
    /**
     * 头像
     */
    private String avatar;
}
