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
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 队列最大长度
     */
    private int queueCapacity;

    /**
     * 线程池维护线程所允许的空闲时间，默认为60s
     */
    private int keepAliveSeconds;
}
