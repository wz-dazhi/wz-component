package com.wz.webmvc.handler;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @projectName: wz-web
 * @package: com.wz.webmvc.handler
 * @className: GlobalPageExceptionHandler
 * @description: 全局页面异常处理
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午12:42
 **/
@Order(2)
@ControllerAdvice(annotations = Controller.class)
public class GlobalPageExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ModelAndView paramException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        return modelAndView(PARAM_ERROR_CODE, super.paramHandlerException(req, resp, e));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        return super.exception(req, resp, e);
    }

    @Override
    protected void modelAndViewAfter(ModelAndView mv) {
        // templates/error/500.html 需要加入spring-boot-starter-thymeleaf依赖
        mv.setViewName("/error/500");
    }
}
