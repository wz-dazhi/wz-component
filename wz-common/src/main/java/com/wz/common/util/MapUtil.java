package com.wz.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wz.common.constant.DateConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
public class MapUtil {

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.forEach((k, v) -> map.put(String.valueOf(k), v));
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (null == bean) {
            throw new NullPointerException("Map to bean error. bean is null...");
        }
        mapToBeanProcess(map, bean);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    private static <T> void mapToBeanProcess(Map<String, Object> map, T bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            String fieldName = f.getName();
            Class<?> fieldType = f.getType();
            Object value = map.get(fieldName);
            if (null != value && fieldType != String.class) {
                String valueTypeName = value.getClass().getTypeName();
                if (fieldType == Date.class && !"java.util.Date".equals(valueTypeName)) {
                    map.put(fieldName, DateUtil.dateTimeParse(String.valueOf(value), DateConsts.DATE_TIME_HH_MM_SS_FORMATTER));
                } else if (fieldType == LocalDate.class && !"java.time.LocalDate".equals(valueTypeName)) {
                    map.put(fieldName, LocalDate.parse(String.valueOf(value)));
                } else if (fieldType == LocalDateTime.class && !"java.time.LocalDateTime".equals(valueTypeName)) {
                    map.put(fieldName, LocalDateTime.parse(String.valueOf(value), DateConsts.DATE_TIME_HH_MM_SS_FORMATTER));
                } else if (fieldType == LocalTime.class && !"java.time.LocalTime".equals(valueTypeName)) {
                    map.put(fieldName, LocalTime.parse(String.valueOf(value)));
                } else if (fieldType == Long.class || fieldType == long.class) {
                    if ("java.lang.Integer".equals(valueTypeName) || "int".equals(valueTypeName)) {
                        map.put(fieldName, Long.valueOf(value.toString()));
                    }
                } else if (fieldType == Double.class || fieldType == double.class) {
                    if ("java.lang.Integer".equals(valueTypeName) || "int".equals(valueTypeName)) {
                        map.put(fieldName, Double.valueOf(value.toString()));
                    }
                }
            }
        }
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            objList.forEach(t -> list.add(beanToMap(t)));
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            maps.forEach(m -> {
                try {
                    list.add(mapToBean(m, clazz.newInstance()));
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("Maps to Objects Error. maps: {}, bean name: {}, msg: {}", JsonUtil.toJsonString(maps), clazz.getName(), e.getMessage());
                }
            });
        }
        return list;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

}
