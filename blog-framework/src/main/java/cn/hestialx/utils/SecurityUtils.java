package cn.hestialx.utils;

import cn.hestialx.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lixu
 * @create 2023-02-27-17:39
 */
public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUserInfo().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUserInfo().getId();
    }
}
