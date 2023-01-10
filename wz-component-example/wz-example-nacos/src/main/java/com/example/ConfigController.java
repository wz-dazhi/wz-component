package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: wz-component
 * @package: com.example
 * @className: ConfigController
 * @description:
 * @author: zhi
 * @date: 2023/1/10
 * @version: 1.0
 */
@RestController
@RefreshScope
public class ConfigController {

    @Value("${key1}")
    private String v1;
    @Value("${key2}")
    private String v2;
    @Value("${key3}")
    private String v3;

    @GetMapping("/config")
    public String config() {
        return "v1: " + v1 + ", v2: " + v2 + ", v3: " + v3;
    }
}
