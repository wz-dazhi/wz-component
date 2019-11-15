package com.wz.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
@Async
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ExecutorConfig {

    /**
     * 自定义spring线程池
     *
     * @return
     */
    @Bean("web-executor")
    @ConditionalOnMissingBean
    public Executor executor(ThreadPoolProperties prop) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(prop.getCorePoolSize());
        executor.setMaxPoolSize(prop.getMaxPoolSize());
        executor.setQueueCapacity(prop.getQueueCapacity());
        executor.setKeepAliveSeconds(prop.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
