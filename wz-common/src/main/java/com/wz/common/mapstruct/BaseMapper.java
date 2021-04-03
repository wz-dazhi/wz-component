package com.wz.common.mapstruct;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: BaseMapper
 * @description: <pre>
 *  1. MapStruct 通用基类, 暂无特殊配置
 *  2. MapStruct 映射基类, 直接继承接口即可使用通用方法, 针对性指定需在继承类的接口方法上指定
 *      例: public interface xxxMapping extends BaseMapper<xxxDO, xxxDTO>
 * </pre>
 * @author: zhi
 * @date: 2021/3/31
 * @version: 1.0
 */
public interface BaseMapper<S, T> {

    /**
     * 映射同名属性
     */
    @InheritConfiguration
    T sourceToTarget(S s);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    S targetToSource(T t);

    /**
     * 后置处理器
     */
    @AfterMapping
    default void afterSourceToTarget(S s, @MappingTarget T t) {
        afterSourceToTargetHandler(s, t);
    }

    /**
     * 后置处理器
     */
    @AfterMapping
    default void afterTargetToSource(T t, @MappingTarget S s) {
        afterTargetToSourceHandler(t, s);
    }

    default void afterSourceToTargetHandler(S s, T t) {
        // TODO 覆盖此方法处理其他复杂转换逻辑
    }

    default void afterTargetToSourceHandler(T t, S s) {
        // TODO 覆盖此方法处理其他复杂转换逻辑
    }

    /**
     * 前置处理器
     */
    @BeforeMapping
    default void beforeSourceToTarget(S s, @MappingTarget T t) {
        beforeSourceToTargetHandler(s, t);
    }

    /**
     * 前置处理器
     */
    @BeforeMapping
    default void beforeTargetToSource(T t, @MappingTarget S s) {
        beforeTargetToSourceHandler(t, s);
    }

    default void beforeSourceToTargetHandler(S s, T t) {
        // TODO 覆盖此方法处理其他复杂转换逻辑
    }

    default void beforeTargetToSourceHandler(T t, S s) {
        // TODO 覆盖此方法处理其他复杂转换逻辑
    }
}
