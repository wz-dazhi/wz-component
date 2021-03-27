package com.wz.common.util;

import com.wz.common.constant.DateConsts;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: BeanCopierUtil
 * @description:
 * @author: zhi
 * @date: 2021/1/15
 * @version: 1.0
 */
@Slf4j
public final class BeanCopierUtil {

    private static final boolean USE_CACHE = true;
    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>(64);

    private BeanCopierUtil() {
    }

    /**
     * 使用BeanCopier拷贝对象属性
     * 1. target不能使用链式调用 {@link lombok.experimental.Accessors#chain}
     * 2. 只会拷贝source,target属性类型和名称(必须)完全一致的字段
     * 3. 源和目标必须存在一致的set方法和get方法, 不然会报null指针
     *
     * @param source    源
     * @param target    目标
     * @param useCache  是否使用缓存
     * @param converter converter对象
     * @param <S>       源对象泛型
     * @param <T>       目标对象泛型
     * @return 返回target对象
     */
    public static <S, T> T copy(S source, T target, boolean useCache, Converter converter) {
        requireNonNull(source, target);
        boolean useConverter = Objects.nonNull(converter);

        Class<?> targetClass = target.getClass();

        String key = getKey(source, targetClass);

        BeanCopier beanCopier = getBeanCopier(useCache, key, createBeanCopier(source, targetClass, useConverter));
        beanCopier.copy(source, target, converter);
        return target;
    }

    /**
     * 使用BeanCopier拷贝对象属性
     * 1. target不能使用链式调用 {@link lombok.experimental.Accessors#chain}
     * 2. 只会拷贝source,target属性类型和名称(必须)完全一致的字段
     * 3. 源和目标必须存在一致的set方法和get方法, 不然会报null指针
     *
     * @param source      源
     * @param targetClass 目标类对象
     * @param useCache    是否使用缓存
     * @param converter   converter对象
     * @param <S>         源对象泛型
     * @param <T>         目标对象泛型
     * @return 返回target对象
     */
    public static <S, T> T copy(S source, Class<T> targetClass, boolean useCache, Converter converter) {
        requireNonNull(source, targetClass);
        boolean useConverter = Objects.nonNull(converter);

        String key = getKey(source, targetClass);

        BeanCopier beanCopier = getBeanCopier(useCache, key, createBeanCopier(source, targetClass, useConverter));

        T instance;
        try {
            instance = targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("Copy bean failed. e: ", e);
            throw new RuntimeException(e);
        }
        beanCopier.copy(source, instance, converter);
        return instance;
    }

    public static <S, T> T copy(S source, T target) {
        return copy(source, target, USE_CACHE, null);
    }

    public static <S, T> T copy(S source, Class<T> targetClass) {
        return copy(source, targetClass, USE_CACHE, null);
    }

    public static <S, T> T copy(S source, Class<T> targetClass, boolean useCache) {
        return copy(source, targetClass, useCache, null);
    }

    public static <S, T> T copy(S source, Class<T> targetClass, Converter converter) {
        return copy(source, targetClass, USE_CACHE, converter);
    }

    public static <S, T> T copy(S source, T target, boolean useCache) {
        return copy(source, target, useCache, null);
    }

    public static <S, T> T copy(S source, T target, Converter converter) {
        return copy(source, target, USE_CACHE, converter);
    }

    public static <S, T> List<T> copy(List<S> sources, T target) {
        List<T> targets = new ArrayList<>(sources.size());
        sources.forEach(source -> targets.add(copy(source, target, USE_CACHE, null)));
        return targets;
    }

    public static <S, T> List<T> copy(List<S> sources, Class<T> targetClass) {
        List<T> targets = new ArrayList<>(sources.size());
        sources.forEach(source -> targets.add(copy(source, targetClass, USE_CACHE, null)));
        return targets;
    }

    public static <S, T> Set<T> copy(Set<S> sources, T target) {
        Set<T> targets = new HashSet<>(sources.size());
        sources.forEach(source -> targets.add(copy(source, target, USE_CACHE, null)));
        return targets;
    }

    public static <S, T> Set<T> copy(Set<S> sources, Class<T> targetClass) {
        Set<T> targets = new HashSet<>(sources.size());
        sources.forEach(source -> targets.add(copy(source, targetClass, USE_CACHE, null)));
        return targets;
    }

    private static BeanCopier getBeanCopier(boolean useCache, String key, BeanCopier beanCopier2) {
        BeanCopier beanCopier;
        if (useCache) {
            beanCopier = BEAN_COPIER_MAP.computeIfAbsent(key, k -> beanCopier2);
        } else {
            beanCopier = beanCopier2;
        }
        return beanCopier;
    }

    private static <S, T> BeanCopier createBeanCopier(S source, Class<T> target, boolean useConverter) {
        return BeanCopier.create(source.getClass(), target, useConverter);
    }

    private static <S, T> String getKey(S source, Class<T> targetClass) {
        return source.getClass().getName() + ":" + targetClass.getName();
    }

    private static <S> void requireNonNull(S source, Object targetClass) {
        Objects.requireNonNull(source, "Source object cannot be empty.");
        Objects.requireNonNull(targetClass, "Target object cannot be empty.");
    }

    /**
     * <p>
     * 不推荐使用, 两个bean尽量属性名相同, 属性类型相同, set get相同.
     * 获取默认转换器, 此转换器有性能问题.
     * 如数据量不大, 可以使用.
     * 如数据量很大, 推荐使用mapstruct框架. 可以继承通用接口: {@link com.wz.common.mapstruct.BaseMapping}
     * </P>
     *
     * @return Converter instance
     */
    public static Converter getDefaultConverter() {
        return DefaultConverter.DEFAULT_CONVERTER;
    }

    private static class DefaultConverter implements Converter {
        private static final DefaultConverter DEFAULT_CONVERTER = new DefaultConverter();

        private DefaultConverter() {
        }

        /**
         * @param sourceValue         源值
         * @param targetValueClass    目标类型值class
         * @param targetSetMethodName 目标set方法名称
         * @return 返回处理后的值
         */
        @Override
        public Object convert(Object sourceValue, Class targetValueClass, Object targetSetMethodName) {
            if (Objects.isNull(sourceValue)) {
                return null;
            }
            final Class<?> sourceValueClass = sourceValue.getClass();
            // 相同class对象, 直接返回
            if (sourceValueClass == targetValueClass) {
                return sourceValue;
            }
            // 处理字符串
            if (sourceValueClass == String.class) {
                final String v = String.valueOf(sourceValue);
                if (targetValueClass == int.class || targetValueClass == Integer.class) {
                    return Integer.parseInt(v);
                } else if (targetValueClass == long.class || targetValueClass == Long.class) {
                    return Long.parseLong(v);
                } else if (targetValueClass == float.class || targetValueClass == Float.class) {
                    return Float.parseFloat(v);
                } else if (targetValueClass == double.class || targetValueClass == Double.class) {
                    return Double.parseDouble(v);
                } else if (targetValueClass == char.class || targetValueClass == Character.class) {
                    return v.charAt(0);
                } else if (targetValueClass == boolean.class || targetValueClass == Boolean.class) {
                    return Boolean.parseBoolean(v);
                } else if (targetValueClass == byte.class || targetValueClass == Byte.class) {
                    return Byte.parseByte(v);
                } else if (targetValueClass == short.class || targetValueClass == Short.class) {
                    return Short.parseShort(v);
                } else if (targetValueClass == BigDecimal.class) {
                    return new BigDecimal(v);
                } else if (targetValueClass == Date.class) {
                    return DateUtil.dateTimeDefaultParse(v);
                } else if (targetValueClass == LocalTime.class) {
                    return LocalDateTime.parse(v, DateConsts.DATE_HH_MM_SS_FORMATTER);
                } else if (targetValueClass == LocalDate.class) {
                    return LocalDateTime.parse(v, DateConsts.DATE_DEFAULT_FORMATTER);
                } else if (targetValueClass == LocalDateTime.class) {
                    return LocalDateTime.parse(v, DateConsts.DATE_TIME_HH_MM_SS_FORMATTER);
                }
            }
            // 不等于字符串, 通过json方式进行转换
            final String json = JsonUtil.toJson(sourceValue);
            try {
                return JsonUtil.toBean(json, targetValueClass);
            } catch (Exception e) {
                log.error("Converter Failed. sourceValue: [{}], targetValueClass: [{}], targetSetMethodName: [{}]. e: ",
                        sourceValue, targetValueClass, targetSetMethodName, e);
                return null;
            }
        }
    }

}
