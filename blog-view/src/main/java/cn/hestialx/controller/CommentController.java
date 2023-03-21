package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.CommentConstants;
import cn.hestialx.domain.dto.CommentDto;
import cn.hestialx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixu
 * @create 2023-02-27-12:05
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum, Integer pageSize){
        return commentService.listComments(CommentConstants.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.listComments(CommentConstants.COMMENT_TYPE_LINK,null,pageNum,pageSize);
    }

    @PostMapping()
    public ResponseResult comment(@RequestBody CommentDto commentDto){
        return commentService.saveComment(commentDto);
    }
}
