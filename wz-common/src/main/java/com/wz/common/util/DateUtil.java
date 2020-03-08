package com.wz.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import static com.wz.common.constant.DateConsts.*;

/**
 * @projectName: wz
 * @package: com.wz.common.util
 * @className: DateUtil
 * @description: java8 日期工具类
 * @author: Zhi Wang
 * @date: 2018/12/21 下午3:22
 * @version: 1.0
 **/
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 获取当天距离凌晨00:00剩余多少秒
     *
     * @return
     */
    public static long getRemainSecondsOneDay() {
        LocalDateTime nowTime = nowTime();
        LocalDateTime nextDay = nowTime.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return betweenSeconds(nowTime, nextDay);
    }

    /**
     * 两个日期相差的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static long betweenSeconds(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.SECONDS.between(start, end);
    }

    /**
     * 两个日期相差的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static long betweenDays(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 两个日期相差的周数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static long betweenWeeks(LocalDate start, LocalDate end) {
        return ChronoUnit.WEEKS.between(start, end);
    }

    /**
     * 两个日期相差的月数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static long betweenMonths(LocalDate start, LocalDate end) {
        return ChronoUnit.MONTHS.between(start, end);
    }

    /**
     * 两个日期相差的年数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static long betweenYears(LocalDate start, LocalDate end) {
        return ChronoUnit.YEARS.between(start, end);
    }

    /**
     * 日期添加年
     *
     * @param date       Date对象
     * @param yearsToAdd the years to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusYears(Date date, long yearsToAdd) {
        return dateToLocalDateTime(date).plusYears(yearsToAdd);
    }

    /**
     * 日期添加月
     *
     * @param date        Date对象
     * @param monthsToAdd the months to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusMonths(Date date, long monthsToAdd) {
        return dateToLocalDateTime(date).plusMonths(monthsToAdd);
    }

    /**
     * 日期添加周
     *
     * @param date       Date对象
     * @param weeksToAdd the weeks to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusWeeks(Date date, long weeksToAdd) {
        return dateToLocalDateTime(date).plusWeeks(weeksToAdd);
    }

    /**
     * 日期添加天数
     *
     * @param date      Date对象
     * @param daysToAdd the days to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusDays(Date date, long daysToAdd) {
        return dateToLocalDateTime(date).plusDays(daysToAdd);
    }

    /**
     * 日期添加小时
     *
     * @param date      Date对象
     * @param daysToAdd the days to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusHours(Date date, long daysToAdd) {
        return dateToLocalDateTime(date).plusHours(daysToAdd);
    }

    /**
     * 日期添加分钟
     *
     * @param date      Date对象
     * @param daysToAdd the days to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusMinutes(Date date, long daysToAdd) {
        return dateToLocalDateTime(date).plusMinutes(daysToAdd);
    }

    /**
     * 日期添加秒
     *
     * @param date      Date对象
     * @param daysToAdd the days to add, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime plusSeconds(Date date, long daysToAdd) {
        return dateToLocalDateTime(date).plusSeconds(daysToAdd);
    }

    /**
     * 日期减去年
     *
     * @param date            Date对象
     * @param yearsToSubtract the years to subtract, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime minusYears(Date date, long yearsToSubtract) {
        return dateToLocalDateTime(date).minusYears(yearsToSubtract);
    }

    /**
     * 日期减去月
     *
     * @param date             Date对象
     * @param monthsToSubtract the months to subtract, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime minusMonths(Date date, long monthsToSubtract) {
        return dateToLocalDateTime(date).minusMonths(monthsToSubtract);
    }

    /**
     * 日期减去周
     *
     * @param date            Date对象
     * @param weeksToSubtract the weeks to subtract, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime minusWeeks(Date date, long weeksToSubtract) {
        return dateToLocalDateTime(date).minusWeeks(weeksToSubtract);
    }

    /**
     * 日期减去天数
     *
     * @param date           Date对象
     * @param daysToSubtract the days to subtract, may be negative
     * @return LocalDateTime
     */
    public static LocalDateTime minusDays(Date date, long daysToSubtract) {
        return dateToLocalDateTime(date).minusDays(daysToSubtract);
    }

    /**
     * 判断是否属于日期格式
     *
     * @param date 20181222
     * @return true or false
     */
    public static boolean isYYYYMMDD(int date) {
        if (date <= 0) {
            return false;
        }
        String text = String.valueOf(date);
        if (8 != text.length()) {
            return false;
        }
        int year = Integer.parseInt(text.substring(0, 4));
        int month = Integer.parseInt(text.substring(4, 6));
        int dayOfMonth = Integer.parseInt(text.substring(6, 8));
        try {
            of(year, month, dayOfMonth);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    /**
     * int类型转换成日期
     *
     * @param date 20181222
     * @return 2018-12-22
     */
    public static String yyyyMMDDToDefaultDateStr(int date) {
        return yyyyMMDDToDateStr(date, DATE_DEFAULT_FORMATTER);
    }

    /**
     * int类型转换成日期
     *
     * @param date    20181222
     * @param pattern the pattern to use, not null
     * @return 格式化之后的日期字符串
     */
    public static String yyyyMMDDToDateStr(int date, String pattern) {
        return yyyyMMDDToDateStr(date, getDateTimeFormatterOfPattern(pattern));
    }

    /**
     * int类型转换成日期
     *
     * @param date      20181222
     * @param formatter DateTimeFormatter对象
     * @return 格式化之后的日期字符串
     */
    public static String yyyyMMDDToDateStr(int date, DateTimeFormatter formatter) {
        LocalDate localDate = yyyyMMDDToLocalDate(date);
        if (null == localDate) {
            return "";
        }
        Objects.requireNonNull(formatter, "formatter");
        return localDate.format(formatter);
    }

    public static LocalDate yyyyMMDDToLocalDate(int date) {
        if (date <= 0) {
            return null;
        }
        String text = String.valueOf(date);
        if (8 != text.length()) {
            return null;
        }
        int year = Integer.parseInt(text.substring(0, 4));
        int month = Integer.parseInt(text.substring(4, 6));
        int dayOfMonth = Integer.parseInt(text.substring(6, 8));
        LocalDate localDate;
        try {
            localDate = of(year, month, dayOfMonth);
        } catch (RuntimeException e) {
            return null;
        }
        return localDate;
    }

    /**
     * 年月日转换成LocalDate
     *
     * @param year       年: 2018
     * @param month      月: 1~12
     * @param dayOfMonth 天: 1~31
     * @return LocalDate
     */
    public static LocalDate of(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * 字符串解析成日期
     *
     * @param text "2018-12-22"
     * @return Date
     */
    public static Date dateDefaultParse(CharSequence text) {
        return dateParse(text, DATE_DEFAULT_FORMATTER);
    }

    /**
     * 字符串解析成日期 + 时间
     *
     * @param text "2018-12-22 14:43:22"
     * @return Date
     */
    public static Date dateTimeDefaultParse(CharSequence text) {
        return dateTimeParse(text, DATE_TIME_HH_MM_SS_FORMATTER);
    }

    /**
     * 字符串解析成日期
     *
     * @param text    "2018-12-22 11:31:21" 注意: 包含时分秒
     * @param pattern the pattern to use, not null
     * @return
     */
    public static Date dateTimeParse(CharSequence text, String pattern) {
        return dateTimeParse(text, getDateTimeFormatterOfPattern(pattern));
    }

    /**
     * 字符串解析成日期
     *
     * @param text      "2018-12-22 11:31:21" 注意: 包含时分秒
     * @param formatter DateTimeFormatter对象
     * @return
     */
    public static Date dateTimeParse(CharSequence text, DateTimeFormatter formatter) {
        return localDateTimeToDate(LocalDateTime.parse(text, formatter));
    }

    /**
     * 字符串解析成日期
     *
     * @param text    "2018-12-22" 注意: 不包括时分秒
     * @param pattern the pattern to use, not null
     * @return
     */
    public static Date dateParse(CharSequence text, String pattern) {
        return dateParse(text, getDateTimeFormatterOfPattern(pattern));
    }

    /**
     * 字符串解析成日期
     *
     * @param text      "2018-12-22" 注意: 不包括时分秒
     * @param formatter DateTimeFormatter对象
     * @return
     */
    public static Date dateParse(CharSequence text, DateTimeFormatter formatter) {
        return localDateToDate(LocalDate.parse(text, formatter));
    }

    /**
     * 格式化日期
     *
     * @param date    Date
     * @param pattern the pattern to use, not null
     * @return
     */
    public static String dateFormat(Date date, String pattern) {
        return dateFormat(date, getDateTimeFormatterOfPattern(pattern));
    }

    /**
     * 格式化日期
     *
     * @param date      Date
     * @param formatter DateTimeFormatter, 优先使用本类定义的常量
     * @return
     */
    public static String dateFormat(Date date, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return dateToLocalDateTime(date).format(formatter);
    }

    /**
     * 获取当前本地时间
     *
     * @return 2018-12-22
     */
    public static String getCurrentDateStr() {
        return now().toString();
    }

    /**
     * 获取当前本地时间
     *
     * @return 2018-12-22 11:48:32
     */
    public static String getCurrentDateTimeStr() {
        return localDateTimeFormat(nowTime(), DATE_TIME_HH_MM_SS_FORMATTER);
    }

    /**
     * 获取当前本地时间
     *
     * @param formatter 格式化
     * @return 2018-12-22 11:48:32
     */
    public static String getCurrentDateTimeStr(DateTimeFormatter formatter) {
        return localDateTimeFormat(nowTime(), formatter);
    }

    /**
     * 获取当前本地时间
     *
     * @param formatter 格式化对象 {@link DateTimeFormatter}
     * @return
     */
    public static String getCurrentDateStr(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return localDateFormat(now(), formatter);
    }

    /**
     * LocalDate 格式化
     *
     * @param localDate LocalDate 对象
     * @param formatter DateTimeFormatter格式化对象
     * @return
     */
    public static String localDateFormat(LocalDate localDate, DateTimeFormatter formatter) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(formatter, "formatter");
        return localDate.format(formatter);
    }

    /**
     * LocalDateTime 格式化
     *
     * @param localDateTime LocalDateTime对象
     * @param formatter     DateTimeFormatter格式化对象
     * @return
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(formatter, "formatter");
        return localDateTime.format(formatter);
    }

    /**
     * 获取DateTimeFormatter 对象
     *
     * @param pattern the pattern to use, not null
     * @return DateTimeFormatter
     */
    public static DateTimeFormatter getDateTimeFormatterOfPattern(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * LocalDateTime转时间戳
     *
     * @param localDateTime LocalDateTime对象
     * @return 时间戳
     */
    public static long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "LocalDateTime cannot is null");
        return localDateTime.atZone(getDefaultZoneId()).toInstant().toEpochMilli();
    }

    /**
     * LocalDate 转时间戳
     *
     * @param localDate LocalDate
     * @return
     */
    public static long localDateToTimestamp(LocalDate localDate) {
        Objects.requireNonNull(localDate, "LocalDate cannot is null");
        return localDate.atStartOfDay(getDefaultZoneId()).toInstant().toEpochMilli();
    }

    /**
     * Date 转时间戳
     *
     * @param date Date
     * @return
     */
    public static long dateToTimestamp(Date date) {
        Objects.requireNonNull(date, "Date cannot is null");
        return date.getTime();
    }

    /**
     * 时间戳转Date
     *
     * @param epochSecond 时间戳 * 1000
     * @return
     */
    public static Date timestampToDate(long epochSecond) {
        return new Date(epochSecond);
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param epochSecond 时间戳(带毫秒)
     * @return
     */
    public static LocalDateTime timestampToLocalDateTime(long epochSecond) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochSecond), getDefaultZoneId());
    }

    /**
     * 获取当前LocalDate 对象
     *
     * @return LocalDate
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 获取当前LocalDateTime 对象
     *
     * @return LocalDateTime
     */
    public static LocalDateTime nowTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前Date对象
     *
     * @return Date
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate LocalDate
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Objects.requireNonNull(localDate, "localDate");
        return Date.from(localDate.atStartOfDay(getDefaultZoneId()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        return Date.from(localDateTime.atZone(getDefaultZoneId()).toInstant());
    }

    /**
     * date转LocalDate
     *
     * @param date Date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToZonedDateTime(date).toLocalDate();
    }

    /**
     * date转LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return dateToZonedDateTime(date).toLocalDateTime();
    }

    /**
     * date转系统时区日期
     *
     * @param date Date
     * @return ZonedDateTime
     */
    private static ZonedDateTime dateToZonedDateTime(Date date) {
        Objects.requireNonNull(date, "date");
        return date.toInstant().atZone(getDefaultZoneId());
    }

    /**
     * 获取系统默认时区
     *
     * @return ZoneId
     */
    private static ZoneId getDefaultZoneId() {
        return ZoneId.systemDefault();
    }

}
