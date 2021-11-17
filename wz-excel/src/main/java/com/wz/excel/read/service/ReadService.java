package com.wz.excel.read.service;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read.service
 * @className: ReadService
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@FunctionalInterface
public interface ReadService<T> {

    /**
     * 处理已经读取的缓存数据
     *
     * @param cachedList 读取的缓存数据
     */
    void process(List<T> cachedList);

}
