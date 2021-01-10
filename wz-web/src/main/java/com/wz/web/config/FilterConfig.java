package com.wz.web.config;

import com.wz.web.filter.RequestBodyFilter;
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
        FilterRegistrationBean<RequestBodyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestBodyFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestBodyFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

}
