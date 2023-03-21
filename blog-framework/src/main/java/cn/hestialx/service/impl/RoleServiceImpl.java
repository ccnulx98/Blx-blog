package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.domain.dto.RoleAddDto;
import cn.hestialx.domain.dto.RoleListDto;
import cn.hestialx.domain.dto.RoleStatusDto;
import cn.hestialx.domain.vo.PageVo;
import cn.hestialx.domain.vo.RoleAdminVo;
import cn.hestialx.entity.RoleMenu;
import cn.hestialx.service.RoleMenuService;
import cn.hestialx.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Role;
import cn.hestialx.service.RoleService;
import cn.hestialx.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 天罡剑f
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-03-10 22:41:33
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleMenuService roleMenuService;

    @Override
    public List<Long> getUserRoleIds(Long uid) {
        //获取用户id拥有的角色id
        List<Long> rids = roleMapper.getRolesIdByUserId(uid);
        return rids;
    }

    @Override
    public List<Role> getRolesByIds(List<Long> ids) {
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public ResponseResult getAllRolePages(int pageNum, int pageSize, RoleListDto roleListDto) {
        Page page = new Page(pageNum, pageSize);
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(roleListDto.getStatus()),Role::getStatus,roleListDto.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(roleListDto.getRoleName()),Role::getRoleName,roleListDto.getRoleName());
        queryWrapper.orderByAsc(Role::getRoleSort);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseResult addRole(RoleAddDto roleAddDto) {
        Role role = BeanCopyUtils.copyBean(roleAddDto, Role.class);
        save(role);
        List<RoleMenu> roleMenus = roleAddDto.getMenuIds().stream().map(a -> new RoleMenu(role.getId(), a)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleAdminVo roleAdminVo = BeanCopyUtils.copyBean(role, RoleAdminVo.class);
        return ResponseResult.okResult(roleAdminVo);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleAddDto roleAddDto) {
        Role role = BeanCopyUtils.copyBean(roleAddDto, Role.class);
        updateById(role);
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleAddDto.getId());
        roleMenuService.remove(queryWrapper);
        List<RoleMenu> roleMenus = roleAddDto.getMenuIds().stream().map(a -> new RoleMenu(role.getId(), a)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.SYSTEM_STATUS_NORMAL);
        List<Role> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }
}




