package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.vo.CategoryHomePageVo;
import cn.hestialx.entity.Category;
import cn.hestialx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lixu
 * @create 2023-02-17-16:22
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult<List> listHomePageCategories(){
        return ResponseResult.okResult(categoryService.getHomePageCategories());
    }
}
