package com.wz.redis.util;

import com.wz.common.util.JsonUtil;
import com.wz.common.util.MapUtil;
import com.wz.common.util.SpringContextUtil;
import com.wz.common.util.StringUtil;
import com.wz.redis.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz
 * @package: com.wz.redis.util
 * @className: RedisUtils
 * @description: 基于Spring的redisTemplate 做了简单的封装
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午7:04
 **/
@Slf4j
public final class RedisUtil {
    private static final RedisTemplate<String, Object> REDIS_TEMPLATE;
    private static final ValueOperations<String, Object> VALUE_OPERATIONS;
    private static final HashOperations<String, String, Object> HASH_OPERATIONS;
    private static final ListOperations<String, Object> LIST_OPERATIONS;
    private static final SetOperations<String, Object> SET_OPERATIONS;
    private static final ZSetOperations<String, Object> Z_SET_OPERATIONS;

    static {
        REDIS_TEMPLATE = SpringContextUtil.getBean(RedisConfig.REDIS_TEMPLATE_BEAN, RedisTemplate.class);
        VALUE_OPERATIONS = REDIS_TEMPLATE.opsForValue();
        HASH_OPERATIONS = REDIS_TEMPLATE.opsForHash();
        LIST_OPERATIONS = REDIS_TEMPLATE.opsForList();
        SET_OPERATIONS = REDIS_TEMPLATE.opsForSet();
        Z_SET_OPERATIONS = REDIS_TEMPLATE.opsForZSet();
    }

    private RedisUtil() {
    }

    public static RedisTemplate<String, Object> redisTemplate() {
        return REDIS_TEMPLATE;
    }

    /**
     * @Description 设置过期时间, 单位: 秒
     */
    public static Boolean expire(String key, long expire) {
        return expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * @Description 设置过期时间
     */
    public static Boolean expire(String key, long expire, TimeUnit unit) {
        Boolean result = REDIS_TEMPLATE.expire(key, expire, unit);
        return result == null ? Boolean.FALSE : result;
    }

    /**
     * @param key key不能为空
     * @return 返回0表示永不过期
     * @Description 根据key获取过期时间, 单位: 秒
     */
    public static Long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @param key key不能为空
     * @return 返回0表示永不过期
     * @Description 根据key获取过期时间
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return REDIS_TEMPLATE.getExpire(key, unit);
    }

    /**
     * @param key
     * @return
     * @Description 判断key是否存在
     */
    public static Boolean keyExists(String key) {
        return REDIS_TEMPLATE.hasKey(key);
    }

    /**
     * @param keys keys
     * @Description 根据key删除缓存
     */
    public static void del(String... keys) {
        if (null != keys && keys.length > 0) {
            REDIS_TEMPLATE.delete(Arrays.asList(keys));
        }
    }

    //------------------------------------string------------------------

    /**
     * @param key   key
     * @param value value
     * @Description 普通缓存放入
     */
    public static void set(String key, Object value) {
        VALUE_OPERATIONS.set(key, value);
    }

    /**
     * @param key    key
     * @param value  value
     * @param expire 超时时间,单位(秒) , 大于0设置超时时间
     * @Description 普通缓存放入
     */
    public static void set(String key, Object value, long expire) {
        set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * @param key    key
     * @param value  value
     * @param expire 超时时间, 大于0设置超时时间
     * @Description 普通缓存放入
     */
    public static void set(String key, Object value, long expire, TimeUnit unit) {
        if (expire > 0) {
            VALUE_OPERATIONS.set(key, value, expire, unit);
        } else {
            set(key, value);
        }
    }

    /**
     * @param key key
     * @return Object
     * @Description <p>
     * 获取缓存的value.
     * 如果value是个对象的话, 默认返回 java.util.LinkedHashMap.
     * 如果需要返回Bean对象, 请使用 {@link #get(String, Class)}
     * 如果需要返回List<Bean>, 请使用 {@link #getList(String, Class)}
     * </p>
     */
    public static Object get(String key) {
        StringUtil.requireNonNull(key, "key 不能为空");
        return VALUE_OPERATIONS.get(key);
    }

    /**
     * @param key key
     * @return T
     * @Description 缓存的bean 对象
     */
    public static <T> T get(String key, Class<T> clazz) {
        StringUtil.requireNonNull(key, "key 不能为空");
        final Object o = get(key);
        if (o == null) {
            return null;
        }
        return JsonUtil.toBean(JsonUtil.toJson(o), clazz);
    }

    /**
     * @param key   key
     * @param clazz Class 对象
     * @return T List<T>
     * @Description 获取缓存的list对象
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        StringUtil.requireNonNull(key, "key 不能为空");
        final Object o = get(key);
        if (o == null) {
            return Collections.emptyList();
        }
        return JsonUtil.toArrayList(JsonUtil.toJson(o), clazz);
    }

    /**
     * @param key   key
     * @param clazz Class 对象
     * @return T Set<T>
     * @Description 获取缓存的set对象
     */
    public static <T> Set<T> getSet(String key, Class<T> clazz) {
        StringUtil.requireNonNull(key, "key 不能为空");
        final Object o = get(key);
        if (o == null) {
            return Collections.emptySet();
        }
        return JsonUtil.toHashSet(JsonUtil.toJson(o), clazz);
    }

    /**
     * 递增 1
     *
     * @param key 键
     */
    public static Long incrOne(String key) {
        return incr(key, 1L, 0, null, false);
    }

    /**
     * 递增 1
     *
     * @param key 键
     */
    public static Long incrOne(String key, long expire, TimeUnit unit) {
        return incr(key, 1L, expire, unit, true);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static Long incr(String key, long delta, long expire, TimeUnit unit, boolean isExpire) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        if (isExpire && expire <= 0 && unit == null) {
            throw new IllegalArgumentException("过期时间不能为空");
        }
        final Long increment = VALUE_OPERATIONS.increment(key, delta);
        if (isExpire) {
            expire(key, expire, unit);
        }
        return increment;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static Double incr(String key, double delta, long expire, TimeUnit unit, boolean isExpire) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        if (isExpire && expire <= 0 && unit == null) {
            throw new IllegalArgumentException("过期时间不能为空");
        }
        final Double increment = VALUE_OPERATIONS.increment(key, delta);
        if (isExpire) {
            expire(key, expire, unit);
        }
        return increment;
    }

    /**
     * 递减 1
     *
     * @param key 键
     */
    public static Long decrOne(String key) {
        return decr(key, 1L);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static Long decr(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return VALUE_OPERATIONS.increment(key, -delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static Double decr(String key, double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return VALUE_OPERATIONS.increment(key, -delta);
    }

    //------------------------------------------map---------------------------

    /**
     * @param key
     * @param field
     * @param value
     * @Description hash 放入缓存
     */
    public static <HK, HV> void hset(String key, HK field, HV value) {
        REDIS_TEMPLATE.<HK, HV>opsForHash().put(key, field, value);
    }

    /**
     * @Description hash放入缓存, 并设置过期时间. 单位: 秒
     */
    public static <HK, HV> void hset(String key, HK field, HV value, long time) {
        hset(key, field, value, time, TimeUnit.SECONDS);
    }

    /**
     * @Description hash放入缓存, 并设置过期时间
     */
    public static <HK, HV> void hset(String key, HK field, HV value, long time, TimeUnit unit) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        REDIS_TEMPLATE.<HK, HV>opsForHash().put(key, field, value);
        expire(key, time, unit);
    }

    /**
     * hget
     *
     * @param key   key不能为空
     * @param field field不能为空
     * @return
     */
    public static <HK, HV> HV hget(String key, HK field) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(field, "field 不能为空");
        return REDIS_TEMPLATE.<HK, HV>opsForHash().get(key, field);
    }

    /**
     * hmset
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static <HK, HV> void hmset(String key, Map<HK, HV> map) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(map, "map 不能为空");
        REDIS_TEMPLATE.<HK, HV>opsForHash().putAll(key, map);
    }

    /**
     * hmset 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public static <HK, HV> void hmset(String key, Map<HK, HV> map, long time) {
        hmset(key, map, time, TimeUnit.SECONDS);
    }

    /**
     * hmset 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间
     */
    public static <HK, HV> void hmset(String key, Map<HK, HV> map, long time, TimeUnit unit) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(map, "map 不能为空");
        if (time <= 0) {
            throw new RuntimeException("time [" + time + "]不能小于0");
        }
        REDIS_TEMPLATE.<HK, HV>opsForHash().putAll(key, map);
        expire(key, time, unit);
    }

    /**
     * Bean 并设置时间
     *
     * @param key  键
     * @param t    bean 对象
     * @param time 时间(秒)
     */
    public static <T> void hmset(String key, T t, long time) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(t, "t 不能为空");
        hmset(key, MapUtil.beanToMap(t), time);
    }

    /**
     * 根据key 获取所有的键值
     *
     * @param key key不能为空
     * @return
     */
    public static <HK, HV> Map<HK, HV> hmget(String key) {
        StringUtil.requireNonNull(key, "key 不能为空");
        return REDIS_TEMPLATE.<HK, HV>opsForHash().entries(key);
    }

    /**
     * 根据key 获取对象
     *
     * @param key key不能为空
     * @return
     */
    public static <T> T hmget(String key, Class<T> clazz) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(clazz, "clazz 不能为空");
        Map<String, Object> map = HASH_OPERATIONS.entries(key);
        if (null == map || map.isEmpty()) {
            return null;
        }
        try {
            return MapUtil.mapToBean(map, clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("根据key[{}]获取对象[{}], 转换异常: {}, e: ", key, clazz.getName(), e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param key
     * @param hashKeys
     * @Description 删除hash中的值
     */
    public static Long hdel(String key, Object... hashKeys) {
        StringUtil.requireNonNull(key, "key 不能为空");
        Objects.requireNonNull(hashKeys, "hashKeys 不能为空");
        return HASH_OPERATIONS.delete(key, hashKeys);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key   键 不能为null
     * @param field 项 不能为null
     * @return true 存在 false不存在
     */
    public static <HK> Boolean hHasKey(String key, HK field) {
        return HASH_OPERATIONS.hasKey(key, field);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     */
    public static Double hincr(String key, String field, double by) {
        StringUtil.requireNonNull(key, "key 不能为空");
        StringUtil.requireNonNull(field, "field 不能为空");
        if (by <= 0) {
            throw new IllegalArgumentException("hash递增因子必须大于0");
        }
        return HASH_OPERATIONS.increment(key, field, by);
    }

    /**
     * hash递减
     */
    public static Double hdecr(String key, String field, double by) {
        if (by <= 0) {
            throw new IllegalArgumentException("hash递减因子必须大于0");
        }
        return HASH_OPERATIONS.increment(key, field, -by);
    }

    //============================set=============================

    /**
     * @param key    根据key放入set
     * @param values 可以有多个
     * @return
     * @Description set 放入缓存
     */
    public static <V> Long sSet(String key, V... values) {
        return SET_OPERATIONS.add(key, values);
    }

    /**
     * @param key
     * @param values
     * @param time
     * @return
     * @Description set放入缓存, 并设置过期时间
     */
    public static <V> boolean sSet(long time, String key, V... values) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        SET_OPERATIONS.add(key, values);
        return expire(key, time);
    }

    /**
     * sget
     *
     * @param key key不能为空
     * @return
     */
    public static Set<Object> sGet(String key) {
        return SET_OPERATIONS.members(key);
    }

    /**
     * @param key   key
     * @param value value
     * @return 存在 成功 false 不存在
     * @Description set 是否存在
     */
    public static <V> Boolean sHasKey(String key, V value) {
        return SET_OPERATIONS.isMember(key, value);
    }

    /**
     * @param key    key
     * @param values value
     * @return 移除的个数
     * @Description 根据key, value移除set缓存, 可以有多个
     */
    public static Long sDel(String key, Object... values) {
        return SET_OPERATIONS.remove(key, values);
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lRange(String key, long start, long end) {
        return LIST_OPERATIONS.range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static Long lSize(String key) {
        return LIST_OPERATIONS.size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lIndex(String key, long index) {
        return LIST_OPERATIONS.index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static <V> Long lRightPush(String key, V value) {
        return LIST_OPERATIONS.rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static <V> Long lLeftPush(String key, V value) {
        return LIST_OPERATIONS.leftPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static <V> boolean lRightPush(String key, V value, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        LIST_OPERATIONS.rightPush(key, value);
        return expire(key, time);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static <V> Long lRightPushAll(String key, List<V> value) {
        return LIST_OPERATIONS.rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lRightPushAll(String key, List<Object> value, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        LIST_OPERATIONS.rightPushAll(key, value);
        return expire(key, time);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static <V> boolean lLeftPush(String key, V value, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        LIST_OPERATIONS.leftPush(key, value);
        return expire(key, time);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static <V> Long lLeftPushAll(String key, List<V> value) {
        return LIST_OPERATIONS.leftPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lLeftPushAll(String key, List<Object> value, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        LIST_OPERATIONS.leftPushAll(key, value);
        return expire(key, time);
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static <V> void lSet(String key, long index, V value) {
        LIST_OPERATIONS.set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static <V> Long lRemove(String key, long count, V value) {
        return LIST_OPERATIONS.remove(key, count, value);
    }

    //-----------------------------------zset-------------------------------

    /**
     * @param key   key
     * @param value 元素
     * @param v1    序号
     * @return
     * @Description 将zset 放入缓存,添加元素并制定序号
     */
    public static <V> Boolean zAdd(String key, V value, double v1) {
        return Z_SET_OPERATIONS.add(key, value, v1);
    }

    /**
     * @param key   key
     * @param value 元素
     * @param v1    序号
     * @return
     * @Description 将zset 放入缓存,添加元素并制定序号,并设置过期时间
     */
    public static <V> boolean zAdd(String key, V value, double v1, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        Z_SET_OPERATIONS.add(key, value, v1);
        return expire(key, time);
    }

    /**
     * @param key key
     * @param set set集合
     * @return 1表示添加成功, 0表示添加失败
     * @Description 添加多个元素, 并指定序号
     */
    public static Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        return Z_SET_OPERATIONS.add(key, set);
    }

    /**
     * @param key key
     * @param set set集合
     * @return 1表示添加成功, 0表示添加失败
     * @Description 添加多个元素, 并指定序号,设置过期时间
     */
    public static boolean zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set, long time) {
        if (time <= 0) {
            throw new IllegalArgumentException("time [" + time + "]不能小于0");
        }
        Z_SET_OPERATIONS.add(key, set);
        return expire(key, time);
    }

    /**
     * @param key
     * @param values
     * @return
     * @Description 移除zSet中的元素
     */
    public static Long zRemove(String key, Object... values) {
        return Z_SET_OPERATIONS.remove(key, values);
    }
}
