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

    private IErrorCode iErrorCode;

    private String code;

    private String msg;

    public ParameterException(IErrorCode iErrorCode) {
        this(iErrorCode, iErrorCode.getErrorMsg(), iErrorCode.getErrorMsg());
    }

    public ParameterException(String msg) {
        this(ResultEnum.PARAM_ERROR, ResultEnum.PARAM_ERROR.getErrorCode(), msg);
    }

    public ParameterException(String code, String msg) {
        this(ResultEnum.SYSTEM_ERROR, code, msg);
    }

    private ParameterException(IErrorCode iErrorCode, String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.iErrorCode = iErrorCode;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
