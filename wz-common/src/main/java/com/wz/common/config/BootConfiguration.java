package com.wz.common.config;

import com.fasterxml.classmate.TypeResolver;
import com.wz.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;

/**
 * @projectName: wz-component
 * @package: com.wz.common.config
 * @className: BootConfiguration
 * @description:
 * @author: zhi
 * @date: 2020/11/4 10:06
 * @version: 1.0
 */
@Configuration
@ComponentScan("com.wz")
public class BootConfiguration {
    @Autowired(required = false)
    private TypeResolver typeResolver;

    @Autowired(required = false)
    @Qualifier("defaultApi")
    private Docket docket;

    @PostConstruct
    public void init() {
        if (docket != null && typeResolver != null) {
            docket.alternateTypeRules(this.alternateTypeRules());
        }
    }

    private AlternateTypeRule[] alternateTypeRules() {
        AlternateTypeRule[] array = new AlternateTypeRule[1];
        array[0] = AlternateTypeRules.newRule(
                typeResolver.resolve(Result.class, typeResolver.resolve(WildcardType.class)),
                typeResolver.resolve(WildcardType.class));
        return array;
    }
}
