package com.wz.common.util;

import java.util.Objects;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: ObjectUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
public final class ObjectUtil {
    private ObjectUtil() {
    }

    public static boolean isAnyNull(Object... objs) {
        for (Object o : objs) {
            if (Objects.isNull(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllNull(Object... objs) {
        for (Object o : objs) {
            if (Objects.nonNull(o)) {
                return false;
            }
        }
        return true;
    }
}
