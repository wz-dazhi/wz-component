package com.wz.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc
 * @className: WebMvcTest
 * @description:
 * @author: zhi
 * @date: 2021/10/25
 * @version: 1.0
 */
@SpringBootApplication
public class WebMvcApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext c = SpringApplication.run(WebMvcApp.class, args);
    }

}
