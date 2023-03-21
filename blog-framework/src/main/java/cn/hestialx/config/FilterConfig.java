package cn.hestialx.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lixu
 * @create 2023-02-28-10:55
 */
@Configuration
public class FilterConfig {
    @Bean
    FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>(new Myfilter());
        filter.addUrlPatterns("/*");
        filter.setName("hello world!");
        filter.setOrder(0);
        return filter;
    }
}
class Myfilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("hello world Filter!");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
