package com.wz.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @projectName: wz-component
 * @package: com.wz.redis.enums
 * @className: LuaLock
 * @description:
 * @author: Zhi
 * @date: 2020-05-18 17:55
 * @version: 1.0
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface LuaLock {

    /**
     * redis锁key
     */
    String key();

    /**
     * redis锁value值
     */
    String requestId();

    /**
     * 锁过期时间, 默认1秒
     */
    long expire() default 1L;

    /**
     * 锁过期时间单位, 默认秒
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 等待时间, 单位为秒. 默认不等待
     */
    long waitTime() default 0;
}
