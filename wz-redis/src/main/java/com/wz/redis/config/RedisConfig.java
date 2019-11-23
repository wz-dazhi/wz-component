package com.wz.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @projectName: wz-redis
 * @package: com.wz.redis.config
 * @className: RedisConfig
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午7:02
 **/
@Configuration
@AllArgsConstructor
public class RedisConfig {
    private final ObjectMapper objectMapper;

    /**
     * @return RedisTemplate
     * @author wangzhi
     * @Description 实例化redisTemplate
     * @date 2018/1/2 17:36
     */
    @Bean
    @Resource
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory, RedisSerializer stringRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // json 序列化方式,用于value
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * @Description 获取ValueOperations, 用来操作String对象
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * @Description 获取HashOperations, 用来操作 Hash对象
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * @Description 获取ListOperations, 用来操作 list对象
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * @Description 获取SetOperations, 用来操作 set对象
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * @Description 获取ZSetOperations, 用来操作 zset对象
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public RedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

}
