package com.wz.common.mapstruct;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: BaseMapper
 * @description: <pre>
 *  1. MapStruct 通用配置, 暂无特殊配置
 *  2. MapStruct 映射基类, 直接继承接口即可使用通用方法, 针对性指定需在继承类的接口方法上指定
 *      例: public interface xxxMapping extends BaseMapping<xxxDO, xxxDTO>
 * </pre>
 * @author: zhi
 * @date: 2020/11/29 下午8:51
 * @version: 1.0
 */
@MapperConfig(componentModel = "spring")
public interface BaseMapping<S, T> {

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
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> s);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<S> targetToSource(List<T> t);

    /**
     * 映射同名属性，集合流形式
     */
    List<T> sourceToTarget(Stream<S> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<S> targetToSource(Stream<T> stream);

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
