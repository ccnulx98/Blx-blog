package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.UserAddDto;
import cn.hestialx.domain.dto.UserListDto;
import cn.hestialx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-20-10:32
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult listAllUserPages(int pageSize, int pageNum, UserListDto userListDto){
        return userService.listAllUserPages(pageSize,pageNum,userListDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserAddDto userAddDto){
        return userService.addUser(userAddDto);
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteUser(@PathVariable("ids") List<Long> ids){
        return userService.deleteUsers(ids);
    }

    @GetMapping("{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserAddDto userAddDto){
        return userService.updateUser(userAddDto);
    }
}
