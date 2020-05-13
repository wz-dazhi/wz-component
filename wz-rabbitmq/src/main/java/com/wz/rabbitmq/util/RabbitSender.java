package com.wz.rabbitmq.util;

import com.wz.common.util.StringUtil;
import com.wz.common.util.UniqueNoGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.util
 * @className: RabbitUtil
 * @description:
 * @author: Zhi
 * @date: 2020-05-12 10:00
 * @version: 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class RabbitSender {
    private static final String PREFIX = "R-";
    private final RabbitMessagingTemplate rabbitMessagingTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, String msg) {
        log.info("exchange: {}, routingKey: {}, msg: {}", exchange, routingKey, msg);
        rabbitMessagingTemplate.convertAndSend(exchange, routingKey, msg);
    }

    public <T> void send(String exchange, String routingKey, T t) {
        log.info("exchange: {}, routingKey: {}, msg: {}", exchange, routingKey, t);
        rabbitMessagingTemplate.convertAndSend(exchange, routingKey, t);
    }

    /**
     * @param headers       不可变的Map
     * @param postProcessor 消息发送前进行处理
     */
    public <T> void send(String exchange, String routingKey, T t, Map<String, Object> headers, MessagePostProcessor postProcessor) {
        log.info("exchange: {}, routingKey: {}, msg: {}, headers: {}, postProcessor: {}", exchange, routingKey, t, headers, postProcessor);
        rabbitMessagingTemplate.convertAndSend(exchange, routingKey, t, headers, postProcessor);
    }

    public <T> void send(String dataId, String exchange, String routingKey, T t) {
        // 判断dataId. 如果为空, 自动生成一个并加前缀R
        dataId = StringUtil.isBlank(dataId) ? PREFIX + UniqueNoGenerator.getDefaultInstance().nextIdStr() : dataId;
        log.info("dataId: {}, exchange: {}, routingKey: {}, msg: {}", dataId, exchange, routingKey, t);
        rabbitTemplate.convertAndSend(exchange, routingKey, t, new CorrelationData(dataId));
    }

    public <T> void send(String dataId, String exchange, String routingKey, T t, org.springframework.amqp.core.MessagePostProcessor messagePostProcessor) {
        // 判断dataId. 如果为空, 自动生成一个并加前缀R
        dataId = StringUtil.isBlank(dataId) ? PREFIX + UniqueNoGenerator.getDefaultInstance().nextIdStr() : dataId;
        log.info("dataId: {}, exchange: {}, routingKey: {}, msg: {}, messagePostProcessor: {}", dataId, exchange, routingKey, t, messagePostProcessor);
        rabbitTemplate.convertAndSend(exchange, routingKey, t, messagePostProcessor, new CorrelationData(dataId));
    }

    public <T> void send(String exchange, String routingKey, T t, org.springframework.amqp.core.MessagePostProcessor messagePostProcessor, CorrelationData correlationData) {
        log.info("exchange: {}, routingKey: {}, msg: {}, messagePostProcessor: {}, correlationData: {}", exchange, routingKey, t, messagePostProcessor, correlationData);
        rabbitTemplate.convertAndSend(exchange, routingKey, t, messagePostProcessor, correlationData);
    }

}
