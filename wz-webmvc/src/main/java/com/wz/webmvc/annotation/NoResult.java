package com.wz.webmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.annotation
 * @className: NoResult
 * @description: 不对Controller返回结果包装为Result对象
 * @author: zhi
 * @date: 2022/9/22
 * @version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoResult {
}
