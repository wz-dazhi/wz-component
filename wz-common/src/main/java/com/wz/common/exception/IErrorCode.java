package com.wz.common.exception;

import com.wz.common.enums.IEnum;

/**
 * @projectName: wz
 * @package: com.common.exception
 * @className: IErrorCode
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午1:50
 **/
public interface IErrorCode extends IEnum<String, String> {

    /**
     * code码
     *
     * @return 错误码
     */
    @Override
    String code();

    /**
     * code对应的msg
     *
     * @return 错误描述信息
     */
    @Override
    String desc();
}
