package com.wz.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.wz.common.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: wz
 * @package: com.wz.common.util
 * @className: JsonUtil
 * @description: 使用jackson
 * @author: Zhi
 * @date: 2019-09-09 11:14
 * @version: 1.0
 */
@Slf4j
public final class JsonUtil {


    private JsonUtil() {
    }

    /**
     * 对象转json字符串
     */
    public static <T> String toJson(T t) {
        Objects.requireNonNull(t, "t is null.");
        try {
            return ObjectMapperUtil.getMapper().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. t: {}, msg: {}", t, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    /**
     * 对象转json字符串
     *
     * @param isPretty 是否需要格式化
     * @param t
     */
    public static <T> String toJson(boolean isPretty, T t) {
        try {
            if (isPretty) {
                Objects.requireNonNull(t, "t is null.");
                return ObjectMapperUtil.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(t);
            } else {
                return toJson(t);
            }
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. isPretty: {}, t: {}, msg: {}", isPretty, t, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return ObjectMapperUtil.getMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error("json 字符串转Bean发生异常. json: {}, clazz: {}, msg: {}", json, clazz, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <T> T toBean(File file, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return ObjectMapperUtil.getMapper().readValue(file, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串文件[{}]转Bean发生异常. msg: {}", file, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <T> T toBean(InputStream is, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return ObjectMapperUtil.getMapper().readValue(is, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}", is, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <T> T toBean(URL url, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return ObjectMapperUtil.getMapper().readValue(url, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}", url, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <E> List<E> toList(String json, Class<? extends List> listClass, Class<E> clazz) {
        CollectionType collectionType = collectionType(listClass, clazz);
        try {
            return ObjectMapperUtil.getMapper().readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("json 转List异常。json: {}, listClass: {}, clazz: {}. msg: {}", json, listClass, clazz, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <E> List<E> toArrayList(String json, Class<E> clazz) {
        return toList(json, ArrayList.class, clazz);
    }

    public static <E> List<E> toLinkedList(String json, Class<E> clazz) {
        return toList(json, LinkedList.class, clazz);
    }

    public static <E> Set<E> toSet(String json, Class<? extends Set> setClass, Class<E> clazz) {
        CollectionType collectionType = collectionType(setClass, clazz);
        try {
            return ObjectMapperUtil.getMapper().readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("json 转Set异常。json: {}, setClass: {}, clazz: {}. msg: {}", json, setClass, clazz, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <E> Set<E> toHashSet(String json, Class<E> clazz) {
        return toSet(json, HashSet.class, clazz);
    }

    public static <E> Set<E> toLinkedHashSet(String json, Class<E> clazz) {
        return toSet(json, LinkedHashSet.class, clazz);
    }

    public static <E> Set<E> toTreeSet(String json, Class<E> clazz) {
        return toSet(json, TreeSet.class, clazz);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<? extends Map> mapClass, Class<K> kClass, Class<V> vClass) {
        MapType mapType = ObjectMapperUtil.getMapper().getTypeFactory().constructMapType(mapClass, kClass, vClass);
        try {
            return ObjectMapperUtil.getMapper().readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("json 转Map异常。json: {}, mapClass: {}, kClass: {}, vClass: {}. msg: {}", json, mapClass, kClass, vClass, e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

    public static <K, V> Map<K, V> toHashMap(String json, Class<K> kClass, Class<V> vClass) {
        return toMap(json, HashMap.class, kClass, vClass);
    }

    public static <K, V> Map<K, V> toLinkedHashMap(String json, Class<K> kClass, Class<V> vClass) {
        return toMap(json, LinkedHashMap.class, kClass, vClass);
    }

    public static <K, V> Map<K, V> toTreeMap(String json, Class<K> kClass, Class<V> vClass) {
        return toMap(json, TreeMap.class, kClass, vClass);
    }

    public static <K, V> Map<K, V> toConcurrentHashMap(String json, Class<K> kClass, Class<V> vClass) {
        return toMap(json, ConcurrentHashMap.class, kClass, vClass);
    }

    private static CollectionType collectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return ObjectMapperUtil.getMapper().getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public static <T> T readValue(String json, TypeReference<T> reference) {
        try {
            return ObjectMapperUtil.getMapper().readValue(json, reference);
        } catch (JsonProcessingException e) {
            log.error("json 转<T>异常. json: {}, type: {}, msg: {}", json, reference.getType(), e.getMessage());
            throw ExceptionUtil.wrapCommon(e);
        }
    }

}
