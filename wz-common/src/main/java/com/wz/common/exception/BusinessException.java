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
public class BusinessException extends RuntimeException {

    private String code;

    private String msg;

    public BusinessException() {
        this(ResultEnum.REQUEST_ERROR);
    }

    public BusinessException(IErrorCode iErrorCode) {
        this(iErrorCode.getCode(), iErrorCode.getMsg());
    }

    public BusinessException(String msg) {
        this(ResultEnum.REQUEST_ERROR.getCode(), msg);
    }

    public BusinessException(String code, String msg) {
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
