package com.wz.rabbitmq.bean;

import lombok.Data;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.bean
 * @className: QueueInfo
 * @description:
 * @author: Zhi
 * @date: 2020-05-06 15:06
 * @version: 1.0
 */
@Data
public class QueueInfo {
    /**
     * 队列名字
     */
    private String name;
    /**
     * routingKey
     */
    private String routingKey;
    /**
     * 是否持久化, 默认true
     */
    private boolean durable = true;
    /**
     * 独有的,只能被当前创建的连接使用,而且当连接关闭后队列即被删除
     */
    private boolean exclusive;
    /**
     * 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除
     */
    private boolean autoDelete;

    /**
     * 队列参数
     */
    @NestedConfigurationProperty
    private QueueArgument queueArgument;

    @Data
    public static class QueueArgument {
        /**
         * 消息的生存时间，在此之后将其丢弃或路由到死信交换
         */
        private int xMessageTtl;
        /**
         * 当队列在指定的时间内没有被使用(访问)就会被删除
         */
        private int xExpires;
        /**
         * 队列可以容纳的消息的最大条数,超过这个条数,队列头部的消息将会被丢弃
         */
        private int xMaxLength;
        /**
         * 队列可以容纳的消息的最大字节数,超过这个字节数,队列头部的消息将会被丢弃
         */
        private int xMaxLengthBytes;
        /**
         * 队列中的消息溢出时,要么丢弃队列头部的消息,要么拒绝接收后面生产者发送过来的所有消息
         */
        @NestedConfigurationProperty
        private QueueBuilder.Overflow xOverflow;
        /**
         * (死信)交换机的名称,当队列中的消息的生存期到了,或者因长度限制被丢弃时,消息会被推送到(绑定到)这台交换机(的队列中),而不是直接丢掉.
         */
        private String xDeadLetterExchange;
        /**
         * 可选的替代路由使用死信消息时的routingKey。如果没有设置,将使用原始消息的routingKey。
         */
        private String xDeadLetterRoutingKey;
        /**
         * 设置该队列中的消息的优先级最大值
         */
        private int xMaxPriority;
        /**
         * 设置队列为懒人模式: lazy
         */
        private String xQueueMode;

        /**
         * 集群相关设置
         */
        @NestedConfigurationProperty
        private QueueBuilder.MasterLocator xQueueMasterLocator;

        /**
         * 设置单个消费
         */
        private boolean xSingleActiveConsumer;

        /**
         * 设置队列参数来声明一个类型为"quorum"而不是"classic"的队列。
         */
        private String xQueueType;

        /**
         * 设置传递限制
         */
        private int xDeliveryLimit;

        /**
         * 队列其他参数
         */
        private Map<String, Object> otherArguments;
    }

}
