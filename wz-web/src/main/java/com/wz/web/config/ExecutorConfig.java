package com.wz.web.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @projectName: wz
 * @package: com.wz.web.config
 * @className: ExecutorConfig
 * @description: 线程池配置
 * @author: Zhi Wang
 * @date: 2019/3/1 3:41 PM
 * @version: 1.0
 **/
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ExecutorConfig {

    @Value("#{ @environment['spring.application.name'] ?: 'app' }")
    private String applicationName;

    /**
     * 自定义spring线程池
     */
    @Bean
    public Executor executor(ThreadPoolProperties prop) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(applicationName + "-");
        executor.setCorePoolSize(prop.getCorePoolSize());
        executor.setMaxPoolSize(prop.getMaxPoolSize());
        executor.setQueueCapacity(prop.getQueueCapacity());
        executor.setKeepAliveSeconds(prop.getKeepAliveSecond());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setTaskDecorator(new ExecutorTaskDecorator());
        executor.initialize();
        return executor;
    }

    private static class ExecutorTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable r) {
            // copy MDC context
            Map<String, String> mdcContext = MDC.getCopyOfContextMap();
            // copy Request context
            RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
            return () -> {
                try {
                    MDC.setContextMap(mdcContext);
                    RequestContextHolder.setRequestAttributes(requestContext);
                    r.run();
                } finally {
                    MDC.clear();
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
