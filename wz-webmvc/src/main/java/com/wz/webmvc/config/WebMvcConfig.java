package com.wz.webmvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @projectName: wz-webmvc
 * @package: com.wz.webmvc.config
 * @className: WebMvcConfig
 * @description: web mvc config
 * @author: Zhi Wang
 * @date: 2019/3/9 11:16 PM
 * @version: 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Resource
    private Validator springValidator;

    @Override
    public Validator getValidator() {
        return springValidator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler(contextPath + "/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        registry.addResourceHandler(contextPath + "/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(contextPath + "/swagger-ui/").setViewName("forward:" + contextPath + "/swagger-ui/index.html");
    }

}
