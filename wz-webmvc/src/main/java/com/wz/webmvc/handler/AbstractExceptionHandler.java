package com.wz.webmvc.handler;

import com.wz.common.enums.ResultEnum;
import com.wz.common.exception.CommonException;
import com.wz.common.exception.SystemException;
import com.wz.swagger.model.Result;
import com.wz.swagger.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

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
 * @className: AbstractExceptionHandler
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 16:55
 * @version: 1.0
 */
public abstract class AbstractExceptionHandler {

    public static final String PARAM_ERROR_CODE = ResultEnum.PARAM_ERROR.code();

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

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
            msg = ResultEnum.PARAM_ERROR.desc();
        }
        return msg;
    }

    public ModelAndView exception(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        Result<Void> r = handlerException(req, resp, e);
        return modelAndView(r.getCode(), r.getMsg());
    }

    protected Result<Void> handlerException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        if (e instanceof SystemException) {
            SystemException se = (SystemException) e;
            return R.fail(se.getCode(), se.getMsg());
        } else if (CommonException.class.isAssignableFrom(e.getClass())) {
            CommonException se = (CommonException) e;
            return R.fail(se.getCode(), se.getMsg());
        }
        this.error(req, e);
        return R.fail();
    }

    protected void setResponse(HttpServletResponse resp) {
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }

    protected void error(HttpServletRequest req, Throwable t) {
        log.error("<<< A run exception has occurred, uri: [{}], e: ", req.getRequestURI(), t);
    }

    protected ModelAndView modelAndView(String code, String msg) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> map = this.model(code, msg);
        mv.addAllObjects(map);
        modelAndViewAfter(mv);
        return mv;
    }

    protected void modelAndViewAfter(ModelAndView mv) {
    }

    protected Map<String, Object> model(String code, String msg) {
        // see Result
        Map<String, Object> model = new HashMap<>(4);
        model.put("code", code);
        model.put("msg", msg);
        model.put("data", null);
        model.put("success", false);
        return model;
    }

}
