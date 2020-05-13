package com.wz.rabbitmq.bean;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.bean
 * @className: QueueProperties
 * @description:
 * @author: Zhi
 * @date: 2020-05-06 14:52
 * @version: 1.0
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "rabbit.queue")
public class QueueProperties {
    /**
     * 队列列表
     */
    private List<RabbitQueueInfo> queues = new ArrayList<>();

    @Data
    @ConfigurationProperties(prefix = "rabbit.queue.queues")
    public static class RabbitQueueInfo {

        @NestedConfigurationProperty
        private QueueInfo queueInfo;

        @NestedConfigurationProperty
        private ExchangeInfo exchangeInfo;
    }

}
