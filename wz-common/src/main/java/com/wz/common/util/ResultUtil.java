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
    public static Result ok() {
        return ok(null);
    }

    /**
     * 成功返回对象
     *
     * @param data
     * @return
     */
    public static Result ok(Object data) {
        ResultEnum ok = ResultEnum.OK;
        return Result.builder().code(ok.getErrorCode()).msg(ok.getErrorMsg()).data(data).build();
    }

    /**
     * 默认系统异常
     *
     * @return
     */
    public static Result fail() {
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
    public static Result fail(String code, String msg) {
        return Result.builder().code(code).msg(msg).build();
    }

    /**
     * 失败
     *
     * @param ec {@link IErrorCode 实现类}
     * @return
     */
    public static Result fail(IErrorCode ec) {
        return fail(ec.getErrorCode(), ec.getErrorMsg());
    }

}
