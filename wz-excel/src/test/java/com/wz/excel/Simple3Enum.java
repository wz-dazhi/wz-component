package com.wz.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wz.common.enums.IEnum;
import com.wz.excel.annotation.ExcelEnum;
import com.wz.excel.converters.EnumConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Simple3Enum {

    @NotBlank(message = "姓名不能为空!!")
    @ExcelProperty("姓名")
    private String name;

    @Range(min = 1, max = 100, message = "年龄只能在{min}-{max}之间")
    @NotNull(message = "年龄不能为空")
    @ExcelProperty("年龄")
    private Integer age;

    @ExcelEnum(enumClass = SexEnum.class)
    @ExcelProperty(value = "性别", converter = EnumConverter.class)
    private Integer sex;

    @NotNull(message = "状态不能为空") // 只能校验包装类
    @ExcelEnum(enumClass = ActiveEnum.class)
    @ExcelProperty(value = "状态", converter = EnumConverter.class)
    //private boolean active;
    private Boolean active;

    @AllArgsConstructor
    public enum SexEnum implements IEnum<Integer, String> {

        MALE(1, "男"),
        FEMALE(2, "女"),

        ;

        private final Integer sex;
        private final String desc;

        @Override
        public Integer code() {
            return sex;
        }

        @Override
        public String desc() {
            return desc;
        }
    }

    @AllArgsConstructor
    public enum ActiveEnum implements IEnum<Boolean, String> {

        YES(true, "启用"),
        NO(false, "不启用"),
        ;

        private final boolean active;
        private final String desc;

        @Override
        public Boolean code() {
            return active;
        }

        @Override
        public String desc() {
            return desc;
        }
    }
}
