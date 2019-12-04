package com.wz.encrypt.annotation;

import java.lang.annotation.*;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.annotation
 * @className: Decrypt
 * @description: 接口解密
 * @author: Zhi Wang
 * @date: 2019/3/5 12:12 PM
 * @version: 1.0
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {
}
