package com.wz.encrypt.annotation;

import java.lang.annotation.*;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.annotation
 * @className: Encrypt
 * @description: 接口加密
 * @author: Zhi Wang
 * @date: 2019/3/5 12:11 PM
 * @version: 1.0
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
}
