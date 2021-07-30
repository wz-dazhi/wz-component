package com.wz.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wz.common.constant.DateConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @projectName: wz-common
 * @package: com.wz.common.util
 * @className: MapUtil
 * @description: map 工具类
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午11:42
 **/
@Slf4j
public final class MapUtil {

    private MapUtil() {
    }

    /**
     * 将对象装换为map
     */
    public static <T, V> Map<String, V> beanToMap(T bean) {
        Map<String, V> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.forEach((k, v) -> map.put(String.valueOf(k), (V) v));
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     */
    public static <T, V> T mapToBean(Map<String, V> map, T bean) {
        if (null == bean) {
            throw new NullPointerException("Map to bean error. bean is null...");
        }
        mapToBeanProcess(map, bean);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    private static <T, V> void mapToBeanProcess(Map<String, V> map, T bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field f : fields) {
            setMap(f, map);
        }
    }

    private static <V> void setMap(Field f, Map<String, V> map) {
        ReflectionUtils.makeAccessible(f);
        // Bean field
        String fieldName = f.getName();
        Class<?> fieldType = f.getType();
        String typeName = fieldType.getTypeName();
        // Map value
        V value = map.get(fieldName);
        if (null == value) {
            return;
        }
        // Map valueType == fieldType return;
        if (typeName.equals(value.getClass().getTypeName())) {
            return;
        }

        // 重新设置map value的类型
        map.put(fieldName, (V) getValue(typeName, String.valueOf(value)));
    }

    private static Object getValue(String typeName, String value) {
        switch (typeName) {
            case "java.util.Date":
                return DateUtil.dateTimeParse(value, DateConsts.DATE_TIME_HH_MM_SS_FORMATTER);
            case "java.time.LocalDate":
                return LocalDate.parse(value);
            case "java.time.LocalDateTime":
                return LocalDateTime.parse(value, DateConsts.DATE_TIME_HH_MM_SS_FORMATTER);
            case "java.time.LocalTime":
                return LocalTime.parse(value);
            case "java.math.BigDecimal":
                return new BigDecimal(value);
            case "java.lang.Integer":
                return Integer.valueOf(value);
            case "int":
                return Integer.parseInt(value);
            case "java.lang.Double":
                return Double.valueOf(value);
            case "double":
                return Double.parseDouble(value);
            case "java.lang.Long":
                return Long.valueOf(value);
            case "long":
                return Long.parseLong(value);
            case "java.lang.Float":
                return Float.valueOf(value);
            case "float":
                return Float.parseFloat(value);
            case "java.lang.Boolean":
                return Boolean.valueOf(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            case "java.lang.Byte":
                return Byte.valueOf(value);
            case "byte":
                return Byte.parseByte(value);
            case "java.lang.Short":
                return Short.valueOf(value);
            case "short":
                return Short.parseShort(value);
            default:
                return null;
        }
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     */
    public static <T, V> List<Map<String, V>> objectsToMaps(List<T> objList) {
        List<Map<String, V>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            objList.forEach(t -> list.add(beanToMap(t)));
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     */
    public static <T, V> List<T> mapsToObjects(List<Map<String, V>> maps, Class<T> clazz) {
        if (maps != null && maps.size() > 0) {
            List<T> list = Lists.newArrayList();
            for (Map<String, V> m : maps) {
                try {
                    list.add(mapToBean(m, clazz.getDeclaredConstructor().newInstance()));
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    log.error("Maps to Objects Error. maps: {}, bean name: {}, msg: {}", JsonUtil.toJson(maps), clazz.getName(), e.getMessage());
                    return Collections.emptyList();
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return null == map || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

}
