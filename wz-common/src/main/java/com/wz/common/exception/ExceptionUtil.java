package com.wz.common.exception;

/**
 * @projectName: wz-component
 * @package: com.wz.common.exception
 * @className: ExceptionUtil
 * @description:
 * @author: zhi
 * @date: 2021/7/30
 * @version: 1.0
 */
public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static RuntimeException wrap(Throwable t) {
        return t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
    }

    public static CommonException wrap(SystemException e) {
        return new CommonException(e.getCode(), e.getMsg());
    }
}
