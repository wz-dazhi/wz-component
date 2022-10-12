package com.wz.webmvc.config;

import com.wz.swagger.config.SwaggerProperties;
import com.wz.webmvc.filter.FilterHelper;
import com.wz.webmvc.filter.RequestBodyFilter;
import com.wz.webmvc.filter.SwaggerFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @projectName: wz-component
 * @package: com.wz.web.config
 * @className: FilterConfig
 * @description:
 * @author: zhi
 * @date: 2021/1/10 下午4:36
 * @version: 1.0
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestBodyFilter> requestBodyFilterRegistrationBean() {
        return FilterHelper.filterRegistrationBean(new RequestBodyFilter(), Ordered.HIGHEST_PRECEDENCE);
    }

    @ConditionalOnProperty(name = SwaggerProperties.KNIFE4J_ENABLE_NAME, havingValue = "false", matchIfMissing = true)
    @Bean
    public FilterRegistrationBean<SwaggerFilter> swaggerFilterRegistrationBean() {
        return FilterHelper.filterRegistrationBean(new SwaggerFilter(), 0);
    }

}
