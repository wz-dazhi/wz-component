package com.wz.shiro.config;

import com.wz.shiro.authc.ShiroModularRealmAuthenticator;
import com.wz.shiro.bean.ShiroProperties;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.config
 * @className: AbstractShiroConfig
 * @description:
 * @author: zhi
 * @date: 2020/12/9 下午3:30
 * @version: 1.0
 */
public class AbstractShiroConfig {
    @Autowired
    protected RedisProperties redisProperties;
    @Autowired
    protected ShiroProperties shiroProperties;
    @Autowired
    protected IRedisManager redisManager;

    @Bean
    public Authenticator authenticator(AuthenticationStrategy authenticationStrategy) {
        ModularRealmAuthenticator authenticator = new ShiroModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(authenticationStrategy);
        return authenticator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        shiroProperties.getPathDefinition().forEach(d -> chainDefinition.addPathDefinition(d.getPath(), d.getDefinition()));
        return chainDefinition;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        redisCacheManager.setExpire(shiroProperties.getCacheExpire());
        redisCacheManager.setKeyPrefix(shiroProperties.getCacheKeyPrefix());
        redisCacheManager.setPrincipalIdFieldName(shiroProperties.getCachePrincipalIdFieldName());
        return redisCacheManager;
    }

    /**
     * 配置shiro IRedisManager, 默认单机版
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public IRedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost() + ":" + redisProperties.getPort());
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setTimeout((int) redisProperties.getTimeout().toMillis());
        redisManager.setPassword(redisProperties.getPassword());
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager(SessionFactory sessionFactory, SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionFactory(sessionFactory);
        sessionManager.setDeleteInvalidSessions(shiroProperties.isDeleteInvalidSessions());
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(shiroProperties.getCacheExpire());
        redisSessionDAO.setRedisManager(redisManager);
        redisSessionDAO.setKeyPrefix(shiroProperties.getSessionDaoKeyPrefix());
        return redisSessionDAO;
    }

    /**
     * 凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(shiroProperties.getAlgorithm().getAlgorithmName());
        hashedCredentialsMatcher.setHashIterations(shiroProperties.getHashIterations());
        return hashedCredentialsMatcher;
    }

}
