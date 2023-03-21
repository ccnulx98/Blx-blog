package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.UserAddDto;
import cn.hestialx.domain.dto.UserListDto;
import cn.hestialx.domain.dto.UserRegisterDto;
import cn.hestialx.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-02-21 14:44:25
*/
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(UserRegisterDto user);

    ResponseResult listAllUserPages(int pageSize, int pageNum, UserListDto userListDto);

    ResponseResult addUser(UserAddDto userAddDto);

    ResponseResult deleteUsers(List<Long> ids);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UserAddDto userAddDto);
}
