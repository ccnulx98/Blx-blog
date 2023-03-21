package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.RoleAddDto;
import cn.hestialx.domain.dto.RoleListDto;
import cn.hestialx.domain.dto.RoleStatusDto;
import cn.hestialx.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-03-10 22:41:33
*/
public interface RoleService extends IService<Role> {

    List<Long> getUserRoleIds(Long uid);

    List<Role> getRolesByIds(List<Long> ids);

    ResponseResult getAllRolePages(int pageNum, int pageSize, RoleListDto roleListDto);

    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(RoleAddDto roleAddDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(RoleAddDto roleAddDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}
