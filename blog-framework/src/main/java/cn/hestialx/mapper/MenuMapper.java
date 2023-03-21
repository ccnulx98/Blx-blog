package cn.hestialx.mapper;

import cn.hestialx.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-03-10 22:41:46
* @Entity cn.hestialx.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<Long> getMenusId(@Param("roleId") Long roleId);

    List<String> getPermsByUserId(@Param("userId") Long id);
}




