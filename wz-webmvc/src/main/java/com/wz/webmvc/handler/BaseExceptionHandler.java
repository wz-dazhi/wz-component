package com.wz.webmvc.handler;

import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.CommonException;
import com.wz.common.exception.SystemException;
import com.wz.common.model.Result;
import com.wz.common.util.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.handler
 * @className: BaseExceptionHandler
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 16:55
 * @version: 1.0
 */
@Slf4j
abstract class BaseExceptionHandler {

    protected String paramHandlerException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        String msg;
        if (e instanceof BindException) {
            // 对象绑定(form 表单 or URL params)
            BindException be = (BindException) e;
            FieldError fe = be.getBindingResult().getFieldErrors().get(0);
            msg = "[" + fe.getField() + "] " + fe.getDefaultMessage();
        } else if (e instanceof MethodArgumentNotValidException) {
            // json 实体 -> @Validated @RequestBody Entity entity
            MethodArgumentNotValidException ae = (MethodArgumentNotValidException) e;
            FieldError fe = ae.getBindingResult().getFieldErrors().get(0);
            msg = "[" + fe.getField() + "] " + fe.getDefaultMessage();
        } else if (e instanceof ConstraintViolationException) {
            // URL param 参数(单一参数 -> @NotNull String name)
            ConstraintViolationException ce = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = ce.getConstraintViolations();
            ConstraintViolation<?> v = violations.iterator().next();
            Iterator<Path.Node> iterator = v.getPropertyPath().iterator();
            String paramName = null;
            while (iterator.hasNext()) {
                Path.Node node = iterator.next();
                ElementKind kind = node.getKind();
                if (ElementKind.PARAMETER == kind) {
                    paramName = node.getName();
                    break;
                }
            }
            msg = "[" + paramName + "] " + v.getMessage();
        } else {
            this.error(req, e);
            msg = ResultEnum.PARAM_ERROR.getMsg();
        }
        return msg;
    }

    protected Result<Void> otherHandlerException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        Result<Void> result;
        if (e instanceof SystemException) {
            SystemException se = (SystemException) e;
            result = Results.fail(se.getCode(), se.getMsg());
        } else if (CommonException.class.isAssignableFrom(e.getClass())) {
            CommonException se = (CommonException) e;
            result = Results.fail(se.getCode(), se.getMsg());
        } else {
            this.error(req, e);
            result = Results.fail();
        }
        return result;
    }

    protected void setResponse(HttpServletResponse resp) {
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }

    protected void error(HttpServletRequest req, Throwable t) {
        log.error("<<< A run exception has occurred, uri: [{}], e: ", req.getRequestURI(), t);
    }

    protected Map<String, Object> model(String code, String msg) {
        Map<String, Object> model = new HashMap<>(4);
        model.put("code", code);
        model.put("msg", msg);
        model.put("data", null);
        model.put("isSuccess", false);
        return model;
    }

}
