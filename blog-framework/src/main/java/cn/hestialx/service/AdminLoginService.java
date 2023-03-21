package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;

/**
 * @author lixu
 * @create 2023-03-10-11:16
 */
public interface AdminLoginService {
    ResponseResult login(String username, String password);

    ResponseResult getInfo();

    ResponseResult getRouters();

    ResponseResult logout();
}
