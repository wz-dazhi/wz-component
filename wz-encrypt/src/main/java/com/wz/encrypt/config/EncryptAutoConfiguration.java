package com.wz.encrypt.config;

import com.wz.encrypt.algorithm.DefaultEncryptAlgorithm;
import com.wz.encrypt.algorithm.DefaultSignAlgorithm;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.algorithm.SignAlgorithm;
import com.wz.encrypt.interceptor.SignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.auto
 * @className: EncryptAutoConfiguration
 * @description: 自动装配
 * @author: Zhi Wang
 * @date: 2019/3/5 12:08 PM
 * @version: 1.0
 **/
@Configuration
@ConditionalOnProperty(value = "api.encrypt.enable", havingValue = "true")
@EnableConfigurationProperties({EncryptProperties.class})
public class EncryptAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EncryptAlgorithm encryptAlgorithm() {
        return new DefaultEncryptAlgorithm();
    }

    @Bean
    @ConditionalOnMissingBean
    public SignAlgorithm signAlgorithm() {
        return new DefaultSignAlgorithm();
    }

    @Bean
    public SignInterceptor signInterceptor(EncryptProperties encryptProperties, EncryptAlgorithm encryptAlgorithm, SignAlgorithm signAlgorithm) {
        return new SignInterceptor(signAlgorithm, encryptAlgorithm, encryptProperties);
    }

    @Bean
    public EncryptWebMvcConfig encryptWebMvcConfig(EncryptProperties encryptProperties, SignInterceptor signInterceptor) {
        return new EncryptWebMvcConfig(encryptProperties, signInterceptor);
    }

}
