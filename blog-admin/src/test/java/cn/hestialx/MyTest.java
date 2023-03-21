package cn.hestialx;

import cn.hestialx.entity.Role;
import cn.hestialx.mapper.RoleMapper;
import cn.hestialx.service.AdminLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author lixu
 * @create 2023-03-10-23:02
 */
@SpringBootTest
public class MyTest {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    AdminLoginService adminLoginService;

    @Test
    public void Test01(){
        List<Long> rolesIdByUserId = roleMapper.getRolesIdByUserId(5L);
        rolesIdByUserId.add(1L);
        System.out.println(rolesIdByUserId);
        List<Role> roles = roleMapper.selectBatchIds(rolesIdByUserId);
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}
