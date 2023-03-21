package cn.hestialx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lixu
 * @create 2023-02-15-17:15
 */
@MapperScan("cn.hestialx.mapper")
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableConfigurationProperties
public class BlogViewApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogViewApplication.class,args);
    }
}
