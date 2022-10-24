package com.wz.web.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @projectName: wz-component
 * @package: com.wz.web.concurrent
 * @className: AsyncExceptionHandler
 * @description:
 * @author: zhi
 * @date: 2022/10/14
 * @version: 1.0
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    public static final AsyncExceptionHandler ASYNC_EXCEPTION_HANDLER = new AsyncExceptionHandler();

    @Override
    public void handleUncaughtException(Throwable t, Method m, Object... params) {
        log.error("Async线程池发生异常. 具体方法: {}.{}, params: {} , t: \n", m.getDeclaringClass().getTypeName(), m.getName(), Arrays.toString(params), t);
    }

}
