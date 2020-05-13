package com.wz.redis.util;

import com.wz.common.constant.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz-redis
 * @package: com.wz.redis.util
 * @className: RedisSingleLock
 * @description: 描述
 * <p>
 * 基于redis setnx 命令实现一个单机的分布式锁
 * </p>
 * @author: Zhi Wang
 * @date: 2019/11/24 9:43 AM
 * @version: 1.0
 **/
@Slf4j
public class RedisSingleLock {

    /**
     * Lua 解锁脚本
     */
    private static final String UNLOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * key前缀
     */
    private static final String LOCK_PREFIX = "lock" + Consts.SUBFIX;

    /**
     * 默认超时时间10秒
     */
    private static final long DEFAULT_EXPIRE = 10;

    /**
     * 锁key
     */
    private String key = LOCK_PREFIX;

    /**
     * 超时时间, 默认10秒
     */
    private long expire = DEFAULT_EXPIRE;

    /**
     * 时间单位, 默认秒
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 是否得到锁
     */
    private volatile boolean isLock = false;

    private String requestId;

    /**
     * redisTemplate 封装的string对象
     */
    private RedisTemplate<String, String> redisTemplate;

    public RedisSingleLock(RedisTemplate<String, String> redisTemplate, String key, String requestId) {
        this.redisTemplate = Objects.requireNonNull(redisTemplate, "RedisTemplate 不能为null.");
        this.key = this.key + Objects.requireNonNull(key, "Key 不能为null.");
        this.requestId = Objects.requireNonNull(requestId, "RequestId 不能为null.");
    }

    public RedisSingleLock(RedisTemplate<String, String> redisTemplate, String key, String requestId, long expire) {
        this(redisTemplate, key, requestId);
        if (expire <= 0) {
            throw new IllegalArgumentException("Expire 过期时间不能低于0.");
        }
        this.expire = expire;
    }

    public RedisSingleLock(RedisTemplate<String, String> redisTemplate, String key, String requestId, long expire, TimeUnit unit) {
        this(redisTemplate, key, requestId, expire);
        this.unit = Objects.requireNonNull(unit, "TimeUnit 不能为null.");
    }

    /**
     * 获取锁
     */
    public boolean lock() {
        isLock = redisTemplate.opsForValue().setIfAbsent(key, requestId, expire, unit);
        return isLock;
    }

    /**
     * 释放锁
     */
    public boolean unLock() {
        if (isLock) {
            RedisScript<Long> redisScript = RedisScript.of(UNLOCK_LUA_SCRIPT);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), requestId);
            log.debug("---> key: [{}] 解锁: [{}]. 返回结果: {}", key, requestId, result);
            isLock = false;
            // 解锁失败, 可能设置的锁超时时间短. 锁已经过期了
            return 1L == result;
        }
        return false;
    }

}
