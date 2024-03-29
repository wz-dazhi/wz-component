package com.wz.webmvc.handler;

import com.wz.common.enums.ResultEnum;
import com.wz.common.model.Result;
import com.wz.common.util.Results;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @projectName: wz-web
 * @package: com.wz.webmvc.handler
 * @className: GlobalApiExceptionHandler
 * @description: 全局API异常处理
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午12:42
 **/
@RestControllerAdvice(annotations = RestController.class)
public class GlobalApiExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalStateException.class})
    public Result<Void> paramException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        return Results.fail(ResultEnum.PARAM_ERROR.getCode(), super.paramHandlerException(req, resp, e));
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> otherException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        return super.otherHandlerException(req, resp, e);
    }

}
