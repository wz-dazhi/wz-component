package com.wz.common.mapstruct;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: ListStreamMapper
 * @description:
 * @author: zhi
 * @date: 2021/3/31
 * @version: 1.0
 */
public interface ListSetStreamMapper<S, T> extends ListMapper<S, T>, SetMapper<S, T>, StreamMapper<S, T> {
}
