package com.wz.common.exception;

/**
 * @projectName: wz
 * @package: com.common.exception
 * @className: IErrorCode
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午1:50
 **/
public interface IErrorCode {

    /**
     * 错误码
     *
     * @return
     */
    String getCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getMsg();
}
