package com.wz.swagger.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;

/**
 * @package: com.wz.swagger
 * @className: SwaggerConfiguration
 * @description:
 * @author: zhi
 * @date: 2020/12/31 下午6:27
 * @version: 1.0
 */
@Component
@ConditionalOnProperty(name = "knife4j.enable", matchIfMissing = true)
@Import({
        OpenApiDocumentationConfiguration.class,
        Knife4jAutoConfiguration.class
})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

    @Bean
    public SwaggerBeanDefinitionRegistryPostProcessor swaggerBeanDefinitionRegistryPostProcessor() {
        return new SwaggerBeanDefinitionRegistryPostProcessor();
    }

}
