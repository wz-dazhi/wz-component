package com.example.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @projectName: wz-component
 * @package: com.example.consumer
 * @className: OOXXListener
 * @description:
 * @author: zhi
 * @date: 2023/2/13
 * @version: 1.0
 */
@Component
@RocketMQMessageListener(consumerGroup = "test2", topic = "ooxx2")
public class OOXXListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("OOXXListener onMessage: " + msg);
    }

}
