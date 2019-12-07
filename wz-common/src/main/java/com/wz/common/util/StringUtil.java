package com.wz.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @projectName: wz
 * @package: com.wz.common.util
 * @className: StringUtil
 * @description: 字符串工具类, 目前直接调用org.apache.commons.lang3.StringUtils 这么做的目的是为了后期如果想自己实现,直接修改本类就可以了
 * @author: Zhi Wang
 * @date: 2018/12/28 下午3:19
 * @version: 1.0
 **/
public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isBlank(CharSequence str) {
        return StringUtils.isBlank(str) || "null".contentEquals(str);
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return StringUtils.isNotEmpty(str);
    }

    public static CharSequence requireNonNull(CharSequence str, String msg) {
        if (isBlank(str)) {
            throw new NullPointerException(msg);
        }
        return str;
    }

    public static boolean isAnyBlank(CharSequence... css) {
        return StringUtils.isAnyBlank(css);
    }
}
