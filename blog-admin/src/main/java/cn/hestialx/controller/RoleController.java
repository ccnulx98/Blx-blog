package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.RoleAddDto;
import cn.hestialx.domain.dto.RoleListDto;
import cn.hestialx.domain.dto.RoleStatusDto;
import cn.hestialx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lixu
 * @create 2023-03-19-11:16
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getALlRolePages(int pageNum, int pageSize, RoleListDto roleListDto){
        return roleService.getAllRolePages(pageNum,pageSize,roleListDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@Valid @RequestBody RoleStatusDto roleStatusDto){
        return roleService.changeStatus(roleStatusDto);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleAddDto roleAddDto){
        System.out.println(roleAddDto);
        return roleService.addRole(roleAddDto);
    }

    @GetMapping("{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleAddDto roleAddDto){
        return roleService.updateRole(roleAddDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

}
