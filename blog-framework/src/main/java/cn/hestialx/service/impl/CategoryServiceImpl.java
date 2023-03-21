package cn.hestialx.service.impl;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.constants.SystemConstants;
import cn.hestialx.domain.dto.CategoryAddDto;
import cn.hestialx.domain.dto.CategoryFindDto;
import cn.hestialx.domain.dto.CommonStatusDto;
import cn.hestialx.domain.vo.*;
import cn.hestialx.enums.AppHttpCodeEnum;
import cn.hestialx.utils.BeanCopyUtils;
import cn.hestialx.utils.WebUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hestialx.entity.Category;
import cn.hestialx.service.CategoryService;
import cn.hestialx.mapper.CategoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author 天罡剑f
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2023-02-17 16:18:58
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<CategoryHomePageVo> getHomePageCategories() {
        List<CategoryHomePageVo> homePageCategories = categoryMapper.getHomePageCategories();
        return homePageCategories;
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.SYSTEM_STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBean(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBean(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            try {
                WebUtils.renderString(response,objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        }
    }

    @Override
    public ResponseResult pageFind(int pageNum, int pageSize, CategoryFindDto categoryFindDto) {
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(categoryFindDto.getStatus()),Category::getStatus,categoryFindDto.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(categoryFindDto.getName()),Category::getName,categoryFindDto.getName());
        page(categoryPage,queryWrapper);
        PageVo pageVo = new PageVo(categoryPage.getRecords(),categoryPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateStatus(CommonStatusDto commonStatusDto) {
        Category category = BeanCopyUtils.copyBean(commonStatusDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addCategory(CategoryAddDto categoryAddDto) {
        Category category = BeanCopyUtils.copyBean(categoryAddDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategorys(List<Integer> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category byId = getById(id);
        CategoryAdminVo categoryAdminVo = BeanCopyUtils.copyBean(byId, CategoryAdminVo.class);
        return ResponseResult.okResult(categoryAdminVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryAddDto categoryAddDto) {
        Category category = BeanCopyUtils.copyBean(categoryAddDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }
}




