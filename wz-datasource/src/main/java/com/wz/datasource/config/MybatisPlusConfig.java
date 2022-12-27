package com.wz.datasource.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.wz.datasource.common.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import com.wz.datasource.common.mybatisplus.interceptor.LikeQueryInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: wz-component
 * @package: com.wz.datasource.common.config
 * @className: MybatisPlusConfig
 * @description:
 * @author: zhi
 * @date: 2022/8/4
 * @version: 1.0
 */
@ConditionalOnProperty(name = "mybatis-plus.default.config", havingValue = "true", matchIfMissing = true)
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 添加插件需要注意插入顺序
        final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // like sql过滤
        interceptor.addInnerInterceptor(new LikeQueryInterceptor());
        // mybatis-plus分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}
