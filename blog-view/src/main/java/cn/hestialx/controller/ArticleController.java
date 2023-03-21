package cn.hestialx.controller;

import cn.hestialx.domain.vo.ArticleDetailVo;
import cn.hestialx.domain.vo.ArticleHomePageVo;
import cn.hestialx.domain.vo.HotArticleVo;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.entity.Article;
import cn.hestialx.common.ResponseResult;
import cn.hestialx.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixu
 * @create 2023-02-15-18:13
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章相关接口")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/articleList")
    @ApiOperation(value = "文章列表",notes = "获取文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小"),
            @ApiImplicitParam(name = "categoryId",value = "文章分类id")
    })
    public ResponseResult listAllArticals(Integer pageNum, Integer pageSize, Long categoryId){
        PageVo page = articleService.listHomePageArticle(pageNum,pageSize,categoryId);
        return ResponseResult.okResult(page);
    }

    @GetMapping("/hotArticleList")
    public ResponseResult<List> listAllHotArticals(){
        List<HotArticleVo> list = articleService.getHotArticles();
        return ResponseResult.okResult(list);
    }

    @GetMapping("{id}")
    public ResponseResult<ArticleDetailVo> getArticalDetails(@PathVariable("id") Long id){
        return articleService.getArticalDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCound(@PathVariable("id") Long id){
        return articleService.updateViewCound(id);
    }
}
