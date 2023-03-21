package cn.hestialx;

import cn.hestialx.constants.SystemConstants;
import cn.hestialx.controller.ArticleController;
import cn.hestialx.entity.ArticleTag;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.service.ArticleTagService;
import cn.hestialx.service.UserService;
import cn.hestialx.utils.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lixu
 * @create 2023-02-21-18:30
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    RedisCache redisCache;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ArticleTagService articleTagService;
    @Test
    public void test01(){
    }
}
