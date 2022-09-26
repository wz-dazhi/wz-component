package com.wz.webmvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.swagger.model.Result;
import com.wz.swagger.util.R;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalApiExceptionHandler extends AbstractExceptionHandler {
    private final MappingJackson2JsonView view;

    public GlobalApiExceptionHandler(ObjectMapper objectMapper) {
        this.view = new MappingJackson2JsonView(objectMapper);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalStateException.class})
    public ModelAndView paramException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        Result<Void> r = R.fail(PARAM_ERROR_CODE, super.paramHandlerException(req, resp, e));
        return modelAndView(r.getCode(), r.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        return super.exception(req, resp, e);
    }

    @Override
    protected void modelAndViewAfter(ModelAndView mv) {
        mv.setView(this.view);
    }

}
