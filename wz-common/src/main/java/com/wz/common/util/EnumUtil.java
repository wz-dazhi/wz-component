//package com.wz.common.util;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @projectName: wz-component
// * @package: com.wz.common.util
// * @className: EnumUtil
// * @description: 不建议使用, 暴力遍历满足大部分场景
// * @author: zhi
// * @date: 2021/3/27
// * @version: 1.0
// */
//@Slf4j
//public final class EnumUtil {
//    private static final Map<String, Map<Object, ? extends Enum<?>>> CACHE_MAP = new HashMap<>();
//
//    private EnumUtil() {
//    }
//
//    /**
//     * 在当前枚举类中static静态代码块中注册该枚举.
//     * 注意: 建议枚举超过20个元素以上的使用, 低于20个以下的元素不建议使用
//     * 1. 低于20个元素的在本类中使用遍历方式匹配示例:
//     * <pre>
//     * public static DeleteEnum getInstance(int delete) {
//     * for (DeleteEnum value : values()) {
//     * if (delete == value.getDelete()) {
//     * return value;
//     * }
//     * }
//     * return null;
//     * }
//     * </pre>
//     * 2. 超过20个元素以上的使用示例:
//     * <pre>
//     * static {
//     * EnumUtil.register(DeleteEnum.class, "getDelete");
//     * }
//     * </pre>
//     *
//     * @param e                  枚举Class
//     * @param getValueMethodName 获取枚举value的get方法名称
//     */
//    public static <E extends Enum<E>> void register(Class<E> e, String getValueMethodName) {
//        Objects.requireNonNull(e, "Enumeration Class is cannot null.");
//        Objects.requireNonNull(getValueMethodName, "Enumeration Class get value method name is cannot null.");
//        // 获取当前枚举类的枚举数组
//        final E[] es = e.getEnumConstants();
//        final String enumClassName = e.getName();
//        Map<Object, E> valueEnum = (Map<Object, E>) CACHE_MAP.get(enumClassName);
//        if (MapUtil.isEmpty(valueEnum)) {
//            valueEnum = new HashMap<>(es.length);
//        }
//        for (E en : es) {
//            final Class<? extends Enum> clazz = en.getClass();
//            Method m;
//            try {
//                // Enum父类方法name()
//                if ("name".equals(getValueMethodName)) {
//                    m = clazz.getSuperclass().getDeclaredMethod(getValueMethodName);
//                } else {
//                    m = clazz.getDeclaredMethod(getValueMethodName);
//                }
//                final Object o = m.invoke(en);
//                // value 对应的枚举值
//                valueEnum.put(o, en);
//            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
//                log.error("反射添加枚举缓存异常. 枚举类: {}, 获取value值的方法为: {}, msg: {}", e, getValueMethodName, ex.getMessage());
//                throw new RuntimeException(ex);
//            }
//        }
//        CACHE_MAP.put(enumClassName, valueEnum);
//    }
//
//    /**
//     * 根据枚举Class 的value值获取该枚举, 示例:
//     * <p>
//     * final DeleteEnum deleteEnum = EnumUtil.getEnumByValue(DeleteEnum.class, 1);
//     * </p>
//     *
//     * @param enumClass 枚举Class
//     * @param value     值
//     */
//    public static <E extends Enum<E>> E getEnumByValue(Class<E> enumClass, Object value) {
//        Objects.requireNonNull(enumClass, "Enumeration Class is cannot null.");
//        Objects.requireNonNull(value, "Enumeration Class value is cannot null.");
//        Map<Object, ? extends Enum<?>> map = CACHE_MAP.get(enumClass.getName());
//        // 当前枚举没有注册到cache中, 手动加载, 前提要在枚举静态代码块中加入注册代码; 如: EnumUtil.register(DeleteEnum.class, "getGender");
//        if (MapUtil.isEmpty(map)) {
//            try {
//                Class.forName(enumClass.getName());
//            } catch (ClassNotFoundException ignored) {
//            }
//        }
//        map = CACHE_MAP.get(enumClass.getName());
//        if (MapUtil.isEmpty(map)) {
//            throw new NullPointerException("The current enumerated class [" + enumClass.getName() + "] is not registered in the cache.");
//        }
//        return (E) map.get(value);
//    }
//
//    /**
//     * 根据枚举Class获取所有的枚举键值对
//     *
//     * @param enumClass 枚举Class
//     * @return DeleteEnum 如: {0=NO, 1=YES}
//     */
//    public static <E extends Enum<E>> Map<Object, E> getMapEnum(Class<E> enumClass) {
//        Objects.requireNonNull(enumClass, "Enumeration Class is cannot null.");
//        return (Map<Object, E>) CACHE_MAP.get(enumClass.getName());
//    }
//
//    /**
//     * 根据枚举Class获取所有的枚举列表
//     */
//    public static <E extends Enum<E>> Collection<E> getEnums(Class<E> enumClass) {
//        final Map<Object, E> mapEnum = getMapEnum(enumClass);
//        return MapUtil.isNotEmpty(mapEnum) ? mapEnum.values() : Collections.emptyList();
//    }
//}
