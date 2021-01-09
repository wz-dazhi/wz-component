package com.wz.shiro.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.enums
 * @className: ShiroEnum
 * @description:
 * @author: Zhi Wang
 * @createDate: 2020/12/15 上午10:39
 **/
public enum ShiroEnum implements IErrorCode {

    /**
     * 未认证
     */
    UNAUTHORIZED("401"),

    /**
     * 请求方式不允许
     */
    METHOD_NOT_ALLOWED("405"),

    ;

    private String code;

    ShiroEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return Resources.getMessage("i18n/shiro/shiro", this.code);
    }
}
