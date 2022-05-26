package com.wz.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @projectName: wz-component
 * @package: com.wz.web.config
 * @className: AopConfig
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 17:17
 * @version: 1.0
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class AopConfig {
}
