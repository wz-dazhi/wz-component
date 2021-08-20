package com.wz.common.exception;

import java.util.function.Function;
import java.util.function.Supplier;

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

    public static CommonException wrap(Exception e) {
        return new CommonException(e.getMessage());
    }

    public static CommonException wrap(SystemException e) {
        return new CommonException(e.getCode(), e.getMsg());
    }

    public static <T> void throwRuntimeIfNecessary(Function<T, Boolean> isThrow, T t, Supplier<Throwable> supplier) {
        if (isThrow.apply(t)) {
            if (null == supplier || supplier.get() == null) {
                throw new RuntimeException("Supplier cannot is null.");
            }
            throw wrap(supplier.get());
        }
    }

    public static <T> void throwCommonIfNecessary(Function<T, Boolean> isThrow, T t, Supplier<Exception> supplier) {
        if (isThrow.apply(t)) {
            if (null == supplier || supplier.get() == null) {
                throw new CommonException("Supplier cannot is null.");
            }
            throw wrap(supplier.get());
        }
    }

    public static <T, R extends RuntimeException> void throwOriginIfNecessary(Function<T, Boolean> isThrow, T t, Supplier<R> supplier) {
        if (isThrow.apply(t)) {
            if (null == supplier || supplier.get() == null) {
                throw new RuntimeException("Supplier cannot is null.");
            }
            throw supplier.get();
        }
    }

}
