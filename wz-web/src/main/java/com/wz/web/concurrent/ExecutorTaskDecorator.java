package com.wz.web.concurrent;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Optional;

/**
 * @projectName: wz-component
 * @package: com.wz.web.concurrent
 * @className: ExecutorTaskDecorator
 * @description:
 * @author: zhi
 * @date: 2022/10/14
 * @version: 1.0
 */
public class ExecutorTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable r) {
        // copy MDC context
        Map<String, String> mdcContext = MDC.getCopyOfContextMap();
        // copy Request context
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        // copy executor context
        Optional<Object> executorOptional = Optional.ofNullable(ExecutorContextHolder.get());
        return () -> {
            try {
                MDC.setContextMap(mdcContext);
                RequestContextHolder.setRequestAttributes(requestContext);
                executorOptional.ifPresent(ExecutorContextHolder::set);
                r.run();
            } finally {
                MDC.clear();
                RequestContextHolder.resetRequestAttributes();
                executorOptional.ifPresent(o -> ExecutorContextHolder.reset());
            }
        };
    }

}
