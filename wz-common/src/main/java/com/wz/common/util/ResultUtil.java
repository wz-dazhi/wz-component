package com.wz.common.util;

import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.IErrorCode;
import com.wz.common.model.Result;

/**
 * @version 1.0
 * @projectName: wz
 * @package: com.common.util
 * @className: ResultUtil
 * @description: 返回工具类
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午11:52
 **/
public class ResultUtil {

    private ResultUtil() {
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 成功返回对象
     *
     * @param t
     * @return
     */
    public static <T> Result<T> ok(T t) {
        ResultEnum ok = ResultEnum.OK;
        return new Result<>(ok.getErrorCode(), ok.getErrorMsg(), t);
    }

    /**
     * 默认系统异常
     *
     * @return
     */
    public static <T> Result<T> fail() {
        ResultEnum se = ResultEnum.SYSTEM_ERROR;
        return fail(se.getErrorCode(), se.getErrorMsg());
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
        return fail(ec.getErrorCode(), ec.getErrorMsg());
    }

}
