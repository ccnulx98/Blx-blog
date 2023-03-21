package cn.hestialx.handle.exception;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

/**
 * @author lixu
 * @create 2023-02-27-11:08
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandle(SystemException e){
      log.error("错误：{}",e);
      return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validExecptionHandle(MethodArgumentNotValidException e){
        log.error("valid错误：{}",e);
        log.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"输入参数不合法！");
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandle(Exception e){
        log.error("错误！：{}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,e.getMessage());
    }

}
