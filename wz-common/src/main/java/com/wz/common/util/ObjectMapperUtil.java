package com.wz.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
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
import com.wz.common.exception.Assert;
import com.wz.common.exception.ExceptionUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: ObjectMapperUtil
 * @description:
 * @author: zhi
 * @date: 2023/2/21
 * @version: 1.0
 */
@Slf4j
@UtilityClass
public class ObjectMapperUtil {
    public static final byte[] EMPTY = new byte[0];

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
        //MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
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
        javaTimeModule.addSerializer(Date.class, DateSerializer.instance);
        javaTimeModule.addDeserializer(Date.class, DateDeserializers.DateDeserializer.instance);

        MAPPER.registerModules(javaTimeModule);
        // 支持kotlin模块， 需要引入kotlin依赖
        registerModules("com.fasterxml.jackson.module.kotlin.KotlinModule");
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static void registerModules(Module... modules) {
        if (modules != null && modules.length > 0) {
            MAPPER.registerModules(modules);
        }
    }

    public static void registerModules(String className) {
        boolean presentKotlinModule = ClassUtils.isPresent(className, null);
        if (presentKotlinModule) {
            try {
                registerModules((Module) ClassUtils.getConstructorIfAvailable(ClassUtils.forName(className, null)).newInstance());
            } catch (Exception e) {
                log.error(">>> ObjectMapper registerModules error. className: {}, e: ", className, e);
            }
        }
    }

    public static <T> byte[] toBytes(T t) {
        Assert.notNull(t, "序列化对象不能为空");
        try {
            return MAPPER.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            ExceptionUtil.wrapAndThrow(e);
        }

        return EMPTY;
    }

    public static <T> T toBean(byte[] bytes, Class<T> clazz) {
        Assert.notNull(bytes, "反序列化bytes不能为空");
        Assert.notNull(clazz, "反序列化clazz不能为空");
        try {
            return MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return null;
    }
}
