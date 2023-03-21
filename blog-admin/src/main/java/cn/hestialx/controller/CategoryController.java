package cn.hestialx.controller;

import cn.hestialx.common.ResponseResult;
import cn.hestialx.domain.dto.CategoryAddDto;
import cn.hestialx.domain.dto.CategoryFindDto;
import cn.hestialx.domain.dto.CommonStatusDto;
import cn.hestialx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lixu
 * @create 2023-02-17-16:22
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void exportCategoryExcel(HttpServletResponse response){
        categoryService.exportExcel(response);
    }

    @GetMapping("/list")
    public ResponseResult listAllCategoryPages(int pageNum, int pageSize, CategoryFindDto categoryFindDto){
        return categoryService.pageFind(pageNum,pageSize,categoryFindDto);
    }
    @PutMapping("/updateStatus")
    public ResponseResult updateStatus(@RequestBody @Valid CommonStatusDto commonStatusDto){
        return categoryService.updateStatus(commonStatusDto);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryAddDto categoryAddDto){
        return categoryService.addCategory(categoryAddDto);
    }
    @DeleteMapping("{ids}")
    public ResponseResult deleteCategorys(@PathVariable("ids") List<Integer> ids){
        return categoryService.deleteCategorys(ids);
    }
    @GetMapping("{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryAddDto categoryAddDto){
        return categoryService.updateCategory(categoryAddDto);
    }
}
