package cn.hestialx.schedule;

import cn.hestialx.constants.ArticleConstants;
import cn.hestialx.entity.Article;
import cn.hestialx.service.ArticleService;
import cn.hestialx.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author lixu
 * @create 2023-03-02-17:21
 */
@Component
public class ArticleSchedule {

    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateViewcountData(){
        String pattern = ArticleConstants.REDIS_VIEWCOUNT+"*";
        //获取前缀为"article:viewcount:"的所有key值
        LinkedHashSet<String> keys = (LinkedHashSet<String>)redisCache.keys(pattern);
        System.out.println(keys);
        ArrayList<Article> articles = new ArrayList<>();
        for (String key : keys) {
            System.out.println("key:"+key);
            String ids = key.substring(key.lastIndexOf(":")+1);
            Long id = Long.parseLong(ids);
            System.out.println("id:"+id);
            Long views = Long.parseLong(redisCache.getCacheObject(key));
            articles.add(new Article(id,views));
            System.out.println(id+":"+views);
        }
        articleService.updateViewsBatchById(articles);
    }
}
