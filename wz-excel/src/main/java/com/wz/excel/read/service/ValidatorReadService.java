package com.wz.excel.read.service;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.read.service
 * @className: ValidatorReadService
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@FunctionalInterface
public interface ValidatorReadService<T> extends ReadService<T> {

    /**
     * 跳过无效数据继续处理
     *
     * @return true: 继续执行process  false: 不执行process, cachedList数据会clear
     */
    default boolean skipInvalid() {
        return true;
    }
}
