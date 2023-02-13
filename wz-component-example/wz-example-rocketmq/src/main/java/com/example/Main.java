package com.example;

import com.example.consumer.OOXXTransactionListener;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@AllArgsConstructor
public class Main {
    private final RocketMQTemplate rocketMQTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext c = SpringApplication.run(Main.class, args);
        Main m = c.getBean(Main.class);
        m.sendTransaction();
    }

    private void sendTransaction() {
        for (int i = 0; i < 10; i++) {
            // 使用arg模拟本地事物
            int arg = i % 3;
            Message<String> msg = MessageBuilder.withPayload("hello_world_" + i)
//                    .setHeader("header1", "value1")
//                    .setHeader("header2", "value2")
                    .build();
            TransactionSendResult r = rocketMQTemplate
                    .sendMessageInTransaction(OOXXTransactionListener.OOXX_TRANSACTION_PRODUCER, "ooxx2:Tag1", msg, arg);
            System.out.println(r);
        }
    }

}