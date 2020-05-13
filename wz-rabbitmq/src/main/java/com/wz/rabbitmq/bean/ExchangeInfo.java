package com.wz.rabbitmq.bean;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.bean
 * @className: ExchangeInfo
 * @description:
 * @author: Zhi
 * @date: 2020-05-11 11:49
 * @version: 1.0
 */
@Data
public class ExchangeInfo {
    /**
     * 交换机名称
     */
    private String name;

    /**
     * 交换机类型
     *
     * @see org.springframework.amqp.core.ExchangeTypes
     */
    private String type;

    /**
     * 持久化
     */
    private boolean durable = true;

    /**
     * 自动删除
     */
    private boolean autoDelete;
    /**
     * 是否内置的，如果为true，只能通过Exchange到Exchange
     */
    private boolean internal;

    /**
     * 延时
     */
    private boolean delayed;

    /**
     * 忽略声明异常
     */
    private boolean ignoreDeclarationExceptions;

    private boolean declare = true;

    private Object[] declaringAdmins;

    @NestedConfigurationProperty
    private ExchangeArgument exchangeArgument;

    @Data
    public static class ExchangeArgument {
        private String alternateExchange;

        private Map<String, Object> otherArguments;
    }
}
