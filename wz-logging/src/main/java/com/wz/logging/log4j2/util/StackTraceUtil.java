package com.wz.logging.log4j2.util;

import java.util.Objects;

/**
 * @projectName: wz-component
 * @package: com.wz.logging.util
 * @className: StackTraceUtil
 * @description: TODO 存在性能问题
 * @author: zhi
 * @date: 2020/12/29 下午2:13
 * @version: 1.0
 */
public final class StackTraceUtil {
    private static final String DEFAULT_LOGGING_EVENT_BUILDER_CLASS_NAME = "org.slf4j.spi.DefaultLoggingEventBuilder";

    private StackTraceUtil() {
    }

    private static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

        // 循环遍历到日志类标识
        boolean isEachLogClass = false;
        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement s : callStack) {
            final String className = s.getClassName();
            // 遍历到日志类
            if (DEFAULT_LOGGING_EVENT_BUILDER_CLASS_NAME.equals(className)) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogClass) {
                if (!DEFAULT_LOGGING_EVENT_BUILDER_CLASS_NAME.equals(className)) {
                    return s;
                }
            }
        }
        return null;
    }

    public static StackTraceElement findCaller(StackTraceElement element) {
        if (Objects.nonNull(element)) {
            // 判断栈信息, 查找log调用类
            if (!DEFAULT_LOGGING_EVENT_BUILDER_CLASS_NAME.equals(element.getClassName())) {
                return element;
            }

            element = StackTraceUtil.findCaller();
            return element;
        }
        return null;
    }

}
