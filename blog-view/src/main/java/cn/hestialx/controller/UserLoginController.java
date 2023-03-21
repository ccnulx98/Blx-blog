package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.UserLoginDto;
import cn.hestialx.enums.AppHttpCodeEnum;
import cn.hestialx.handle.exception.SystemException;
import cn.hestialx.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixu
 * @create 2023-02-21-16:16
 */
@RestController
@RequestMapping
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody UserLoginDto userLoginDto){
        if(!StringUtils.hasText(userLoginDto.getUsername())){
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR.getCode(),"用户名为空!");
        }
        return userLoginService.login(userLoginDto.getUsername(),userLoginDto.getPassword());
    }

    @PostMapping("/logout")
    public ResponseResult userLogout(){
        return userLoginService.logout();
    }

}
