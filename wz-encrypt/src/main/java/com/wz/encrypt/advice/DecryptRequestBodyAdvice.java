package com.wz.encrypt.advice;

import com.google.common.base.Stopwatch;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.annotation.Decrypt;
import com.wz.encrypt.auto.EncryptProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.advice
 * @className: DecryptRequestBodyAdvice
 * @description: 解密数据
 * <p>
 * 只能处理POST请求, 并且方法加入了@RequestBody注解.
 * </p>
 * @author: Zhi Wang
 * @date: 2019/3/5 5:33 PM
 * @version: 1.0
 **/
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@AllArgsConstructor
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
    private final EncryptProperties properties;
    private final EncryptAlgorithm algorithm;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            if (Objects.requireNonNull(parameter.getMethod()).isAnnotationPresent(Decrypt.class) && !properties.isDebug()) {
                return new ApiHttpInputMessage(inputMessage, algorithm, properties.getKey(), properties.getCharset());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return inputMessage;
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private class ApiHttpInputMessage implements HttpInputMessage {
        private InputStream body;
        private HttpHeaders headers;

        ApiHttpInputMessage(HttpInputMessage message, EncryptAlgorithm algorithm, String key, String charset) throws Exception {
            this.headers = message.getHeaders();
            this.decrypt(message, algorithm, key, charset);
        }

        private void decrypt(HttpInputMessage message, EncryptAlgorithm algorithm, String key, String charset) throws Exception {
            log.debug("<<< Starting decrypt data.");
            Stopwatch sw = Stopwatch.createStarted();
            String content = IOUtils.toString(message.getBody(), charset);
            String decryptBody = algorithm.decrypt(content, key);
            this.body = IOUtils.toInputStream(decryptBody, charset);
            log.debug("<<< Decrypt Time: {} ms.", sw.stop().elapsed(TimeUnit.MILLISECONDS));
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
