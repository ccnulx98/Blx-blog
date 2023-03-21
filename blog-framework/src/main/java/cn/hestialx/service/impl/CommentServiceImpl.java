package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.CommentConstants;
import cn.hestialx.domain.dto.CommentDto;
import cn.hestialx.domain.vo.CommentVo;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.entity.User;
import cn.hestialx.enums.AppHttpCodeEnum;
import cn.hestialx.mapper.UserMapper;
import cn.hestialx.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Comment;
import cn.hestialx.service.CommentService;
import cn.hestialx.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 天罡剑f
* @description 针对表【sg_comment(评论表)】的数据库操作Service实现
* @createDate 2023-02-27 12:04:52
*/
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult listComments(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //1、使用MybatisPlus编写分页
        log.info("pagesize:{}",pageSize);
        log.info("pageNum:{}",pageNum);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotNull(articleId),Comment::getArticleId,articleId);
        queryWrapper.eq(commentType.equals(CommentConstants.COMMENT_TYPE_LINK),Comment::getType,CommentConstants.COMMENT_TYPE_LINK);
        queryWrapper.eq(Comment::getRootId, CommentConstants.DEFAULT_ROOT_ID);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Comment> comments = page.getRecords();
        List<CommentVo> commentVos = toCommentVoList(comments);
        //设置二级回复评论
        setChildrenComment(commentVos);
        PageVo pageVo = new PageVo();
        pageVo.setRows(commentVos);
        pageVo.setTotal(page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult saveComment(CommentDto commentDto) {
        Comment comment = BeanCopyUtils.copyBean(commentDto, Comment.class);
        boolean save = save(comment);
        if(!save){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"系统错误，评论失败！");
        }
        return ResponseResult.okResult("评论成功！");
    }

    public List<CommentVo> toCommentVoList(List<Comment> comments){
        List<CommentVo> commentVos = BeanCopyUtils.copyBean(comments, CommentVo.class);
        commentVos.forEach(temp->{
            User own = userMapper.selectById(temp.getCreateBy());
            if(ObjectUtils.isNotNull(own)){
                temp.setUsername(own.getNickName());
                temp.setAvatar(own.getAvatar());
            }
            User toCommentUser = userMapper.selectById(temp.getToCommentUserId());
            if(ObjectUtils.isNotNull(toCommentUser)){
                temp.setToCommentUserName(toCommentUser.getNickName());
            }
        });
        return commentVos;
    }
    public void setChildrenComment(List<CommentVo> commentVos){
        commentVos.forEach(temp->{
            LambdaQueryWrapper<Comment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Comment::getRootId,temp.getId());
            List<Comment> list = list(queryWrapper1);
            List<CommentVo> commentVos1 = toCommentVoList(list);
            temp.setChildren(commentVos1);
        });
    }

}




