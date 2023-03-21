package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.UserAddDto;
import cn.hestialx.domain.dto.UserListDto;
import cn.hestialx.domain.dto.UserRegisterDto;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.domain.vo.UserAdminUpdateVo;
import cn.hestialx.domain.vo.UserAdminVo;
import cn.hestialx.domain.vo.UserInfoVo;
import cn.hestialx.entity.Role;
import cn.hestialx.entity.UserRole;
import cn.hestialx.enums.AppHttpCodeEnum;
import cn.hestialx.service.RoleService;
import cn.hestialx.service.UserRoleService;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.User;
import cn.hestialx.service.UserService;
import cn.hestialx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author 天罡剑f
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-02-21 14:44:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        String nickName = user.getNickName();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        User one = getOne(queryWrapper);
        if(one!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        updateById(user);
        return ResponseResult.okResult("操作成功!");
    }

    @Override
    public ResponseResult register(UserRegisterDto user) {
        //判断用户填写的用户名、用户昵称是否重复
        if(usernameIsExist(user.getUsername())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nicknameIsExist(user.getNickName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        //转换为User对象
        User saveUser = BeanCopyUtils.copyBean(user, User.class);
        save(saveUser);
        //存入数据库封装返回
        return ResponseResult.okResult("注册成功");
    }

    @Override
    public ResponseResult listAllUserPages(int pageSize, int pageNum, UserListDto userListDto) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userListDto.getStatus()),User::getStatus,userListDto.getStatus())
                .like(StringUtils.isNotBlank(userListDto.getUserName()),User::getUsername,userListDto.getUserName())
                .like(StringUtils.isNotBlank(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        page(userPage,queryWrapper);
        List<UserAdminVo> records = userPage.getRecords().stream().map(a -> {
            UserAdminVo userAdminVo = BeanCopyUtils.copyBean(a, UserAdminVo.class);
            userAdminVo.setUserName(a.getUsername());
            return userAdminVo;
        }).collect(Collectors.toList());
        PageVo pageVo = new PageVo(records, userPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(UserAddDto userAddDto) {
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        user.setUsername(userAddDto.getUserName());
        save(user);
        List<UserRole> us = userAddDto.getRoleIds().stream().map(a -> new UserRole(user.getId(), a)).collect(Collectors.toList());
        userRoleService.saveBatch(us);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteUsers(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        List<Role> data = (List<Role>)roleService.listAllRole().getData();
        List<Long> userRoleIds = roleService.getUserRoleIds(id);
        User byId = getById(id);
        UserAdminVo userAdminVo = BeanCopyUtils.copyBean(byId, UserAdminVo.class);
        userAdminVo.setUserName(byId.getUsername());
        UserAdminUpdateVo userAdminUpdateVo = new UserAdminUpdateVo(userAdminVo, data, userRoleIds);
        return ResponseResult.okResult(userAdminUpdateVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserAddDto userAddDto) {
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        user.setUsername(userAddDto.getUserName());
        updateById(user);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userAddDto.getId());
        userRoleService.remove(queryWrapper);
        List<UserRole> userRoles = userAddDto.getRoleIds().stream().map(a -> new UserRole(userAddDto.getId(), a)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    public boolean usernameIsExist(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        int count = count(queryWrapper);
        if(count>0){
            return true;
        }
        return false;
    }
    public boolean nicknameIsExist(String nickname){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickname);
        int count = count(queryWrapper);
        if(count>0){
            return true;
        }
        return false;
    }


}




