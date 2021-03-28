package com.wz.redis.serializer;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @projectName: wz-component
 * @package: com.wz.redis.serializer
 * @className: KeySerializer
 * @description:
 * @author: zhi
 * @date: 2021/3/27
 * @version: 1.0
 */
public class KeySerializer extends StringRedisSerializer {

    private String redisKeyPrefix;

    public KeySerializer(String redisKeyPrefix) {
        super();
        this.redisKeyPrefix = redisKeyPrefix;
    }

    @Override
    public String deserialize(byte[] bytes) {
        final String value = super.deserialize(bytes);
        return null == value ? value : value.replace(redisKeyPrefix, "");
    }

    @Override
    public byte[] serialize(String string) {
        return super.serialize(redisKeyPrefix + string);
    }
}
