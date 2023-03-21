package cn.hestialx.service;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.CategoryAddDto;
import cn.hestialx.domain.dto.CategoryFindDto;
import cn.hestialx.domain.dto.CommonStatusDto;
import cn.hestialx.domain.vo.CategoryHomePageVo;
import cn.hestialx.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-02-17 16:18:58
*/
public interface CategoryService extends IService<Category> {

    List<CategoryHomePageVo> getHomePageCategories();

    ResponseResult listAllCategory();

    void exportExcel(HttpServletResponse response);

    ResponseResult pageFind(int pageNum, int pageSize, CategoryFindDto categoryFindDto);

    ResponseResult updateStatus(CommonStatusDto commonStatusDto);

    ResponseResult addCategory(CategoryAddDto categoryAddDto);

    ResponseResult deleteCategorys(List<Integer> ids);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryAddDto categoryAddDto);
}
