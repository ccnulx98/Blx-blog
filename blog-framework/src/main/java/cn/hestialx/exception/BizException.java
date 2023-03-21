package cn.hestialx.exception;

import cn.hestialx.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixu
 * @create 2023-02-28-16:30
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = AppHttpCodeEnum.SYSTEM_ERROR.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(AppHttpCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getMsg();
    }


}
