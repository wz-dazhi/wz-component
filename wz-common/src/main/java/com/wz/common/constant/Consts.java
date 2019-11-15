package com.wz.common.constant;

/**
 * @projectName: wz
 * @package: com.common.constant
 * @className: Consts
 * @description: 公共常量类
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午2:33
 **/
public class Consts {

    /**
     * 年月正则
     */
    public static final String YYYYMM_REGEX = "^\\d{4}-\\d{1,2}$";
    /**
     * 年月日正则
     */
    public static final String YYYYMMDD_REGEX = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
    /**
     * 年月日时分正则
     */
    public static final String YYYYMMDDHHMM_REGEX = "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$";
    /**
     * 年月日时分秒正则
     */
    public static final String YYYYMMDDHHMMSS_REGEX = "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$";
    /**
     * 逗号正则表达式
     */
    public static final String COMMA = ",";
    /**
     * 竖线正则表达式
     */
    public static final String VERTICAL_LINE = "\\|";
    /**
     * redis分割符号
     */
    public static final String SUBFIX = ":";
}
