package cn.hestialx.runner;

import cn.hestialx.constants.ArticleConstants;
import cn.hestialx.domain.dto.ArticleViewCountDto;
import cn.hestialx.entity.Article;
import cn.hestialx.service.ArticleService;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-02-15:44
 *
 * springboot 启动时加载文章访问量到reids
 *
 */
@Component
public class ArticleViewCountRunner implements CommandLineRunner {

    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        //查询文章列表
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ArticleConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(queryWrapper);
        //将文章访问量信息存入redis
        for (Article article : list) {
            String key = ArticleConstants.REDIS_VIEWCOUNT+article.getId();
            redisCache.setCacheObject(key,article.getViewCount());
        }
    }
}
