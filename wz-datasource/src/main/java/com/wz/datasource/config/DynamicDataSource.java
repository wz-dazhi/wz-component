package com.wz.datasource.config;

import com.wz.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @projectName: wz
 * @package: com.wz.datasource.config
 * @className: DynamicDataSource
 * @description: 动态数据源
 * @author: Zhi Wang
 * @date: 2019/2/23 3:31 PM
 * @version: 1.0
 **/
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DbContextHolder.getDataSourceKey();
        if (StringUtil.isBlank(dataSourceKey)) {
            DbContextHolder.useMasterDataSource();
            dataSourceKey = DbContextHolder.getDataSourceKey();
        }
        log.debug("Current datasource is: [{}]", dataSourceKey);
        return dataSourceKey;
    }

}
