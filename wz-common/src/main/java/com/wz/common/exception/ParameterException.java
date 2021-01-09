package com.wz.common.exception;

import com.wz.common.enums.ResultEnum;

/**
 * @projectName: wz
 * @package: com.common.exception
 * @className: ParameterException
 * @description: 参数异常
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午6:24
 **/
public class ParameterException extends RuntimeException {

    private String code;

    private String msg;

    public ParameterException() {
        this(ResultEnum.PARAM_ERROR);
    }

    public ParameterException(IErrorCode iErrorCode) {
        this(iErrorCode.getCode(), iErrorCode.getMsg());
    }

    public ParameterException(String msg) {
        this(ResultEnum.PARAM_ERROR.getCode(), msg);
    }

    public ParameterException(String code, String msg) {
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
