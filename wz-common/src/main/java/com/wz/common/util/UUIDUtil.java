package com.wz.common.util;

import java.util.UUID;

/**
 * @projectName: wz-common
 * @package: com.wz.common.utils
 * @className: UUIDUtil
 * @description: 随机字符串工具类
 * @author: Zhi Wang
 * @date: 2018/12/21 下午3:13
 * @version: 1.0
 **/
public final class UUIDUtil {

    private static final String TARGET = "-";
    private static final String REPLACEMENT_BLANK = "";

    private UUIDUtil() {
    }

    /**
     * 随机字符串并转化成大写
     *
     * @return 例: 19EB68EE5ABE42CE9ADB57AAD8EA59DD
     */
    public static String getUUIDUpperCase() {
        return getUUIDUpper().replace(TARGET, REPLACEMENT_BLANK);
    }

    /**
     * 随机字符串并转化成小写
     *
     * @return 例: 19eb68ee5abe42ce9adb57aad8ea59dd
     */
    public static String getUUIDLowerCase() {
        return getUUIDLower().replace(TARGET, REPLACEMENT_BLANK);
    }

    /**
     * 随机字符串
     *
     * @return 例: 19EB68EE-5ABE-42CE-9ADB-57AAD8EA59DD
     */
    public static String getUUIDUpper() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 随机字符串
     *
     * @return 例: 19eb68ee-5abe-42ce-9adb-57aad8ea59dd
     */
    public static String getUUIDLower() {
        return UUID.randomUUID().toString().toLowerCase();
    }

}
