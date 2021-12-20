package com.wz.common.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @projectName: wz-component
 * @package: com.wz.common.config
 * @className: BootConfiguration
 * @description:
 * @author: zhi
 * @date: 2020/11/4 10:06
 * @version: 1.0
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ComponentScan("com.wz")
public class BootConfiguration {

}
