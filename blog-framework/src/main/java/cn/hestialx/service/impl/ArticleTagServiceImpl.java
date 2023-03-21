package cn.hestialx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.ArticleTag;
import cn.hestialx.service.ArticleTagService;
import cn.hestialx.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author 天罡剑f
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-03-15 18:02:54
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




