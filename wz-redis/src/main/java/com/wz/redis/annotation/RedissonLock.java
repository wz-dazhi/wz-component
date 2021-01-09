package com.wz.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @projectName: wz-redis
 * @package: com.wz.redis.annotation
 * @className: RedissonLock
 * @description:
 * @author: Zhi
 * @date: 2020-05-18 17:55
 * @version: 1.0
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface RedissonLock {

    /**
     * redis锁key
     */
    String key();

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
