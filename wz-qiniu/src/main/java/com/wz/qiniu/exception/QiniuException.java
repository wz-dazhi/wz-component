package com.wz.qiniu.exception;

import com.wz.common.exception.CommonException;
import com.wz.common.exception.IErrorCode;
import com.wz.qiniu.enums.QiniuEnum;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.exception
 * @className: QiniuException
 * @description:
 * @author: zhi
 * @date: 2021/5/14
 * @version: 1.0
 */
public class QiniuException extends CommonException {

    public QiniuException() {
        super(QiniuEnum.UPLOAD_FAIL);
    }

    public QiniuException(IErrorCode iErrorCode) {
        super(iErrorCode.code(), iErrorCode.desc());
    }

    public QiniuException(String msg) {
        super(QiniuEnum.UPLOAD_FAIL.code(), msg);
    }

    public QiniuException(String code, String msg) {
        super(code, msg);
    }

}
