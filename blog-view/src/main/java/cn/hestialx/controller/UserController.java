package cn.hestialx.controller;

import cn.hestialx.annotation.SystemLog;
import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.UserRegisterDto;
import cn.hestialx.domain.vo.UserLoginInfo;
import cn.hestialx.entity.User;
import cn.hestialx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lixu
 * @create 2023-02-28-15:03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult registerUser(@Valid @RequestBody UserRegisterDto user){
        return userService.register(user);
    }
}
