package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.domain.dto.MenuListDto;
import cn.hestialx.domain.vo.MenuTreeSelectVo;
import cn.hestialx.exception.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Menu;
import cn.hestialx.service.MenuService;
import cn.hestialx.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 天罡剑f
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-03-10 22:41:46
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Long> getMenuIds(List<Long> roleIds) {
        List<Long> menuIds  = new ArrayList<>();
        for (Long roleId : roleIds) {
            List<Long> ids = menuMapper.getMenusId(roleId);
            menuIds.addAll(ids);
        }
        return menuIds;
    }

    @Override
    public List<Menu> getMenus(List<Long> ids) {
        return menuMapper.selectBatchIds(ids);
    }

    @Override
    public ResponseResult listAllMenu(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(menuListDto.getStatus()), Menu::getStatus,menuListDto.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(menuListDto.getMenuName()),Menu::getMenuName,menuListDto.getMenuName());
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        Long parentId = menu.getParentId();
        if(parentId.equals(menu.getId())){
            throw new BizException("不能把父菜单设置为当前菜单！");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        int count = count(queryWrapper);
        if(count!=0){
            throw new BizException("菜单存在子菜单不允许删除！");
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, SystemConstants.SYSTEM_STATUS_NORMAL);
        List<Menu> allMenus = list(queryWrapper);
        List<MenuTreeSelectVo> allMenusList = allMenus.stream().map(a ->
                MenuTreeSelectVo.builder()
                        .label(a.getMenuName())
                        .parentId(a.getParentId())
                        .id(a.getId()).build()).collect(Collectors.toList());
        queryWrapper.eq(Menu::getParentId, 0);
        List<Menu> list = list(queryWrapper);
        List<MenuTreeSelectVo> rootlist = list.stream().map(a ->
                MenuTreeSelectVo.builder()
                        .label(a.getMenuName())
                        .parentId(a.getParentId())
                        .id(a.getId()).build()).collect(Collectors.toList());
        for (MenuTreeSelectVo menuTreeSelectVo : rootlist) {
            setChildrenMenu(menuTreeSelectVo,allMenusList);
        }
        return ResponseResult.okResult(rootlist);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        List<Long> menusId = menuMapper.getMenusId(id);
        Object data = treeSelect().getData();
        HashMap<String, Object> result = new HashMap<>();
        result.put("menus",data);
        result.put("checkedKeys",menusId);
        return ResponseResult.okResult(result);
    }

    public void setChildrenMenu(MenuTreeSelectVo root,List<MenuTreeSelectVo> allList){
        List<MenuTreeSelectVo> childrens = allList.stream().filter(a -> a.getParentId().equals(root.getId())).collect(Collectors.toList());
        for (MenuTreeSelectVo children : childrens) {
            setChildrenMenu(children,allList);
        }
        root.setChildren(childrens);
    }
}




