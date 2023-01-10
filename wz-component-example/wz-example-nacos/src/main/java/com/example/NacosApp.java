package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz-component
 * @package: com.example
 * @className: NacosApp
 * @description:
 * @author: zhi
 * @date: 2023/1/7
 * @version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
public class NacosApp {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext c = SpringApplication.run(NacosApp.class, args);
        ConfigurableEnvironment e = c.getEnvironment();
        // 死循环动态刷新
        while (true) {
            String mysqlIp = e.getProperty("mysql.ip");
            String mysqlName = e.getProperty("mysql.name");
            System.out.println("shared-configs: shared-config-1.yaml, mysqlIp: " + mysqlIp + ", mysqlName: " + mysqlName);

            String userId = e.getProperty("user.id");
            String userName = e.getProperty("user.name");
            String userAge = e.getProperty("user.age");
            System.out.println("extension-configs: userId: " + userId + ", userName: " + userName + ", userAge: " + userAge);

            String v1 = e.getProperty("key1");
            String v2 = e.getProperty("key2");
            String v3 = e.getProperty("key3");
            System.out.println("应用配置优先级测试: key1: " + v1 + ", key2: " + v2 + ", key3: " + v3);
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
