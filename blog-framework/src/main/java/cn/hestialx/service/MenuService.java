package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.MenuListDto;
import cn.hestialx.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-03-10 22:41:46
*/
public interface MenuService extends IService<Menu> {

    List<Long> getMenuIds(List<Long> roleIds);

    List<Menu> getMenus(List<Long> ids);

    ResponseResult listAllMenu(MenuListDto menuListDto);

    ResponseResult addMenu(Menu menu);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselect(Long id);
}
