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
public class BusinessException extends CommonException {

    public BusinessException() {
        super(ResultEnum.REQUEST_ERROR);
    }

    public BusinessException(IErrorCode iErrorCode) {
        super(iErrorCode.getCode(), iErrorCode.getMsg());
    }

    public BusinessException(String msg) {
        super(ResultEnum.REQUEST_ERROR.getCode(), msg);
    }

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

}
