package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.AdminLoginDto;
import cn.hestialx.domain.dto.UserLoginDto;
import cn.hestialx.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixu
 * @create 2023-03-10-11:14
 */
@RestController
@RequestMapping()
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    public ResponseResult adminLogin(@RequestBody AdminLoginDto adminLoginDto){
        return adminLoginService.login(adminLoginDto.getUserName(),adminLoginDto.getPassword());
    }

    @GetMapping("/getInfo")
    public ResponseResult getUserInfo(){
        return adminLoginService.getInfo();
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        return adminLoginService.getRouters();
    }

    @PostMapping("/user/logout")
    public ResponseResult adminLogout(){
        return adminLoginService.logout();
    }

}
