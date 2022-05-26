package com.wz.common.exception;

import com.wz.common.enums.ResultEnum;

/**
 * @projectName: wz
 * @package: com.common.exception
 * @className: SystemException
 * @description: 系统异常
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午6:24
 **/
public class SystemException extends Exception {
    private final String code;
    private final String msg;

    public SystemException() {
        this(ResultEnum.SYSTEM_ERROR);
    }

    public SystemException(IErrorCode iErrorCode) {
        this(iErrorCode.code(), iErrorCode.desc());
    }

    public SystemException(String msg) {
        this(ResultEnum.SYSTEM_ERROR.code(), msg);
    }

    private SystemException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
