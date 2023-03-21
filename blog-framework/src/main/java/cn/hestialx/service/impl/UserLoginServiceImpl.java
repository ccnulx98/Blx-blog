package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.domain.vo.UserLoginInfo;
import cn.hestialx.domain.vo.UserLoginVo;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.service.UserLoginService;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.JWTUtils;
import cn.hestialx.utils.RedisCache;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixu
 * @create 2023-02-21-16:25
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult login(String username, String password) {
        //创建客户端输入的 用户名密码 组成的token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        //使用配置好的SpringSecurity 验证用户输入token的合法性(验证失败则返回null)
        Authentication authenticate = authenticationManager.authenticate(token);
        if(ObjectUtils.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误,验证失败！");
        }
        //获取认证成功的用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        /**
         * 1、用户信息存入redis
         * 2、生成jwt
         * 3、封装返回信息
         */
        redisCache.setCacheObject(SystemConstants.BLOG_USER_REDIS_PREFIX+loginUser.getUserInfo().getId(),loginUser);

        String jwt = JWTUtils.createJWT(loginUser.getUserInfo().getId().toString(), "json", JWTUtils.JWT_TTL);

        UserLoginInfo userLoginInfo = BeanCopyUtils.copyBean(loginUser.getUserInfo(), UserLoginInfo.class);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserInfo(userLoginInfo);
        userLoginVo.setToken(jwt);

        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //删除redis中用户登录信息
        Long userId = loginUser.getUserInfo().getId();
        redisCache.deleteObject(SystemConstants.BLOG_USER_REDIS_PREFIX+userId);
        return ResponseResult.okResult("登出成功！");
    }
}
