package com.wz.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @projectName: wz-component
 * @package: com.wz.excel
 * @className: Simple2
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@Data
public class Simple2 {

    @NotBlank(message = "姓名不能为空!!")
    @ExcelProperty("姓名")
    private String name;

    @Range(min = 1, max = 100, message = "年龄只能在{min}-{max}之间")
    @NotNull(message = "年龄不能为空")
    @ExcelProperty("年龄")
    private Integer age;

}
