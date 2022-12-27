package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @projectName: wz-example
 * @package: com.example
 * @className: App
 * @description:
 * @author: zhi
 * @date: 2022/8/4
 * @version: 1.0
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext c = SpringApplication.run(App.class, args);
         DataSource ds = c.getBean(DataSource.class);
         System.out.println(ds.getConnection());
    }
}
