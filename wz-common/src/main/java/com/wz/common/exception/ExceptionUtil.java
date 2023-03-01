package com.wz.common.exception;

import lombok.experimental.UtilityClass;

/**
 * @projectName: wz-component
 * @package: com.wz.common.exception
 * @className: ExceptionUtil
 * @description:
 * @author: zhi
 * @date: 2022/10/11
 * @version: 1.0
 */
@UtilityClass
public class ExceptionUtil {

    public static RuntimeException wrapRuntime(Throwable t) {
        return t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
    }

    public static CommonException wrapCommon(Exception e) {
        return new CommonException(e);
    }

    public static CommonException wrapCommon(SystemException e) {
        return new CommonException(e.getCode(), e.getMsg());
    }

    public static void wrapAndThrow(Exception e) {
        if (e instanceof SystemException) {
            throw wrapCommon((SystemException) e);
        }
        throw wrapCommon(e);
    }
}
