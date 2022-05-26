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
public class ParameterException extends CommonException {

    public ParameterException() {
        super(ResultEnum.PARAM_ERROR);
    }

    public ParameterException(IErrorCode iErrorCode) {
        super(iErrorCode.code(), iErrorCode.desc());
    }

    public ParameterException(String msg) {
        super(ResultEnum.PARAM_ERROR.code(), msg);
    }

    public ParameterException(String code, String msg) {
        super(code, msg);
    }

}
