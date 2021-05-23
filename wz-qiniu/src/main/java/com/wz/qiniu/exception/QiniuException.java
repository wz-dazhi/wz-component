package com.wz.qiniu.exception;

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
public class QiniuException extends RuntimeException {

    private String code;

    private String msg;

    public QiniuException() {
        this(QiniuEnum.UPLOAD_FAIL);
    }

    public QiniuException(IErrorCode iErrorCode) {
        this(iErrorCode.getCode(), iErrorCode.getMsg());
    }

    public QiniuException(String msg) {
        this(QiniuEnum.UPLOAD_FAIL.getCode(), msg);
    }

    public QiniuException(String code, String msg) {
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
