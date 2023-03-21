package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.ArticleAddDto;
import cn.hestialx.domain.dto.ArticleListDto;
import cn.hestialx.domain.vo.ArticleDetailVo;
import cn.hestialx.domain.vo.ArticleHomePageVo;
import cn.hestialx.domain.vo.HotArticleVo;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-02-15 18:12:24
*/
public interface ArticleService extends IService<Article> {
    public abstract List<HotArticleVo> getHotArticles();

    PageVo listHomePageArticle(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<ArticleDetailVo> getArticalDetail(Long id);

    ResponseResult updateViewCound(Long id);

    ResponseResult addArticle(ArticleAddDto article);

    ResponseResult updateArticle(ArticleAddDto article);

    ResponseResult listPages(int pageNum, int pageSize, ArticleListDto articleListDto);

    ResponseResult getArticalById(Long id);

    ResponseResult deleteByIds(List<Long> ids);

    void updateViewsBatchById(ArrayList<Article> articles);
}
