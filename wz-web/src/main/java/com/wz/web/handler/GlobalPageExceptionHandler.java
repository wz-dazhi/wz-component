package com.wz.web.handler;

import com.wz.common.enums.ResultEnum;
import com.wz.common.model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: wz-web
 * @package: com.wz.web.handler
 * @className: GlobalPageExceptionHandler
 * @description: 全局页面异常处理
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午12:42
 **/
@ControllerAdvice(annotations = Controller.class)
public class GlobalPageExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ModelAndView paramException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        return new ModelAndView("redirect:/static/error/500.html", this.model(ResultEnum.PARAM_ERROR.getErrorCode(), super.paramHandlerException(req, resp, e)));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView otherException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        Result r = super.otherHandlerException(req, resp, e);
        return new ModelAndView("redirect:/static/error/500.html", this.model(r.getCode(), r.getMsg()));
    }

    private Map<String, String> model(String code, String msg) {
        Map<String, String> model = new HashMap<>(2);
        model.put("code", code);
        model.put("msg", msg);
        return model;
    }

}
