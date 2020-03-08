package com.wz.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangzhi
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.redisson")
public class RedissonProperties {

    /**
     * redisson 的配置文件, JSON/YAML
     */
    private String config = "classpath:redisson.yml";

    /**
     * 是否启用Redisson, 默认不启用
     */
    private boolean enable;

}
