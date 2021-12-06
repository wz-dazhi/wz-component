package com.wz.common.enums;

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
}
