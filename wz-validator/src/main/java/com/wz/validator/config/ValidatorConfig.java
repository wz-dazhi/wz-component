package com.wz.validator.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wz.common.constant.Consts;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;

/**
 * @projectName: rnjf-product-api
 * @package: com.rnjf.bj.overseas.configuration.i18n
 * @className: ValidatorConfig
 * @description:
 * @author: Zhi
 * @date: 2019-09-27 16:57
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "validator.config")
public class ValidatorConfig {

    /**
     * 国际化文件path, 多个文件以 "," 逗号隔开
     */
    private String baseNames = "i18n/validations/validations";

    @Bean
    public MessageSource messageSource() {
        if (StringUtils.isBlank(baseNames)) {
            throw new RuntimeException("请设置[validator.config.base-names]国际化文件路径");
        }
        Iterable<String> iterable = Splitter.on(Consts.COMMA).omitEmptyStrings().trimResults().split(baseNames);
        List<String> paths = Lists.newArrayList(iterable);
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames(paths.toArray(new String[0]));
        return messageSource;
    }

}
