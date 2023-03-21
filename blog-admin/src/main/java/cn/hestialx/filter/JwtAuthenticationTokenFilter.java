package cn.hestialx.filter;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.utils.WebUtils;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.enums.AppHttpCodeEnum;
import cn.hestialx.utils.JWTUtils;
import cn.hestialx.utils.RedisCache;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lixu
 * @create 2023-03-10-10:04
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1、获取请求头中token字段的信息
        String token = request.getHeader("token");
        //不存在token直接放行，交给后面的过滤器处理
        if(StringUtils.isBlank(token)){
            filterChain.doFilter(request,response);
            return;
        }
        // 2、校验token信息
        Claims claims = null;
        try {
            claims = JWTUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }
        // 3、 通过解析出的信息查询redis
        String id = claims.getId();
        Object userinfo = redisCache.getCacheObject(SystemConstants.BLOG_ADMIN_REDIS_PREFIX + id);
        // 如果redis中没有数据提示需要登录
        if(ObjectUtils.isNull(userinfo)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, objectMapper.writeValueAsString(result));
            return;
        }
        LoginUser loginUser = objectMapper.convertValue(userinfo, LoginUser.class);
        // 4、 将用户登录信息存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 5、 放行
        filterChain.doFilter(request, response);
    }
}
