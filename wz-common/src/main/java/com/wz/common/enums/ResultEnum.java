package com.wz.common.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz
 * @package: com.common.enums
 * @className: ResultEnum
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午10:39
 **/
public enum ResultEnum implements IErrorCode {

    /**
     * 成功
     */
    OK("0"),

    /**
     * 参数错误
     */
    PARAM_ERROR("-1"),

    /**
     * 服务器开小差, 请稍后再试
     */
    SYSTEM_ERROR("-2"),

    /**
     * 请求失败, 请稍后再试
     */
    REQUEST_ERROR("-3"),

    /**
     * 请勿重复提交
     */
    REQUEST_LIMIT("-4"),

    /**
     * 状态错误
     */
    STATE_ERROR("-5"),

    ;

    private final String code;

    ResultEnum(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String desc() {
        return Resources.getMessage(this.code);
    }
}
