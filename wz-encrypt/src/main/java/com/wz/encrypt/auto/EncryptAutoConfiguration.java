package com.wz.encrypt.auto;

import com.wz.encrypt.algorithm.DefaultEncryptAlgorithm;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableAutoConfiguration
@EnableConfigurationProperties({EncryptProperties.class})
public class EncryptAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EncryptAlgorithm encryptAlgorithm() {
        return new DefaultEncryptAlgorithm();
    }

}
