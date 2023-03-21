package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.CommentDto;
import cn.hestialx.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 天罡剑f
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2023-02-27 12:04:52
*/
public interface CommentService extends IService<Comment> {

    ResponseResult listComments(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult saveComment(CommentDto commentDto);
}
