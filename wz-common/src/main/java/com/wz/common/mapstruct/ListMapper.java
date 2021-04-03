package com.wz.common.mapstruct;

import org.mapstruct.InheritConfiguration;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: ListMapper
 * @description:
 * @author: zhi
 * @date: 2021/3/31
 * @version: 1.0
 */
public interface ListMapper<S, T> extends BaseMapper<S, T> {
    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> s);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<S> targetToSource(List<T> t);
}
