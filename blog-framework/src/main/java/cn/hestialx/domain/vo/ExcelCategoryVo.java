package cn.hestialx.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lixu
 * @create 2023-03-16-11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String name;
    //描述
    @ExcelProperty("描述")
    private String description;

    //状态0:正常,1禁用
    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
