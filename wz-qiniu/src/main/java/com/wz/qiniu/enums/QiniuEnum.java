package com.wz.qiniu.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu
 * @className: QiniuEnum
 * @description:
 * @author: Zhi
 * @date: 2020-03-08 16:07
 * @version: 1.0
 */
public enum QiniuEnum implements IErrorCode {

    /**
     * 上传失败
     */
    UPLOAD_FAIL("QN001"),

    ;


    private String code;

    QiniuEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return Resources.getMessage("i18n/qiniu/qiniu", this.code);
    }
}
