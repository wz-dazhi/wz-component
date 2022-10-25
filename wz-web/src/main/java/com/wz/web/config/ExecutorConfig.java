package com.wz.web.config;

import com.wz.common.util.StringUtil;
import com.wz.web.concurrent.AsyncExceptionHandler;
import com.wz.web.concurrent.ExecutorTaskDecorator;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

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
public class ExecutorConfig implements AsyncConfigurer {

    @Value("#{ @environment['spring.application.name'] ?: 'app' }")
    protected String applicationName;

    protected final AtomicInteger threadCount = new AtomicInteger(0);

    @Resource
    protected ThreadPoolProperties threadPoolProperties;

    @Autowired(required = false)
    private AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler;

    @Autowired(required = false)
    private RejectedExecutionHandler rejectedExecutionHandler;

    @Override
    public final Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(this::createThread);
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSecond());
        executor.setRejectedExecutionHandler(rejectedExecutionHandler == null ? new ThreadPoolExecutor.CallerRunsPolicy() : rejectedExecutionHandler);
        executor.setTaskDecorator(new ExecutorTaskDecorator());
        initBefore(executor);
        executor.initialize();
        initAfter(executor);
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncUncaughtExceptionHandler == null ? AsyncExceptionHandler.ASYNC_EXCEPTION_HANDLER : asyncUncaughtExceptionHandler;
    }

    protected Thread createThread(Runnable r) {
        Thread t = new Thread(getThreadGroup(), r, nextThreadName());
        t.setPriority(threadPoolProperties.getThreadPriority());
        t.setDaemon(threadPoolProperties.isDaemon());
        return t;
    }

    protected String nextThreadName() {
        String threadNamePrefix = StringUtil.isBlank(threadPoolProperties.getThreadNamePrefix()) ? applicationName : threadPoolProperties.getThreadNamePrefix();
        return threadNamePrefix + "-" + threadCount.incrementAndGet();
    }

    protected ThreadGroup getThreadGroup() {
        return StringUtil.isBlank(threadPoolProperties.getThreadGroupName()) ? null : new ThreadGroup(threadPoolProperties.getThreadGroupName());
    }

    protected void initBefore(ThreadPoolTaskExecutor executor) {
    }

    protected void initAfter(ThreadPoolTaskExecutor executor) {
    }

}
