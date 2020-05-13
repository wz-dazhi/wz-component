package com.wz.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.wz.common.constant.DateConsts;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        MAPPER.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        // 允许字段不带双引号
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许字段为单引号
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许 json 存在没用引号括起来的 ascii 控制字符
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许 json 存在形如 // 或 /**/ 的注释
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 允许 json 生成器自动补全未匹配的括号
        MAPPER.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true);
        // 设置全局的时间转化
        SimpleDateFormat smt = new SimpleDateFormat(DateConsts.NORMAL_YYYY_MM_DD_HH_MM_SS_PATTERN);
        MAPPER.setDateFormat(smt);
        // 解决时区差8小时问题
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //LocalDateTime系列序列化和反序列化模块，继承自jsr310，我们在这里修改了日期格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateConsts.DATE_TIME_HH_MM_SS_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateConsts.DATE_DEFAULT_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateConsts.DATE_HH_MM_SS_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateConsts.DATE_TIME_HH_MM_SS_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateConsts.DATE_DEFAULT_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateConsts.DATE_HH_MM_SS_FORMATTER));

        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class, new DateSerializer());
        javaTimeModule.addDeserializer(Date.class, new DateDeserializers.DateDeserializer());

        MAPPER.registerModules(javaTimeModule, new KotlinModule());
    }

    private JsonUtil() {
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * 对象转json字符串
     *
     * @param t
     * @return
     */
    public static <T> String toJsonString(T t) {
        Objects.requireNonNull(t, "t is null.");
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. t: {}, msg: {}", t, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转json字符串
     *
     * @param isPretty 是否需要格式化
     * @param t
     * @return
     */
    public static <T> String toJsonString(boolean isPretty, T t) {
        try {
            if (isPretty) {
                Objects.requireNonNull(t, "t is null.");
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(t);
            } else {
                return toJsonString(t);
            }
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. isPretty: {}, t: {}, msg: {}", isPretty, t, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error("json 字符串转Bean发生异常. json: {}, clazz: {}, msg: {}", json, clazz, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(File file, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串文件[{}]转Bean发生异常. msg: {}", file, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(InputStream is, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return MAPPER.readValue(is, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}", is, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(URL url, Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null.");
        try {
            return MAPPER.readValue(url, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}", url, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <E> List<E> toList(String json, Class<? extends List> listClass, Class<E> clazz) {
        CollectionType collectionType = collectionType(listClass, clazz);
        try {
            return MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("json 转List异常。json: {}, listClass: {}, clazz: {}. msg: {}", json, listClass, clazz, e.getMessage());
            throw new RuntimeException(e);
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
            return MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("json 转Set异常。json: {}, setClass: {}, clazz: {}. msg: {}", json, setClass, clazz, e.getMessage());
            throw new RuntimeException(e);
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
        MapType mapType = MAPPER.getTypeFactory().constructMapType(mapClass, kClass, vClass);
        try {
            return MAPPER.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("json 转Map异常。json: {}, mapClass: {}, kClass: {}, vClass: {}. msg: {}", json, mapClass, kClass, vClass, e.getMessage());
            throw new RuntimeException(e);
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
        return MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public static <T> T readValue(String json, TypeReference<T> reference) {
        try {
            return MAPPER.readValue(json, reference);
        } catch (JsonProcessingException e) {
            log.error("json 转<T>异常. json: {}, type: {}, msg: {}", json, reference.getType(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
