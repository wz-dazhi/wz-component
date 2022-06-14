package com.wz.datasource.mybatisplus.model;

import java.util.function.Consumer;

/**
 * @projectName: wz-component
 * @package: com.wz.datasource.mybatisplus.model
 * @className: IPage
 * @description:
 * @author: zhi
 * @date: 2022/6/14
 * @version: 1.0
 */
public interface IPage<T> extends com.baomidou.mybatisplus.core.metadata.IPage<T> {

    /**
     * IPage 的数据操作
     *
     * @param consumer 消费函数
     */
    default void consumer(Consumer<? super T> consumer) {
        this.getRecords().forEach(consumer);
    }

}
