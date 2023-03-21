package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;

/**
 * @author lixu
 * @create 2023-02-21-16:23
 */
public interface UserLoginService {
    ResponseResult login(String username, String password);
    ResponseResult logout();
}
