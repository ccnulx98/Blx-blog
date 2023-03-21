package cn.hestialx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lixu
 * @create 2023-03-09-20:22
 */
@SpringBootApplication
@MapperScan("cn.hestialx.mapper")
@EnableTransactionManagement
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
    }
}
