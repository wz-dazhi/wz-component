package com.wz.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: wz-component
 * @package: com.wz.web.config
 * @className: ThreadPoolProperties
 * @description:
 * @author: Zhi
 * @date: 2019-11-05 19:44
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 最大线程数
     */
    private int maxPoolSize = corePoolSize * 2;

    /**
     * 队列最大长度
     */
    private int queueCapacity = 100;

    /**
     * 线程池维护线程所允许的空闲时间，默认为60s
     */
    private int keepAliveSecond = 60;

    /**
     * 线程名称前缀: 默认 spring.application.name
     */
    private String threadNamePrefix;

    /**
     * 线程优先级, 默认5
     */
    private int threadPriority = Thread.NORM_PRIORITY;

    /**
     * 是否为守护线程, 默认false
     */
    private boolean daemon = false;

    /**
     * 线程组名字
     */
    private String threadGroupName;
}
