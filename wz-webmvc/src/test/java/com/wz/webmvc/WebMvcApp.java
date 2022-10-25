package com.wz.webmvc;

import com.wz.webmvc.async.AsyncService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc
 * @className: WebMvcTest
 * @description:
 * @author: zhi
 * @date: 2021/10/25
 * @version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WebMvcApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConfigurableApplicationContext c = SpringApplication.run(WebMvcApp.class, args);
        AsyncService as = c.getBean(AsyncService.class);
        System.out.println(as.say("张三").get());
        System.out.println(as.f().get());
//        as.f();
//        as.f2();
    }

}
