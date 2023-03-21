package cn.hestialx.handle.security;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.utils.WebUtils;
import cn.hestialx.enums.AppHttpCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lixu
 * @create 2023-02-25-11:03
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            result =  ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());

        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        WebUtils.renderString(response,objectMapper.writeValueAsString(result));
    }
}
