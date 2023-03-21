package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.ArticleAddDto;
import cn.hestialx.domain.dto.ArticleListDto;
import cn.hestialx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-15-17:45
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody ArticleAddDto article){
        return articleService.addArticle(article);
    }

    @PutMapping
    public ResponseResult update(@RequestBody ArticleAddDto article){
        return articleService.updateArticle(article);
    }

    @GetMapping("list")
    public ResponseResult listAllArticlePages(int pageNum, int pageSize, ArticleListDto articleListDto){
        return articleService.listPages(pageNum,pageSize,articleListDto);
    }

    @GetMapping("{id}")
    public ResponseResult listAllArticlePages(@PathVariable("id") Long id){
        return articleService.getArticalById(id);
    }

    @DeleteMapping("{ids}")
    public ResponseResult delete(@PathVariable("ids") List<Long> ids){
        return articleService.deleteByIds(ids);
    }

}
