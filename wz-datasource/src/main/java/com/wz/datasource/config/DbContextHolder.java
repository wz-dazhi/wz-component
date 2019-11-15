package com.wz.datasource.config;

import com.wz.datasource.enums.DBEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @projectName: wz-datasource
 * @package: com.wz.datasource.config
 * @className: DbContextHolder
 * @description: 数据源上下文
 * @author: Zhi Wang
 * @date: 2019/2/23 3:31 PM
 * @version: 1.0
 **/
@Slf4j
public final class DbContextHolder {

    /**
     * 用于轮循的计数器
     */
    private static final AtomicInteger COUNT = new AtomicInteger();

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * All DataSource List
     */
    private final static List<DBEnum> ALL_DATA_SOURCE_KEYS = new ArrayList<>();

    /**
     * The constant SLAVE_DATA_SOURCE_KEYS.
     */
    private final static List<DBEnum> SLAVE_DATA_SOURCE_KEYS = new ArrayList<>();

    private DbContextHolder() {
    }

    static void addAllDataSource(DBEnum... dbs) {
        if (null == dbs) {
            throw new NullPointerException("DBEnum cannot is null...");
        }
        ALL_DATA_SOURCE_KEYS.addAll(Arrays.asList(dbs));
    }

    static void addSlaveDataSource(DBEnum... dbs) {
        if (null == dbs) {
            throw new NullPointerException("DBEnum cannot is null...");
        }
        SLAVE_DATA_SOURCE_KEYS.addAll(Arrays.asList(dbs));
    }

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Use master data source.
     */
    static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DBEnum.MASTER.name());
    }

    /**
     * 当使用只读数据源时通过轮循方式选择要使用的数据源
     */
    public static void useSlaveDataSource() {
        try {
            int datasourceKeyIndex = COUNT.get() % SLAVE_DATA_SOURCE_KEYS.size();
            CONTEXT_HOLDER.set(SLAVE_DATA_SOURCE_KEYS.get(datasourceKeyIndex).name());
            COUNT.incrementAndGet();
        } catch (Exception e) {
            log.error("Switch slave datasource failed, err msg is {}, e: {}", e.getMessage(), e);
            COUNT.set(0);
            useMasterDataSource();
        }
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * Check if give DataSource is in current DataSource list
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(DBEnum key) {
        return ALL_DATA_SOURCE_KEYS.contains(key);
    }

}
