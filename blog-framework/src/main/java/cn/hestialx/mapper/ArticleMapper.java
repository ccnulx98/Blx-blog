package cn.hestialx.mapper;

import cn.hestialx.domain.vo.ArticleHomePageVo;
import cn.hestialx.domain.vo.HotArticleVo;
import cn.hestialx.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2023-02-15 18:12:24
* @Entity cn.hestialx.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {
    List<HotArticleVo> listAllHotArticle();

    List<ArticleHomePageVo> listHomePageArticle(@Param("pageNum")Integer pageNum,@Param("pageSize") Integer pageSize,@Param("categoryId") Long categoryId);
    int homePageArticlesNum(@Param("categoryId") Long categoryId);

    void updateViewsBatchById(@Param("articles") ArrayList<Article> articles);
}




