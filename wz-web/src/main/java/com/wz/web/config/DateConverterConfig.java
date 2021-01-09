package com.wz.web.config;

import com.wz.common.constant.Consts;
import com.wz.common.constant.DateConsts;
import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.SystemException;
import com.wz.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static com.wz.common.constant.DateConsts.*;

/**
 * @projectName: wz-web
 * @package: com.wz.web.config
 * @className: DateConverterConfig
 * @description: 全局handler前日期统一处理
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午2:34
 **/
@Slf4j
@Configuration
public class DateConverterConfig {

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new LocalDateConverter();
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new LocalDateTimeConverter();
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new LocalTimeConverter();
    }

    /**
     * Date转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new DateConverter();
    }

    private static class DateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String source) {
            if (StringUtil.isBlank(source) || StringUtil.isBlank(source.trim())) {
                return null;
            }
            try {
                if (source.matches(Consts.YYYYMM_REGEX)) {
                    return parseDate(source, NORMAL_YYYY_MM_PATTERN);
                } else if (source.matches(Consts.YYYYMMDD_REGEX)) {
                    return parseDate(source, NORMAL_YYYY_MM_DD_PATTERN);
                } else if (source.matches(Consts.YYYYMMDDHHMM_REGEX)) {
                    return parseDate(source, NORMAL_YYYY_MM_DD_HH_MM_PATTERN);
                } else if (source.matches(Consts.YYYYMMDDHHMMSS_REGEX)) {
                    return parseDate(source, NORMAL_YYYY_MM_DD_HH_MM_SS_PATTERN);
                } else {
                    throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
                }
            } catch (SystemException e) {
                throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
            }
        }

        /**
         * 格式化日期
         *
         * @param dateStr String 字符型日期
         * @param format  String 格式
         * @return Date 日期
         */
        private static Date parseDate(String dateStr, String format) throws SystemException {
            Date date;
            DateFormat dateFormat = new SimpleDateFormat(format);
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                log.error("<<< date formatting exception msg: {}, e: {}", e.getMessage(), e);
                throw new SystemException(ResultEnum.SYSTEM_ERROR);
            }
            return date;
        }
    }

    private static class LocalTimeConverter implements Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            if (StringUtil.isBlank(source) || StringUtil.isBlank(source.trim())) {
                return null;
            }
            return LocalTime.parse(source, DateConsts.DATE_HH_MM_SS_FORMATTER);
        }
    }

    private static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            if (StringUtil.isBlank(source) || StringUtil.isBlank(source.trim())) {
                return null;
            }
            return LocalDateTime.parse(source, DateConsts.DATE_TIME_HH_MM_SS_FORMATTER);
        }
    }

    private static class LocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            if (StringUtil.isBlank(source) || StringUtil.isBlank(source.trim())) {
                return null;
            }
            return LocalDate.parse(source, DateConsts.DATE_DEFAULT_FORMATTER);
        }
    }
}
