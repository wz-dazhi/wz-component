package com.wz.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: wz-web
 * @package: com.wz.web.config
 * @className: WebMvcConfig
 * @description: web mvc config
 * @author: Zhi Wang
 * @date: 2019/3/9 11:16 PM
 * @version: 1.0
 **/
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private MessageSource messageSource;
    @Resource
    private Converter dateConverter;
    @Resource
    private Converter localTimeConverter;
    @Resource
    private Converter localDateConverter;
    @Resource
    private Converter localDateTimeConverter;
    @Resource
    private HttpMessageConverter stringHttpMessageConverter;
    @Resource
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter);
        converters.add(mappingJackson2HttpMessageConverter);
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConverter);
        registry.addConverter(localTimeConverter);
        registry.addConverter(localDateConverter);
        registry.addConverter(localDateTimeConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
