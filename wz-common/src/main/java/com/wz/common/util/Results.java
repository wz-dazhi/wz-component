package com.wz.common.util;

import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.IErrorCode;
import com.wz.common.model.Result;

/**
 * @projectName: wz
 * @package: com.common.util
 * @className: Results
 * @description: 返回工具类
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午11:52
 **/
public final class Results {

    private Results() {
    }

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
        return ok(t, ResultEnum.OK.getMsg());
    }

    /**
     * 成功, 并携带自定义msg
     */
    public static <T> Result<T> ok(T t, String msg) {
        return new Result<>(ResultEnum.OK.getCode(), msg, t);
    }

    /**
     * 默认系统异常
     *
     * @return
     */
    public static <T> Result<T> fail() {
        ResultEnum se = ResultEnum.REQUEST_ERROR;
        return fail(se.getCode(), se.getMsg());
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
        return fail(ec.getCode(), ec.getMsg());
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail(String msg) {
        return fail(ResultEnum.REQUEST_ERROR.getCode(), msg);
    }

}
