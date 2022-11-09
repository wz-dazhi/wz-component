package com.wz.common.exception;

import com.wz.common.enums.ResultEnum;
import com.wz.common.util.StringUtil;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @projectName: wz-component
 * @package: com.wz.common.exception
 * @className: Assert
 * @description:
 * @author: zhi
 * @date: 2021/7/30
 * @version: 1.0
 */
@UtilityClass
public class Assert {

    public static <T> void notNull(T t) {
        notNull(t, ResultEnum.PARAM_ERROR);
    }

    public static <T> void notNull(T t, IErrorCode ec) {
        notNull(t, ec.code(), ec.desc());
    }

    public static <T> void notNull(T t, String msg) {
        notNull(t, ResultEnum.PARAM_ERROR.code(), msg);
    }

    public static <T> void notNull(T t, String code, String msg) {
        boolean expression = t == null;
        if (!expression) {
            if (t instanceof Collection) {
                expression = ((Collection<?>) t).isEmpty();
            } else if (t instanceof Map) {
                expression = ((Map<?, ?>) t).isEmpty();
            } else if (t.getClass().isArray()) {
                expression = ((T[]) t).length == 0;
            }
        }
        notThrow(expression, () -> new ParameterException(code, msg));
    }

    public static <T extends CharSequence> void notBlank(T t) {
        notBlank(t, ResultEnum.PARAM_ERROR);
    }

    public static <T extends CharSequence> void notBlank(T t, IErrorCode ec) {
        notBlank(t, ec.code(), ec.desc());
    }

    public static <T extends CharSequence> void notBlank(T t, String msg) {
        notBlank(t, ResultEnum.PARAM_ERROR.code(), msg);
    }

    public static <T extends CharSequence> void notBlank(T t, String code, String msg) {
        notThrow(StringUtil.isBlank(t), () -> new ParameterException(code, msg));
    }

    public static <A, B> void equals(A a, B b) {
        equals(a, b, ResultEnum.REQUEST_ERROR);
    }

    public static <A, B> void equals(A a, B b, IErrorCode ec) {
        equals(a, b, ec.code(), ec.desc());
    }

    public static <A, B> void equals(A a, B b, String msg) {
        equals(a, b, ResultEnum.REQUEST_ERROR.code(), msg);
    }

    public static <A, B> void equals(A a, B b, String code, String msg) {
        notThrow(!Objects.equals(a, b), () -> new BusinessException(code, msg));
    }

    public static void state(boolean expression) {
        state(expression, ResultEnum.STATE_ERROR);
    }

    public static void state(boolean expression, IErrorCode ec) {
        state(expression, ec.code(), ec.desc());
    }

    public static void state(boolean expression, String msg) {
        state(expression, ResultEnum.STATE_ERROR.code(), msg);
    }

    public static void state(boolean expression, String code, String msg) {
        state(expression, () -> new BusinessException(code, msg));
    }

    public static <T extends Throwable> void state(boolean expression, Supplier<T> supplier) throws T {
        if (!expression) throw supplier.get();
    }

    public static <T extends Throwable> void notThrow(boolean expression, Supplier<T> supplier) throws T {
        if (expression) throw supplier.get();
    }

}
