package com.wz.encrypt.annotation;

import com.wz.encrypt.auto.EncryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.annotation
 * @className: EnableEncrypt
 * @description: 开启加解密
 * <pre class="code">
 * @SpringBootApplication
 * @EnableEncrypt
 * public class App {
 *
 *      public static void main(String[] args) {
 *          SpringApplication.run(App.class, args);
 *      }
 * }
 * </pre>
 * @author: Zhi Wang
 * @date: 2019/3/5 5:06 PM
 * @version: 1.0
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({EncryptAutoConfiguration.class})
public @interface EnableEncrypt {
}
