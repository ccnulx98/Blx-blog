package cn.hestialx.mapper;

import cn.hestialx.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-03-10 22:41:33
* @Entity cn.hestialx.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<Long> getRolesIdByUserId(@Param("uid") Long uid);

}




