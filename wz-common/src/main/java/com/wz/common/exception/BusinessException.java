package com.wz.common.exception;

import com.wz.common.enums.ResultEnum;

/**
 * @projectName: wz
 * @package: com.common.exception
 * @className: BusinessException
 * @description: 业务异常
 * @author: Zhi Wang
 * @createDate: 2018/9/8 下午6:24
 **/
public class BusinessException extends Exception {

    private IErrorCode iErrorCode;

    private String code;

    private String msg;

    public BusinessException(IErrorCode iErrorCode) {
        this(iErrorCode, iErrorCode.getErrorCode(), iErrorCode.getErrorMsg());
    }

    public BusinessException(String code, String msg) {
        this(ResultEnum.REQUEST_ERROR, code, msg);
    }

    private BusinessException(IErrorCode iErrorCode, String code, String msg) {
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
