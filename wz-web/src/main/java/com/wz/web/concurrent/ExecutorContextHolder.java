package com.wz.web.concurrent;

import org.springframework.core.NamedInheritableThreadLocal;

/**
 * @projectName: wz-component
 * @package: com.wz.web.concurrent
 * @className: ExecutorContextHolder
 * @description:
 * @author: zhi
 * @date: 2022/10/14
 * @version: 1.0
 */
public class ExecutorContextHolder {

    private static final ThreadLocal<Object> EXECUTOR_HOLDER = new NamedInheritableThreadLocal<>("Executor Holder");

    public static void reset() {
        EXECUTOR_HOLDER.remove();
    }

    public static <T> void set(T t) {
        EXECUTOR_HOLDER.set(t);
    }

    public static <T> T get() {
        return (T) EXECUTOR_HOLDER.get();
    }

}
