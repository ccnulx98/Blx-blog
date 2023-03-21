package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.ArticleConstants;
import cn.hestialx.domain.dto.ArticleAddDto;
import cn.hestialx.domain.dto.ArticleListDto;
import cn.hestialx.domain.dto.ArticleViewCountDto;
import cn.hestialx.domain.vo.*;
import cn.hestialx.entity.ArticleTag;
import cn.hestialx.entity.Category;
import cn.hestialx.mapper.CategoryMapper;
import cn.hestialx.service.ArticleTagService;
import cn.hestialx.service.TagService;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Article;
import cn.hestialx.service.ArticleService;
import cn.hestialx.mapper.ArticleMapper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author 天罡剑f
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-02-15 18:12:24
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ArticleTagService articleTagService;

    @Autowired
    RedisCache redisCache;

    @Override
    public List<HotArticleVo> getHotArticles() {
        //文章访问量从redis中获取
        List<HotArticleVo> hotArticleVos = articleMapper.listAllHotArticle();
        for (HotArticleVo hotArticleVo : hotArticleVos) {
            String viewCount = (String)redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT + hotArticleVo.getId());
            if(viewCount!=null){
                hotArticleVo.setViewCount(Long.parseLong(viewCount));
            }
        }
        return hotArticleVos;
    }

    @Override
    public PageVo listHomePageArticle(Integer pageNum, Integer pageSize, Long categoryId) {
        if(pageNum<1||pageSize<1||categoryId<0){
            return null;
        }
        long total = articleMapper.homePageArticlesNum(categoryId);
        List<ArticleHomePageVo> articleHomePageVos = articleMapper.listHomePageArticle((pageNum-1) * pageSize, pageSize, categoryId);
        for (ArticleHomePageVo articleHomePageVo : articleHomePageVos) {
            String viewCount = (String)redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT + articleHomePageVo.getId());
            if(viewCount!=null){
                articleHomePageVo.setViewCount(Long.parseLong(viewCount));
            }
        }
        PageVo pageVo = new PageVo(articleHomePageVos, total);
        return pageVo;
    }

    @Override
    public ResponseResult<ArticleDetailVo> getArticalDetail(Long id) {
        Article article = articleMapper.selectById(id);
        Category category = categoryMapper.selectById(article.getCategoryId());
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        String viewCount = (String)redisCache.getCacheObject(ArticleConstants.REDIS_VIEWCOUNT + articleDetailVo.getId());
        articleDetailVo.setViewCount(Long.parseLong(viewCount));
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCound(Long id) {
        //查询redis中的对应文章的浏览量
        String key = ArticleConstants.REDIS_VIEWCOUNT+id;

        //这里对访问量的修改操作存在并发问题，多个修改请求同时尽量会导致不准确的问题
        //但是由于访问量业务的可靠性要求不强，因此不做处理，可以容忍此误差
        Long viewcount = Long.parseLong(redisCache.getCacheObject(key));

        /** reids中没有数据，则查询数据库保存到reids
         *  * 在高访问量的情况下，可能有大量用户同时访问
         *  这会发送大量的无效请求到数据库，因此需要使用到互斥锁
         *  1、直接使用java提供的锁机制不太符合当前业务要求，因为我们希望的是不同的
         *  文章可以并发的进行缓存更新操作，只有相同文章需要进行限制。
         *  2、这里我选择使用redis的setIfAbsent(key,value,timeout,TimeUnit) 来设计锁，使用文章id作为key
         *  只有在设置成功时才进行更新缓存操作
         */
        if(viewcount==null){
            String lockKey = ArticleConstants.ARTICLE_VIEWCOUNT_LOCK+id;

            //尝试获取锁
            boolean locked = redisCache.setIfAbsent(lockKey,
                    ArticleConstants.ARTICLE_VIEWCOUNT_LOCK_VALUE,
                    ArticleConstants.ARTICLE_VIEWCOUNT_LOCK_EXPIRE,
                    TimeUnit.SECONDS);
            //获取到锁则查询数据库并更新到缓存
            if(locked){
                try {
                    Article article = getById(id);
                    //如果文章不存在，将文章id和虚拟访问量存入reids并返回。防止缓存穿透攻击
                    if(article==null){
                        redisCache.setCacheObject(key,-1L);
                        return ResponseResult.okResult();
                    }
                    //修改并返回
                    redisCache.setCacheObject(key,article.getViewCount()+1);
                    return ResponseResult.okResult();
                } finally {
                    //解锁
                    redisCache.deleteObject(lockKey);
                }

            }else{
                //没有获取到锁则休眠
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //重新尝试
                updateViewCound(id);
            }

        }

        //更新redis中对应文章的浏览量
        redisCache.setCacheObject(key,viewcount+1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseResult addArticle(ArticleAddDto article) {
        Article article1 = BeanCopyUtils.copyBean(article, Article.class);
        save(article1);
        List<ArticleTag> articleTags = article.getTags().stream().map(a -> new ArticleTag(article1.getId(), a)).collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseResult updateArticle(ArticleAddDto article) {
        Article article1 = BeanCopyUtils.copyBean(article, Article.class);
        updateById(article1);
        List<ArticleTag> articleTags = article.getTags().stream().map(a -> new ArticleTag(article1.getId(), a)).collect(Collectors.toList());
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listPages(int pageNum, int pageSize, ArticleListDto articleListDto) {
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        queryWrapper.like(StringUtils.isNotBlank(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        page(articlePage,queryWrapper);
        PageVo pageVo = new PageVo(articlePage.getRecords(), articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticalById(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        Article one = getById(id);
        ArticleAdminVo articleAdminVo = BeanCopyUtils.copyBean(one, ArticleAdminVo.class);
        List<String> tags = list.stream().map(a -> a.getTagId() + "").collect(Collectors.toList());
        articleAdminVo.setTags(tags);
        return ResponseResult.okResult(articleAdminVo);
    }

    @Override
    public ResponseResult deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ArticleTag::getArticleId,ids);
        articleTagService.remove(queryWrapper);
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public void updateViewsBatchById(ArrayList<Article> articles) {
        articleMapper.updateViewsBatchById(articles);
    }
}




