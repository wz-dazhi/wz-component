package com.wz.webmvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wz.common.enums.ResultEnum;
import com.wz.common.model.Result;
import com.wz.common.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestControllerAdvice(annotations = RestController.class)
public class GlobalApiExceptionHandler extends BaseExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalStateException.class})
    public ModelAndView paramException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        Result<Void> result = Results.fail(ResultEnum.PARAM_ERROR.getCode(), super.paramHandlerException(req, resp, e));
        return this.modelAndView(result);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView otherException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        this.setResponse(resp);
        Result<Void> result = super.otherHandlerException(req, resp, e);
        return this.modelAndView(result);
    }

    protected ModelAndView modelAndView(Result<Void> r) {
        return new ModelAndView(new MappingJackson2JsonView(objectMapper), this.model(r.getCode(), r.getMsg()));
    }

}
