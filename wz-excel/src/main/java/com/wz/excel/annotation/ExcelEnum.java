package com.wz.excel.annotation;

import com.wz.common.enums.IEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.annotation
 * @className: ExcelEnum
 * @description:
 * @author: zhi
 * @date: 2021/12/6
 * @version: 1.0
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelEnum {

    /**
     * 需要转换的枚举类
     */
    Class<? extends IEnum<?, ?>> enumClass();
}
