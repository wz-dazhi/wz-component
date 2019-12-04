package com.wz.encrypt.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz-component
 * @package: com.wz.encrypt.enums
 * @className: EncryptEnum
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 19:19
 * @version: 1.0
 */
public enum EncryptEnum implements IErrorCode {

    /**
     * 缺少签名信息
     */
    NOT_EXIST_SIGNATURE("SIGN-0001"),

    /**
     * 请求已过期,请重试
     */
    REQUEST_EXPIRED("SIGN-0002"),

    /**
     * 参数被篡改,请重试
     */
    PARAMETER_TAMPERED("SIGN-0003"),
    /**
     * 缺少签名时间
     */
    NOT_EXIST_SIGNATURE_TIME("SIGN-0004");

    private String code;

    EncryptEnum(String code) {
        this.code = code;
    }

    /**
     * 错误码
     *
     * @return
     */
    @Override
    public String getErrorCode() {
        return this.code;
    }

    /**
     * 错误信息
     *
     * @return
     */
    @Override
    public String getErrorMsg() {
        return Resources.getMessage("i18n/encrypt/encrypt", this.code);
    }
}
