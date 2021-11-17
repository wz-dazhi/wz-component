package com.wz.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @projectName: wz-component
 * @package: com.wz.excel
 * @className: Simple
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@Data
public class Simple {

    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    @ExcelProperty("发生时间")
    private Date date;
//    private String date;

    @NotBlank(message = "类别不能为空!!")
    @ExcelProperty("类别")
    private String type;

    @NotNull
    @ExcelProperty("ID")
    private String id;

}
