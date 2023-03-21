package cn.hestialx.service.impl;

import cn.hestialx.constants.SystemConstants;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.entity.Menu;
import cn.hestialx.entity.User;
import cn.hestialx.mapper.MenuMapper;
import cn.hestialx.mapper.RoleMapper;
import cn.hestialx.service.AdminLoginService;
import cn.hestialx.service.MenuService;
import cn.hestialx.service.UserService;
import cn.hestialx.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lixu
 * @create 2023-02-21-17:05
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUsername,username);
        User one = userService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(one)){
            throw new RuntimeException("用户不存在！");
        }
        LoginUser loginUser = new LoginUser();
        //如果是管理员则 查询权限信息并封装
        loginUser.setUserInfo(one);
        if(SystemConstants.ADMIN_USRE_TYPE.equals(one.getType())){
            List<String> perms = menuMapper.getPermsByUserId(one.getId());
            loginUser.setPerms(perms);
        }
        return loginUser;
    }
}
