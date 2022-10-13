package com.wz.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

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

    public static boolean isAllBlank(CharSequence... css) {
        return StringUtils.isAllBlank(css);
    }

    public static boolean isNoneBlank(CharSequence... css) {
        return StringUtils.isNoneBlank(css);
    }

    public static List<String> split(CharSequence str, String separator) {
        requireNonNull(str, "string is not null");
        requireNonNull(separator, "separator is not null");
        Iterable<String> iterable = Splitter.on(separator)
                .omitEmptyStrings()
                .trimResults()
                .split(str);
        return Lists.newArrayList(iterable);
    }

    public static <T> String join(Iterable<T> strings, String separator) {
        Objects.requireNonNull(strings, "strings is not null");
        requireNonNull(separator, "separator is not null");
        return Joiner.on(separator)
                .join(strings);
    }

    public static void main(String[] args) {
        System.out.println(isNoneBlank(null, null)); // false
        System.out.println(isNoneBlank(null, "")); // false
        System.out.println(isNoneBlank("", "")); // false
        System.out.println(isNoneBlank("", "1")); // false
        System.out.println(isNoneBlank("1", "")); // false
        System.out.println(isNoneBlank("1", "1")); // true
    }
}
