package com.wz.common.mapstruct;

import org.mapstruct.InheritConfiguration;

import java.util.Set;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: SetMapper
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
public interface SetMapper<S, T> extends BaseMapper<S, T> {
    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    Set<T> sourceToTarget(Set<S> s);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    Set<S> targetToSource(Set<T> t);
}
