package com.wz.swagger;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;

/**
 * @package: com.wz.swagger
 * @className: SwaggerConfiguration
 * @description:
 * @author: zhi
 * @date: 2020/12/31 下午6:27
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(name = "api.swagger.enabled", matchIfMissing = true)
@Import({
        OpenApiDocumentationConfiguration.class,
        Knife4jAutoConfiguration.class
})
public class SwaggerConfiguration {
}
