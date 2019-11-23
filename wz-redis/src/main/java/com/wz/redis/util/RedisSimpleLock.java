//package com.wz.redis.util;
//
//import com.wz.common.constant.Consts;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
///**
// * @projectName: wz
// * @package: com.wz.common.util
// * @className: RedisSimpleLock
// * @description: 描述
// * <p>
// * 基于redis setnx 命令实现一个简单的分布式锁
// * </p>
// * @author: Zhi Wang
// * @date: 2019/3/1 9:43 AM
// * @version: 1.0
// **/
//@Slf4j
//public class RedisSimpleLock {
//
//    /**
//     * key前缀
//     */
//    private static final String LOCK_PREFIX = "lock" + Consts.SUBFIX;
//
//    /**
//     * 默认超时时间60秒
//     */
//    private static final long DEFAULT_EXPIRE = 60;
//
//    /**
//     * 锁key
//     */
//    private String key = LOCK_PREFIX;
//
//    /**
//     * 超时时间
//     */
//    private long expire = DEFAULT_EXPIRE;
//
//    /**
//     * 是否得到锁
//     */
//    private volatile boolean isLock = false;
//
//    private final Random random = new Random();
//
//    /**
//     * redisTemplate 封装的string对象
//     */
//    private RedisTemplate redisTemplate;
//
//    public RedisSimpleLock(RedisTemplate<String, Object> redisTemplate, String key) {
//        this.redisTemplate = redisTemplate;
//        this.key = this.key + key;
//    }
//
//    public RedisSimpleLock(RedisTemplate<String, Object> redisTemplate, String key, long expire) {
//        this(redisTemplate, key);
//        this.expire = expire;
//    }
//
//    /**
//     * 获取锁
//     * TODO 待优化
//     *
//     * @return
//     */
//    public boolean lock() {
//        int num = 1 + random.nextInt(9);
//        // 随机睡眠, 避免多个机器同时抢锁
//        try {
//            Thread.sleep(num * 100);
//        } catch (InterruptedException e) {
//            log.public.static.public.static.static(e.getMessage(), e);
//        }
//        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis());
//        if (ifAbsent) {
//            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
//            isLock = true;
//            return true;
//        }
//        Object value = redisTemplate.opsForValue().get(key);
//        // 检查值状态, 避免获得锁之后没有设置过期时间导致发生死锁
//        if (null != value && redisTemplate.getExpire(key) < 0) {
//            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
//            isLock = false;
//        }
//        return false;
//    }
//
//    /**
//     * 释放锁
//     */
//    public void unLock() {
//        if (this.isLock) {
//            redisTemplate.delete(key);
//            isLock = false;
//        }
//    }
//
//}
