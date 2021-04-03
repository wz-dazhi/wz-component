package com.wz.common.mapstruct;

import java.util.List;
import java.util.stream.Stream;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: StreamMapper
 * @description:
 * @author: zhi
 * @date: 2021/3/31
 * @version: 1.0
 */
public interface StreamMapper<S, T> extends BaseMapper<S, T> {
    /**
     * 映射同名属性，集合流形式
     */
    List<T> sourceToTarget(Stream<S> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<S> targetToSource(Stream<T> stream);
}
