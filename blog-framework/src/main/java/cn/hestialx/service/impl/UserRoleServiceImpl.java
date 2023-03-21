package cn.hestialx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.UserRole;
import cn.hestialx.service.UserRoleService;
import cn.hestialx.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 天罡剑f
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-03-20 14:30:26
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




