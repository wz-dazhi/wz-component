package com.wz.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.common.util.JsonUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: wz-component
 * @package: com.wz.common.config
 * @className: JsonConfig
 * @description:
 * @author: Zhi
 * @date: 2019-11-05 18:51
 * @version: 1.0
 */
@Configuration
public class JsonConfig {

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     */
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return JsonUtil.getMapper();
    }
}
