package com.wz.encrypt.advice;

import com.google.common.base.Stopwatch;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.StringUtil;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.annotation.Encrypt;
import com.wz.encrypt.config.EncryptProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.advice
 * @className: EncryptResponseBodyAdvice
 * @description: 加密数据
 * <p>
 * 接口返回数据进行加密
 * </p>
 * @author: Zhi Wang
 * @date: 2019/3/5 5:35 PM
 * @version: 1.0
 **/
@Slf4j
@ConditionalOnBean({EncryptProperties.class, EncryptAlgorithm.class})
@RestControllerAdvice(annotations = RestController.class)
@AllArgsConstructor
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final EncryptProperties properties;
    private final EncryptAlgorithm algorithm;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !properties.isDebug() && Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(Encrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        try {
            return this.encryptData(body);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return body;
        }
    }

    private Object encryptData(Object body) throws Exception {
        Stopwatch sw = Stopwatch.createStarted();
        String content = JsonUtil.toJson(body);
        log.debug("Starting encrypt date: {}", content);
        String key = properties.getKey();
        StringUtil.requireNonNull(key, "请配置spring.encrypt.key");
        String result = algorithm.encrypt(content, key);
        log.debug("Encrypt Time: {} ms", sw.stop().elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

}
