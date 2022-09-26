package com.wz.swagger.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;

/**
 * @package: com.wz.swagger
 * @className: SwaggerConfig
 * @description: swagger与JRebel冲突, 使用JRebel会出现泛型不显示的问题
 * @author: zhi
 * @date: 2020/12/31 下午6:27
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true")
@Import({
        OpenApiDocumentationConfiguration.class,
        Knife4jAutoConfiguration.class
})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    @Bean
    public SwaggerBeanDefinitionRegistryPostProcessor swaggerBeanDefinitionRegistryPostProcessor() {
        return new SwaggerBeanDefinitionRegistryPostProcessor();
    }

}
