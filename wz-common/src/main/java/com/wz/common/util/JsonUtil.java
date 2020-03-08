package com.wz.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
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
import java.util.Date;
import java.util.TimeZone;

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

        MAPPER.registerModule(javaTimeModule);
    }

    private JsonUtil() {
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * 对象转json字符串
     *
     * @param o
     * @return
     */
    public static String toJsonString(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. msg: {}, e: ", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 对象转json字符串
     *
     * @param isPretty 是否需要格式化
     * @param o
     * @return
     */
    public static String toJsonString(boolean isPretty, Object o) {
        try {
            if (isPretty) {
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                return toJsonString(o);
            }
        } catch (JsonProcessingException e) {
            log.error("Bean 转json字符串发生异常. msg: {}, e: ", e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error("json 字符串转Bean发生异常. msg: {}, e: ", e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toBean(File file, Class<T> clazz) {
        try {
            return MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串文件[{}]转Bean发生异常. msg: {}, e: ", file, e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toBean(InputStream is, Class<T> clazz) {
        try {
            return MAPPER.readValue(is, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}, e: ", is, e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toBean(URL url, Class<T> clazz) {
        try {
            return MAPPER.readValue(url, clazz);
        } catch (IOException e) {
            log.error("读取json 字符串输入流[{}]转Bean发生异常. msg: {}, e: ", url, e.getMessage(), e);
        }
        return null;
    }

}
