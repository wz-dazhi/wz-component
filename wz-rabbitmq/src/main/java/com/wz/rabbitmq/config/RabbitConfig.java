package com.wz.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.common.util.StringUtil;
import com.wz.rabbitmq.bean.ExchangeInfo;
import com.wz.rabbitmq.bean.QueueInfo;
import com.wz.rabbitmq.bean.QueueProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.config
 * @className: RabbitConfig
 * @description:
 * @author: Zhi
 * @date: 2020-04-15 18:57
 * @version: 1.0
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({QueueProperties.RabbitQueueInfo.class})
public class RabbitConfig {
    private final QueueProperties queueProperties;
    private final AmqpAdmin amqpAdmin;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        List<QueueProperties.RabbitQueueInfo> queues = queueProperties.getQueues();
        log.info("Starting init rabbit message queue. createQueue size: {}", queues.size());
        queues.forEach(q -> {
            Queue queue = createQueue(q.getQueueInfo());
            amqpAdmin.declareQueue(queue);
            Exchange exchange = createExchange(q.getExchangeInfo());
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(this.binding(queue, exchange, q.getQueueInfo().getRoutingKey()));
        });
        log.info("Started init rabbit message queue.");
    }

    private Binding binding(Queue queue, Exchange exchange, String routingKey) {
        if (exchange instanceof FanoutExchange) {
            return BindingBuilder.bind(queue).to((FanoutExchange) exchange);
        }
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    private Queue createQueue(QueueInfo q) {
        String name = q.getName();
        StringUtil.requireNonNull(name, "Rabbit queue name is null");
        QueueBuilder builder = q.isDurable() ? QueueBuilder.durable(name) : QueueBuilder.nonDurable(name);
        if (q.isExclusive()) {
            builder.exclusive();
        }
        if (q.isAutoDelete()) {
            builder.autoDelete();
        }
        QueueInfo.QueueArgument arg = q.getQueueArgument();
        if (arg != null) {
            builder = arg.getXMessageTtl() > 0 ? builder.ttl(arg.getXMessageTtl()) : builder;
            builder = arg.getXExpires() > 0 ? builder.expires(arg.getXExpires()) : builder;
            builder = arg.getXMaxLength() > 0 ? builder.maxLength(arg.getXMaxLength()) : builder;
            builder = arg.getXMaxLengthBytes() > 0 ? builder.maxLengthBytes(arg.getXMaxLengthBytes()) : builder;
            builder = arg.getXOverflow() != null ? builder.overflow(arg.getXOverflow()) : builder;
            builder = StringUtil.isNotBlank(arg.getXDeadLetterExchange()) ? builder.deadLetterExchange(arg.getXDeadLetterExchange()) : builder;
            builder = StringUtil.isNotBlank(arg.getXDeadLetterRoutingKey()) ? builder.deadLetterRoutingKey(arg.getXDeadLetterRoutingKey()) : builder;
            builder = arg.getXMaxPriority() > 0 ? builder.maxPriority(arg.getXMaxPriority()) : builder;
            builder = arg.getXQueueMasterLocator() != null ? builder.masterLocator(arg.getXQueueMasterLocator()) : builder;
            builder = arg.getXDeliveryLimit() > 0 ? builder.deliveryLimit(arg.getXDeliveryLimit()) : builder;
            builder = "lazy".equals(arg.getXQueueMode()) ? builder.lazy() : builder;
            builder = arg.isXSingleActiveConsumer() ? builder.singleActiveConsumer() : builder;
            builder = "quorum".equalsIgnoreCase(arg.getXQueueType()) ? builder.quorum() : builder;
            // other arguments
            if (arg.getOtherArguments() != null && !arg.getOtherArguments().isEmpty()) {
                builder.withArguments(arg.getOtherArguments());
            }
        }

        return builder.build();
    }

    private Exchange createExchange(ExchangeInfo e) {
        String name = e.getName();
        String type = e.getType();
        StringUtil.requireNonNull(name, "Exchange name is null.");
        StringUtil.requireNonNull(type, "Exchange type is null.");
        ExchangeBuilder builder = new ExchangeBuilder(name, type);
        if (e.isAutoDelete()) {
            builder.autoDelete();
        }
        if (e.isInternal()) {
            builder.internal();
        }
        if (e.isDelayed()) {
            builder.delayed();
        }
        if (e.isIgnoreDeclarationExceptions()) {
            builder.ignoreDeclarationExceptions();
        }
        if (e.isDeclare()) {
            builder.suppressDeclaration();
        }
        builder.durable(e.isDurable());
        if (null != e.getDeclaringAdmins()) {
            builder.admins(e.getDeclaringAdmins());
        }
        ExchangeInfo.ExchangeArgument arg = e.getExchangeArgument();
        if (arg != null) {
            builder = StringUtil.isNotBlank(arg.getAlternateExchange()) ? builder.alternate(arg.getAlternateExchange()) : builder;
            // other arguments
            if (arg.getOtherArguments() != null && !arg.getOtherArguments().isEmpty()) {
                builder.withArguments(arg.getOtherArguments());
            }
        }

        return builder.build();
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    @Bean
    @Resource
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(messageConverter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        // 消息返回, yml需要配置 publisher-returns: true
        // 消息最终没有进入队列时回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.error("消息最终没有进入队列. message: {}, replyCode: {}, replyText: {}, exchange: {}, routingKey: {}", message, replyCode, replyText, exchange, routingKey));
        // 消息确认, yml需要配置 publisher-confirms: true or publisher-confirm-type: correlated
        // 消息保存成功后回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息确认. correlationData: {}, ack: {}, cause: {}", correlationData, ack, cause));
        return rabbitMessagingTemplate;
    }

}
