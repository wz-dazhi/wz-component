package com.wz.common.annotation;

import com.wz.common.config.BootConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @projectName: wz-component
 * @package: com.wz.common.annotation
 * @className: EnableBoot
 * @description:
 * @author: zhi
 * @date: 2020/11/4 10:05
 * @version: 1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BootConfiguration.class)
public @interface EnableBoot {
}
