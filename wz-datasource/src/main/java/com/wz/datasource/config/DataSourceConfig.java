package com.wz.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.wz.datasource.enums.DBEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

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

}
