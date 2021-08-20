package com.wz.push.util;

import com.wz.common.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @projectName: wz-component
 * @package: com.wz.push.util
 * @className: Main
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        final ConfigurableApplicationContext c = SpringApplication.run(Main.class, args);
        System.out.println(c.getBean(SpringContextUtil.class));
    }

    @Bean
    public JavaMailSender javaMailSender() {
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setUsername("username");
        sender.setPassword("password");

        return sender;
    }

}
