package com.wz.encrypt.config;

import com.wz.encrypt.auto.EncryptProperties;
import com.wz.encrypt.interceptor.SignInterceptor;
import com.wz.webmvc.config.WebMvcConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @projectName: wz-component
 * @package: com.wz.encrypt.config
 * @className: EncryptWebMvcConfig
 * @description:
 * @author: Zhi
 * @date: 2019-12-04 23:28
 * @version: 1.0
 */
@Configuration
@AllArgsConstructor
public class EncryptWebMvcConfig extends WebMvcConfig {
    private final EncryptProperties encryptProperties;
    private final SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor).addPathPatterns(encryptProperties.getSignPath());
    }

    @Override
    public Validator getValidator() {
        return null;
    }
}
