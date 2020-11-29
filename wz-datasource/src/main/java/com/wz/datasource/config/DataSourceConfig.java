package com.wz.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.wz.datasource.enums.DBEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
@EnableTransactionManagement
public class DataSourceConfig {

    @Resource
    private ResourceLoader resourceLoader;

    @Resource
    private MybatisPlusProperties plusProperties;

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
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
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource master,
                                               @Qualifier("slaveDataSource") DataSource slave) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dynamicDataSource(master, slave));
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.plusProperties.getConfigLocation())) {
            sqlSessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(this.plusProperties.getConfigLocation()));
        }
        // MP 拦截器
        sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor());
        // MP 全局配置，更多内容进入类看注释
        final GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig()
                .setIdType(IdType.AUTO)
                .setTableUnderline(true);
        GlobalConfig globalConfig = new GlobalConfig()
                .setDbConfig(dbConfig)
                .setBanner(false);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);

        if (StringUtils.hasLength(this.plusProperties.getTypeAliasesPackage())) {
            sqlSessionFactoryBean.setTypeAliasesPackage(this.plusProperties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.plusProperties.getTypeHandlersPackage())) {
            sqlSessionFactoryBean.setTypeHandlersPackage(this.plusProperties.getTypeHandlersPackage());
        }
        if (StringUtils.hasLength(this.plusProperties.getTypeEnumsPackage())) {
            sqlSessionFactoryBean.setTypeEnumsPackage(this.plusProperties.getTypeEnumsPackage());
        }
        if (!ObjectUtils.isEmpty(this.plusProperties.resolveMapperLocations())) {
            sqlSessionFactoryBean.setMapperLocations(this.plusProperties.resolveMapperLocations());
        }
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    @Resource
    public PlatformTransactionManager masterTransactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 性能插件
        //interceptor.addInnerInterceptor(illegalSQLInnerInterceptor());
        return interceptor;
    }

    /**
     * mybatis-plus分页插件
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    /**
     * 乐观锁插件
     */
    private OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * sql性能规范
     */
    private IllegalSQLInnerInterceptor illegalSQLInnerInterceptor() {
        return new IllegalSQLInnerInterceptor();
    }

}
