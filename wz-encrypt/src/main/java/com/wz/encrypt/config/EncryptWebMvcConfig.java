package com.wz.encrypt.config;

import com.wz.encrypt.interceptor.SignInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @projectName: wz-component
 * @package: com.wz.encrypt.config
 * @className: EncryptWebMvcConfig
 * @description:
 * @author: Zhi
 * @date: 2019-12-04 23:28
 * @version: 1.0
 */
@AllArgsConstructor
public class EncryptWebMvcConfig implements WebMvcConfigurer {
    private final EncryptProperties encryptProperties;
    private final SignInterceptor signInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 开启签名
        if (encryptProperties.isSignEnable()) {
            registry.addInterceptor(signInterceptor)
                    .addPathPatterns(encryptProperties.getSignPath())
                    .order(Ordered.HIGHEST_PRECEDENCE);
        }
    }

}
