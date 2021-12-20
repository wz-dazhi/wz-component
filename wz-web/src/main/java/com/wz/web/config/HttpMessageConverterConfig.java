package com.wz.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.web.config
 * @className: HttpMessageConverterConfig
 * @description:
 * @author: Zhi
 * @date: 2019-12-04 14:34
 * @version: 1.0
 */
@Configuration
public class HttpMessageConverterConfig {
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return stringHttpMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper) {
            /**
             * 用于加解密去除双引号
             */
            @Override
            protected void writeInternal(Object o, Type t, HttpOutputMessage m) throws IOException, HttpMessageNotWritableException {
                if (o instanceof String) {
                    Charset charset = this.getDefaultCharset() == null ? StandardCharsets.UTF_8 : this.getDefaultCharset();
                    StreamUtils.copy((String) o, charset, m.getBody());
                } else {
                    m.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    super.writeInternal(o, t, m);
                }
            }
        };
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return mappingJackson2HttpMessageConverter;
    }
}
