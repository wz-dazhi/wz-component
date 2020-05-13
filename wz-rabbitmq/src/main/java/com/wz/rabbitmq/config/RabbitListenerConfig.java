package com.wz.rabbitmq.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @projectName: wz-component
 * @package: com.wz.rabbitmq.config
 * @className: RabbitListenerConfig
 * @description: 该配置用来在 @RabbitListener 注解上方法可以直接使用对象进行消费
 * @author: Zhi
 * @date: 2020-05-13 15:29
 * @version: 1.0
 */
@Configuration
@AllArgsConstructor
public class RabbitListenerConfig implements RabbitListenerConfigurer {
    private final MessageConverter messageConverter;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(messageConverter);
        return messageHandlerMethodFactory;
    }

}
