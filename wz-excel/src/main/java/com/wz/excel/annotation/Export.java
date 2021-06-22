package com.wz.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.wz.excel.enums.TemplateTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.annotation
 * @className: Export
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {

    /**
     * 文件名称
     */
    String fileName() default "";

    /**
     * 模板所在位置, 默认不存在模板
     */
    TemplateTypeEnum templateType() default TemplateTypeEnum.NONE;

    /**
     * 模板, 默认没有
     * <pre>
     * 1. TemplateTypeEnum.NONE -> 没有模板
     * 2. TemplateTypeEnum.CLASSPATH -> 模板在当前项目resources下, 例: templates/demo-template.xlsx
     * 2. TemplateTypeEnum.LOCAL -> 模板在本地目录中, 例: /root/templates/demo-template.xlsx
     * 2. TemplateTypeEnum.URL -> 模板地址, 例: https://www.example.com/demo-template.xlsx
     * </pre>
     */
    String template() default "";

    /**
     * 文件后缀, 默认.xlsx
     */
    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;

    /**
     * sheet页, 默认一个sheet
     */
    Sheet[] sheet() default {@Sheet};

    @Documented
    @Target({ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Sheet {

        /**
         * sheet 名称
         */
        String sheetName() default "";

        /**
         * Sheet head Class
         * 不设置的情况下, 默认取List<T> 泛型T的类型.
         * 当List<T>为空的情况下, 建议手动设置head类型
         */
        Class<?> head() default Object.class;
    }

}
