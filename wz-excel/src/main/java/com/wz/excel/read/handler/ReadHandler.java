package com.wz.excel.read.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read
 * @className: ReadHandler
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
public interface ReadHandler<T> extends ReadListener<T> {

    /**
     * 批量默认100条
     */
    int DEFAULT_BATCH_COUNT = 100;


    /**
     * 解析一条调用该方法
     */
    default void parseInvoke(T t, AnalysisContext ac) {
    }

    /**
     * 处理缓存数据
     */
    void processCachedData(List<T> cachedList);

}
