package com.wz.swagger.util;

import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.IErrorCode;
import com.wz.swagger.model.Result;
import lombok.experimental.UtilityClass;

/**
 * @projectName: wz
 * @package: com.wz.swagger.util
 * @className: R
 * @description: 返回工具类
 * @author: Zhi Wang
 * @createDate: 2022/6/14 上午11:52
 **/
@UtilityClass
public class R {

    /**
     * 默认成功
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 成功返回对象
     */
    public static <T> Result<T> ok(T t) {
        return ok(t, ResultEnum.OK.desc());
    }

    /**
     * 成功, 并携带自定义msg
     */
    public static <T> Result<T> ok(T t, String msg) {
        return new Result<>(ResultEnum.OK.code(), msg, t);
    }

    /**
     * 默认系统异常
     *
     * @return
     */
    public static <T> Result<T> fail() {
        ResultEnum se = ResultEnum.REQUEST_ERROR;
        return fail(se.code(), se.desc());
    }

    /**
     * 失败
     *
     * @param code 自定义code
     * @param msg  自定义msg
     * @return
     */
    public static <T> Result<T> fail(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 失败
     *
     * @param ec {@link IErrorCode 实现类}
     * @return
     */
    public static <T> Result<T> fail(IErrorCode ec) {
        return fail(ec.code(), ec.desc());
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(String msg) {
        return fail(ResultEnum.REQUEST_ERROR.code(), msg);
    }

}
