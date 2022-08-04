package com.wz.common.enums;

import java.util.Objects;
import java.util.Optional;

/**
 * @projectName: wz
 * @package: com.common.enums
 * @className: IEnum
 * @description: 枚举父类接口
 * @author: Zhi
 * @createDate: 2021/12/6 上午10:39
 **/
public interface IEnum<C, D> {

    /**
     * 获取编码
     */
    C code();

    /**
     * 获取描述
     */
    D desc();

    /**
     * 根据code从数组中获取desc
     */
    static <C, D, E extends IEnum<C, D>> D desc(E[] list, C c) {
        return Optional.ofNullable(instance(list, c))
                .map(E::desc)
                .orElse(null);
    }

    /**
     * 根据code从枚举数组中获取对应的实例
     */
    static <C, D, E extends IEnum<C, D>> E instance(E[] list, C c) {
        if (null == list) {
            return null;
        }
        for (E e : list) {
            if (Objects.equals(c, e.code())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据code从Class枚举中获取对应的实例, Class对象为枚举子类
     */
    static <C, D, E extends IEnum<C, D>> E instance(Class<E> clazz, C c) {
        if (clazz == null || !clazz.isEnum()) {
            return null;
        }
        return instance(clazz.getEnumConstants(), c);
    }

}

