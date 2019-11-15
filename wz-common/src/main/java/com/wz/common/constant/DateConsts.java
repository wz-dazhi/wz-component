package com.wz.common.constant;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @projectName: wz
 * @package: com.wz.common.constant
 * @className: DateConsts
 * @description: 时间常量类
 * @author: Zhi Wang
 * @date: 2018/12/27 下午5:10
 * @version: 1.0
 **/
public final class DateConsts {

    private DateConsts() {
    }

    // pattern------------------------------------
    /**
     * yyyy-MM
     */
    public static final String NORMAL_YYYY_MM_PATTERN = "yyyy-MM";
    /**
     * yyyy-MM-dd
     */
    public static final String NORMAL_YYYY_MM_DD_PATTERN = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String NORMAL_YYYY_MM_DD_HH_MM_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String NORMAL_YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * HH:mm:ss
     */
    public static final String NORMAL_HH_MM_SS_PATTERN = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String NORMAL_YYYY_MM_DD_HH_MM_SSS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * yyyy.MM.dd
     */
    public static final String YYYY_MM_DD_PATTERN = "yyyy.MM.dd";
    /**
     * MM.dd
     */
    public static final String MM_DD_PATTERN = "MM.dd";
    /**
     * yyyy.MM.dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM_PATTERN = "yyyy.MM.dd HH:mm";
    /**
     * yyyy.MM.dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyy.MM.dd HH:mm:ss";
    /**
     * yyyy年MM月dd日
     */
    public static final String YEAR_MONTH_DAY_PATTERN = "yyyy年MM月dd日";
    /**
     * yyyy年MM月dd日 HH时mm分
     */
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_PATTERN = "yyyy年MM月dd日 HH时mm分";
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * yyyyMM
     */
    public static final String YYYYMM_PATTERN = "yyyyMM";
    /**
     * yyyyMMdd
     */
    public static final String YYYYMMDD_PATTERN = "yyyyMMdd";
    /**
     * yyyyMMddHH
     */
    public static final String YYYYMMDDHH_PATTERN = "yyyyMMddHH";
    /**
     * yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS_PATTERN = "yyyyMMddHHmmss";

    /**
     * yyyyMMddHHmmssSSS
     */
    public static final String YYYYMMDDHHMMSS_SSS_PATTERN = "yyyyMMddHHmmssSSS";

    // DateTimeFormatter-------------------------------
    /**
     * yyyy-MM-dd DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_DEFAULT_FORMATTER = ISO_LOCAL_DATE;
    /**
     * yyyy-MM-dd HH:mm DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_TIME_HH_MM_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_YYYY_MM_DD_HH_MM_PATTERN);
    /**
     * yyyy-MM-dd HH:mm:ss DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_TIME_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_YYYY_MM_DD_HH_MM_SS_PATTERN);
    /**
     * HH:mm:ss DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_HH_MM_SS_PATTERN);
    /**
     * yyyy-MM-dd HH:mm:ss.SSS DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_TIME_HH_MM_SS_SSS_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_YYYY_MM_DD_HH_MM_SSS_PATTERN);
    /**
     * yyyy.MM.dd DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_PATTERN);
    /**
     * yyyy.MM.dd DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_MM_DD_FORMATTER = DateTimeFormatter.ofPattern(MM_DD_PATTERN);
    /**
     * yyyy.MM.dd HH:mm DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_PATTERN);
    /**
     * yyyy.MM.dd HH:mm:ss DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YYYY_MM_DD_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS_PATTERN);
    /**
     * yyyy年MM月dd日 DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YEAR_MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_PATTERN);
    /**
     * yyyy年MM月dd日 HH时mm分 DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YEAR_MONTH_DAY_HOUR_MINUTE_FORMATTER = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_HOUR_MINUTE_PATTERN);
    /**
     * yyyy年MM月dd日 HH时mm分ss秒 DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
    /**
     * yyyyMM DateTimeFormatter
     */
    public static final DateTimeFormatter YYYYMM_FORMATTER = DateTimeFormatter.ofPattern(YYYYMM_PATTERN);
    /**
     * yyyyMMdd DateTimeFormatter
     */
    public static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDD_PATTERN);
    /**
     * yyyyMMddHH DateTimeFormatter
     */
    public static final DateTimeFormatter YYYYMMDDHH_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDDHH_PATTERN);
    /**
     * yyyyMMddHHmmss DateTimeFormatter
     */
    public static final DateTimeFormatter YYYYMMDDHHMMSS_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS_PATTERN);
    /**
     * yyyyMMddHHmmssSSS DateTimeFormatter
     */
    public static final DateTimeFormatter YYYYMMDDHHMMSS_SSS_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS_SSS_PATTERN);
    /**
     * yyyyMMdd DateTimeFormatter
     */
    public static final DateTimeFormatter DATE_YYYYMMDD_FORMATTER = BASIC_ISO_DATE;
}
