package com.wz.common.mapstruct;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: MapMapper
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
public interface MapMapper<S, T> extends BaseMapper<S, T> {

    /**
     * map to source
     *
     * @param map map对象
     * @return source
     */
    S mapToSource(Map<String, String> map);

    /**
     * map to target
     *
     * @param map map 对象
     * @return target
     */
    T mapToTarget(Map<String, String> map);

    /**
     * mapToSource 前置处理器
     */
    @BeforeMapping
    default void beforeMapToSource(Map<String, String> map, @MappingTarget S s) {
        beforeMapToSourceHandler(map, s);
    }

    default void beforeMapToSourceHandler(Map<String, String> map, S s) {
    }

    /**
     * mapToTarget 前置处理器
     */
    @BeforeMapping
    default void beforeMapToTarget(Map<String, String> map, @MappingTarget T t) {
        beforeMapToTargetHandler(map, t);
    }

    default void beforeMapToTargetHandler(Map<String, String> map, T t) {
    }

    /**
     * mapToSource 后置处理器
     */
    @AfterMapping
    default void afterMapToSource(Map<String, String> map, @MappingTarget S s) {
        afterMapToSourceHandler(map, s);
    }

    default void afterMapToSourceHandler(Map<String, String> map, S s) {
    }

    /**
     * mapToTarget 后置处理器
     */
    @AfterMapping
    default void afterMapToTarget(Map<String, String> map, @MappingTarget T t) {
        afterMapToTargetHandler(map, t);
    }

    default void afterMapToTargetHandler(Map<String, String> map, T t) {
    }

}
