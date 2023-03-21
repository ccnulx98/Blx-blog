package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.MenuListDto;
import cn.hestialx.entity.Menu;
import cn.hestialx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixu
 * @create 2023-03-19-10:22
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listAllMenu(MenuListDto menuListDto){
        return menuService.listAllMenu(menuListDto);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.getById(id));
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id){
        return menuService.roleMenuTreeselect(id);
    }
}
