package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.domain.vo.*;
import cn.hestialx.entity.LoginUser;
import cn.hestialx.entity.Menu;
import cn.hestialx.entity.Role;
import cn.hestialx.entity.User;
import cn.hestialx.mapper.UserMapper;
import cn.hestialx.service.AdminLoginService;
import cn.hestialx.service.MenuService;
import cn.hestialx.service.RoleService;
import cn.hestialx.service.UserService;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.JWTUtils;
import cn.hestialx.utils.RedisCache;
import cn.hestialx.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lixu
 * @create 2023-03-10-11:16
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(String username, String password) {
        //封装校验信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        //校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(ObjectUtils.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误,验证失败！");
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        /**
         * 1、用户信息存入redis
         * 2、生成jwt
         * 3、封装返回信息
         */
        redisCache.setCacheObject(SystemConstants.BLOG_ADMIN_REDIS_PREFIX+loginUser.getUserInfo().getId(),loginUser);

        String jwt = JWTUtils.createJWT(loginUser.getUserInfo().getId().toString(), "json", JWTUtils.JWT_TTL);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult getInfo() {
        //SecurityContext 中获取登录用户 获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = SecurityUtils.getUserId();
        Boolean isAdmin = SecurityUtils.isAdmin();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUserInfo(), UserInfoVo.class);
        //menu查询条件
        LambdaQueryWrapper<Menu> menuQueryWrapper = new LambdaQueryWrapper<>();
        menuQueryWrapper.in(Menu::getMenuType,"F","C");
        menuQueryWrapper.eq(Menu::getStatus,"0");

        if(isAdmin){
            AdminUserInfoVo adminInfo = getAdminInfo(userInfoVo);
            return ResponseResult.okResult(adminInfo);
        }

        List<Long> roleIds = roleService.getUserRoleIds(userId);
        List<Role> roles = roleService.getRolesByIds(roleIds);
        //过滤筛选
        roles = roles.stream().filter(a-> "0".equals(a.getStatus())).collect(Collectors.toList());

        List<Menu> menus = getMenusByRoleIds(roleIds);

        //封装AdminUserInfo
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo();
        adminUserInfoVo.setUser(userInfoVo);
        adminUserInfoVo.setPermissions(menus.stream().map(a->a.getPerms()).collect(Collectors.toList()));
        adminUserInfoVo.setRoles(roles.stream().map(a->a.getRoleKey()).collect(Collectors.toList()));

        return ResponseResult.okResult(adminUserInfoVo);
    }

    public AdminUserInfoVo getAdminInfo(UserInfoVo userInfoVo){
        LambdaQueryWrapper<Menu> menuQueryWrapper = new LambdaQueryWrapper<>();
        menuQueryWrapper.in(Menu::getMenuType,"F","C");
        menuQueryWrapper.eq(Menu::getStatus,"0");
        //如果用户角色为Admin（即用户角色id为1），则拥有所有权限
        List<Menu> list = menuService.list(menuQueryWrapper);
        //封装AdminUserInfo，返回结果
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo();
        adminUserInfoVo.setUser(userInfoVo);
        List<String> permissions = list.stream().map((a)->a.getPerms()).collect(Collectors.toList());
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        adminUserInfoVo.setPermissions(permissions);
        adminUserInfoVo.setRoles(roles);
        return adminUserInfoVo;
    }

    public List<Menu> getMenusByRoleIds(List<Long> roleIds){
        //根据用户角色列表查询用户menu权限信息
        List<Long> menuIds = menuService.getMenuIds(roleIds);
        List<Menu> menus = menuService.getMenus(menuIds);
        menus = menus.stream().filter(menu->{
            if("C".equals(menu.getMenuType())||"F".equals(menu.getMenuType())){
                if("0".equals(menu.getStatus())){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        return menus;
    }

    @Override
    public ResponseResult getRouters() {

        Long userId = SecurityUtils.getUserId();
        if(userId == 1){
            HashMap<String, List<MenuRouersVo>> stringListHashMap = new HashMap<>();
            stringListHashMap.put("menus",getAdminRouters());
            return ResponseResult.okResult(stringListHashMap);
        }
        List<Long> roleIds = roleService.getUserRoleIds(userId);
        List<Long> menuIds = menuService.getMenuIds(roleIds);
        List<Menu> menus = menuService.getMenus(menuIds);
        Collections.sort(menus, Comparator.comparingInt(Menu::getOrderNum));
        menus = menus.stream().filter(a->{
            if("M".equals(a.getMenuType())||"C".equals(a.getMenuType())){
                if("0".equals(a.getStatus())){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        List<Menu> rootMenus = menus.stream().filter(a->a.getParentId()==0).collect(Collectors.toList());
        List<MenuRouersVo> menuRouersVos = BeanCopyUtils.copyBean(rootMenus, MenuRouersVo.class);
        for (MenuRouersVo menu : menuRouersVos) {
            buildMenuVo(menu,menus);
        }
        HashMap<String, List> stringListHashMap = new HashMap<>();
        stringListHashMap.put("menus",menuRouersVos);
        return ResponseResult.okResult(stringListHashMap);
    }

    @Override
    public ResponseResult logout() {
        //删除redis中的用户信息
        Long userId = SecurityUtils.getUserId();
        String key = SystemConstants.BLOG_ADMIN_REDIS_PREFIX+ userId;
        redisCache.deleteObject(key);
        return ResponseResult.okResult("退出成功！");
    }

    public void buildMenuVo(MenuRouersVo menu,List<Menu> menus){
        List<MenuRouersVo> list = menus.stream().filter(a->a.getParentId().equals(menu.getId())).map(a->BeanCopyUtils.copyBean(a,MenuRouersVo.class)).collect(Collectors.toList());
        for (MenuRouersVo menuRouersVo : list) {
            buildMenuVo(menuRouersVo,menus);
        }
        menu.setChildren(list);
    }

    public List<MenuRouersVo> getAdminRouters(){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus,"0");
        queryWrapper.in(Menu::getMenuType,"C","M");
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = menuService.list(queryWrapper);
        List<Menu> filtered = list.stream().filter(a -> a.getParentId() == 0).collect(Collectors.toList());
        List<MenuRouersVo> rootvos = BeanCopyUtils.copyBean(filtered, MenuRouersVo.class);
        for (MenuRouersVo menu : rootvos) {
            buildMenuVo(menu,list);
        }
        return rootvos;
    }
}
