package com.wz.shiro.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: wz-component
 * @package: com.wz.shiro.annotation
 * @className: Anon
 * @description:
 * @author: zhi
 * @date: 2021/2/27
 * @version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Anon {
}
