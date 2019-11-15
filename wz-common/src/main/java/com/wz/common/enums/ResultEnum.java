package com.wz.common.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz
 * @package: com.common.enums
 * @className: ResultEnum
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午10:39
 **/
public enum ResultEnum implements IErrorCode {

    /**
     * 成功
     */
    OK("0"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR("-1"),

    /**
     * 参数错误
     */
    PARAM_ERROR("-2"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("-3"),

    ;


    private String code;

    ResultEnum(String code) {
        this.code = code;
    }

    @Override
    public String getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return Resources.getMessage(this.code);
    }
}
