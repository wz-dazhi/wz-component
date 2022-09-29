package com.wz.webmvc.aspectj;

import com.wz.swagger.model.Result;
import com.wz.swagger.util.R;
import com.wz.webmvc.annotation.NoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.aspectj
 * @className: ResultResponseBodyAdvice
 * @description:
 * @author: zhi
 * @date: 2022/9/22
 * @version: 1.0
 */
@Slf4j
@Order(0)
@ConditionalOnProperty(prefix = "api", value = "result.advice", havingValue = "true", matchIfMissing = true)
@RestControllerAdvice(annotations = ResponseBody.class)
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 默认过滤原生 ResponseEntity
     */
    @Value("${api.result.advice-filter-classes:org.springframework.http.ResponseEntity}")
    private String adviceFilterClasses;
    private volatile Set<Class<?>> adviceFilterClassesSet;

    private boolean filterClasses(MethodParameter mp) {
        Method m = mp.getMethod();
        Class<?> returnClass;
        if (m == null || (returnClass = m.getReturnType()) == null) {
            return true;
        }

        if (adviceFilterClassesSet == null) {
            synchronized (this) {
                if (adviceFilterClassesSet == null) {
                    Set<String> classes = StringUtils.commaDelimitedListToSet(adviceFilterClasses);
                    if (classes.isEmpty()) {
                        adviceFilterClassesSet = Collections.emptySet();
                        return false;
                    }
                    adviceFilterClassesSet = new HashSet<>(classes.size());
                    // 第一次进来直接循环判断
                    boolean exist = false;
                    for (String clazz : classes) {
                        try {
                            Class<?> aClass = ClassUtils.forName(clazz, null);
                            adviceFilterClassesSet.add(aClass);
                            if (!exist) {
                                exist = aClass == returnClass;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    return exist;
                }
            }
        }
        if (adviceFilterClassesSet.isEmpty()) {
            return false;
        }
        return adviceFilterClassesSet.contains(returnClass);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (filterClasses(returnType)) {
            return false;
        }
        // 不支持字符串 StringHttpMessageConverter, 大多数通用的httpMessageConverter都实现了GenericHttpMessageConverter. 不实现的不支持
        if (!GenericHttpMessageConverter.class.isAssignableFrom(converterType)) return false;
        // 由于StringHttpMessageConverter位于messageConverters 最前面, 所以如果Controller 直接返回String, 会由StringHttpMessageConverter处理
        // 最好不要在Controller中直接返回字符串
        return !(AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), NoResult.class) || returnType.hasMethodAnnotation(NoResult.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter mp, MediaType mt, Class<? extends HttpMessageConverter<?>> mcc, ServerHttpRequest req, ServerHttpResponse resp) {
        if (null == body) return R.ok(null);
        if (body.getClass() == Result.class) return body;
        return R.ok(body);
    }

}
