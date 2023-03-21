package cn.hestialx.aspect;

import cn.hestialx.annotation.SystemLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author lixu
 * @create 2023-03-01-20:08
 */

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("@annotation(cn.hestialx.annotation.SystemLog)")
    public void entrypoint(){

    }

    @Around("entrypoint()")
    public Object aroundHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        try {

            beforeExecute(joinPoint);
            //获取方法执行结果
            res = joinPoint.proceed();
            afterExecute(res);

        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return res;
    }

    private void afterExecute(Object res) throws JsonProcessingException {
        // 打印出参
        log.info("Response       : {}", objectMapper.writeValueAsString(res));
    }

    public void beforeExecute(ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        HttpServletRequest request = getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = (SystemLog)signature.getMethod().getAnnotation(SystemLog.class);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", signature.getDeclaringType(),signature.getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}",objectMapper.writeValueAsString(joinPoint.getArgs()));
    }

    public HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }


}
