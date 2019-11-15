package com.wz.validator.config;

import com.wz.web.config.WebMvcConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @projectName: wz-component
 * @package: com.wz.validator.config
 * @className: ValidatorWebMvcConfig
 * @description: validator web mvc 配置.
 * @author: Zhi
 * @date: 2019-11-15 18:52
 * @version: 1.0
 */
@Configuration
@AllArgsConstructor
public class ValidatorWebMvcConfig extends WebMvcConfig {
    private final MessageSource messageSource;

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}
