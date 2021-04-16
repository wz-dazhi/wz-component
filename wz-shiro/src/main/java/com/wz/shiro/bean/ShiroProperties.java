package com.wz.shiro.bean;

import com.wz.shiro.enums.AlgorithmEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.bean
 * @className: ShiroProperties
 * @description:
 * @author: zhi
 * @date: 2020/12/9 下午6:20
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "shiro.config")
public class ShiroProperties {
    /**
     * shiro 缓存过期时间, session 过期时间
     */
    private int cacheExpire = 1800;

    /**
     * 从会话存储中删除无效的会话, 默认为true
     */
    private boolean deleteInvalidSessions = true;

    /**
     * 加密方式, 默认MD5
     */
    private AlgorithmEnum algorithm = AlgorithmEnum.MD5;

    /**
     * 加密次数
     */
    private int hashIterations = 1;

    /**
     * path 定义
     */
    private List<ShiroPathDefinitionProperties> pathDefinition = new ArrayList<>();

    /**
     * token名称
     */
    private String token = "token";

    /**
     * 缓存key前缀, 默认 shiro:cache:
     */
    private String cacheKeyPrefix = "shiro:cache:";

    /**
     * 缓存字段名称, 默认 id
     */
    private String cachePrincipalIdFieldName = "id";

    /**
     * sessionDAO 缓存前缀, 默认 shiro:session:
     */
    private String sessionDaoKeyPrefix = "shiro:session:";

    /**
     * 不校验token的uri前缀
     */
    private Set<String> anonStartUris = new HashSet<>();
}
