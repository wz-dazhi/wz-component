package com.wz.encrypt.advice;

import com.google.common.base.Stopwatch;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.annotation.Decrypt;
import com.wz.encrypt.auto.EncryptProperties;
import com.wz.encrypt.constant.EncryptConsts;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public boolean supports(MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.nonNull(parameter.getMethodAnnotation(Decrypt.class)) && !properties.isDebug();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            return new ApiHttpInputMessage(inputMessage, algorithm, properties.getKey(), EncryptConsts.CHARSET_UTF_8);
        } catch (Exception e) {
            log.error("Request Body decrypt fail. msg: {}, e: ", e.getMessage(), e);
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private static class ApiHttpInputMessage implements HttpInputMessage {
        private InputStream body;
        private HttpHeaders headers;

        ApiHttpInputMessage(HttpInputMessage message, EncryptAlgorithm algorithm, String key, String charset) throws Exception {
            this.headers = message.getHeaders();
            this.decrypt(message, algorithm, key, charset);
        }

        private void decrypt(HttpInputMessage message, EncryptAlgorithm algorithm, String key, String charset) throws Exception {
            log.debug("<<< Starting decrypt data.");
            Stopwatch sw = Stopwatch.createStarted();
            String content = this.getContent(message.getBody(), charset);
            String decryptBody = algorithm.decrypt(content, key);
            this.body = this.toBody(decryptBody, charset);
            log.debug("<<< Decrypt Time: {} ms.", sw.stop().elapsed(TimeUnit.MILLISECONDS));
        }

        private String getContent(InputStream inputStream, String charset) throws Exception {
            @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            return os.toString(charset);
        }

        private InputStream toBody(String input, String encoding) throws IOException {
            byte[] bytes = encoding != null ? input.getBytes(encoding) : input.getBytes();
            return new ByteArrayInputStream(bytes);
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
