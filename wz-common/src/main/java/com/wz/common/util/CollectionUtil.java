package com.wz.common.util;

import java.util.Collection;

/**
 * @projectName: wz
 * @package: com.common.util
 * @className: CollectionUtil
 * @description: 集合工具类
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午12:08
 **/
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 判断一个集合是否为空
     * null或者空集合都会返回true
     *
     * @param collection 需要判断的集合
     * @return 是否有值，null或者空集合都是返回true
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断一个集合是否不为空
     *
     * @param collection 需要判断的集合
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
