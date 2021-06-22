package com.wz.excel.exception;

import com.wz.common.exception.CommonException;
import com.wz.common.exception.IErrorCode;
import com.wz.excel.enums.ExcelEnum;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.exception
 * @className: ExcelException
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
public class ExcelException extends CommonException {

    public ExcelException() {
        super(ExcelEnum.EXPORT_ERROR);
    }

    public ExcelException(IErrorCode iErrorCode) {
        super(iErrorCode.getCode(), iErrorCode.getMsg());
    }

    public ExcelException(String code, String msg) {
        super(msg);
    }

    public ExcelException(String msg) {
        super(ExcelEnum.EXPORT_ERROR.getCode(), msg);
    }

}
