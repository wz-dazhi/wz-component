package com.wz.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.classmate.TypeResolver;
import com.wz.common.model.Result;
import com.wz.datasource.enums.DBEnum;
import com.wz.datasource.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import com.wz.datasource.mybatisplus.interceptor.LikeQueryInterceptor;
import com.wz.datasource.mybatisplus.model.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: wz-datasource
 * @package: com.wz.datasource.config
 * @className: DataSourceConfig
 * @description: 多数据源配置
 * @author: Zhi Wang
 * @date: 2019/2/23 3:17 PM
 * @version: 1.0
 **/
@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan("com.wz.datasource")
public class DataSourceConfig {

    @Autowired(required = false)
    private TypeResolver typeResolver;

    @Autowired(required = false)
    @Qualifier("defaultApi")
    private Docket docket;

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DruidDataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DruidDataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @DependsOn({"masterDataSource", "slaveDataSource"})
    @Primary
    public DynamicDataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                               @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DBEnum master = DBEnum.MASTER;
        DBEnum slave = DBEnum.SLAVE;

        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(master.name(), masterDataSource);
        targetDataSources.put(slave.name(), slaveDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为myTestDbDataSourcereturn dataSource;
        dataSource.setDefaultTargetDataSource(masterDataSource);

        // 设置所有数据源
        DbContextHolder.addAllDataSource(master, slave);
        // 设置从数据源
        DbContextHolder.addSlaveDataSource(slave);
        return dataSource;
    }

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

    @PostConstruct
    public void init() {
        if (docket != null && typeResolver != null) {
            docket.alternateTypeRules(this.alternateTypeRules());
        }
    }

    private AlternateTypeRule[] alternateTypeRules() {
        AlternateTypeRule[] array = new AlternateTypeRule[1];
        array[0] = AlternateTypeRules.newRule(
                typeResolver.resolve(Result.class, typeResolver.resolve(Page.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class)
        );
        return array;
    }
}
