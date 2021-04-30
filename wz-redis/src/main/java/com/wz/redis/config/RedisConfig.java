package com.wz.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.common.util.StringUtil;
import com.wz.redis.serializer.KeySerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
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
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    public static final String REDIS_TEMPLATE_BEAN = "redisTemplate";

    @Value("#{ @environment['spring.redis.key-prefix'] }")
    private String redisKeyPrefix;

    private final ObjectMapper objectMapper;

    /**
     * @return RedisTemplate
     * @author wangzhi
     * @Description 实例化redisTemplate
     * @date 2018/1/2 17:36
     */
    @Bean(REDIS_TEMPLATE_BEAN)
    @Resource
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory, RedisSerializer<String> stringRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // json 序列化方式,用于value
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisConnectionFactory.setValidateConnection(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 初始化RedisTemplate
        redisTemplate.afterPropertiesSet();
        final String pong = redisTemplate.execute(RedisConnectionCommands::ping);
        log.info(">>> RedisTemplate ping: [{}]", pong);
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
    public RedisSerializer<String> stringRedisSerializer() {
        return StringUtil.isBlank(redisKeyPrefix) ? new StringRedisSerializer() : new KeySerializer(redisKeyPrefix);
    }

}
