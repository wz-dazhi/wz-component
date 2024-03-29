package com.wz.common.exception;

import com.wz.common.enums.ResultEnum;

/**
 * @projectName: wz-component
 * @package: com.wz.common.exception
 * @className: CommonException
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
public class CommonException extends RuntimeException {
    private final String code;
    private final String msg;

    public CommonException() {
        this(ResultEnum.SYSTEM_ERROR);
    }

    public CommonException(Throwable cause) {
        super(cause);
        this.code = ResultEnum.SYSTEM_ERROR.code();
        this.msg = cause.getMessage();
    }

    public CommonException(IErrorCode iErrorCode) {
        this(iErrorCode.code(), iErrorCode.desc());
    }

    public CommonException(String msg) {
        this(ResultEnum.SYSTEM_ERROR.code(), msg);
    }

    public CommonException(String code, String msg) {
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
