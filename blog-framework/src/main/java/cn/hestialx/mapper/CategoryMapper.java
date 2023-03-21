package cn.hestialx.mapper;

import cn.hestialx.domain.vo.CategoryHomePageVo;
import cn.hestialx.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_category(分类表)】的数据库操作Mapper
* @createDate 2023-02-17 16:18:58
* @Entity cn.hestialx.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryHomePageVo> getHomePageCategories();
}




