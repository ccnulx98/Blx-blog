package cn.hestialx.handle.exception;

import cn.hestialx.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-02-27-11:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException {
    private int code;
    private String message;
    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMsg();
    }
}
