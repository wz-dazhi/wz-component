package com.wz.shiro.filter;

import com.wz.common.enums.ResultEnum;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.Results;
import com.wz.common.util.StringUtil;
import com.wz.shiro.bean.ShiroProperties;
import com.wz.shiro.enums.ShiroEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wz.shiro.enums.ShiroEnum.METHOD_NOT_ALLOWED;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.filter
 * @className: AbstractShiroFilter
 * @description:
 * @author: zhi
 * @date: 2020/12/12 下午8:14
 * @version: 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractShiroFilter extends AuthenticatingFilter {
    protected final ShiroProperties shiroProperties;

    /**
     * 执行登录需要创建token
     */
    @Override
    protected abstract AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception;

    /**
     * 是否允许通过
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        final String token = this.getToken(req);
        // 判断是否登录. 如果登录请求, 不让通过. 在不通过的方法中执行登录逻辑
        final boolean isLoginRequest = isLoginRequest(request, response);
        final boolean tokenIsBlank = StringUtil.isBlank(token);
        if (isLoginRequest || tokenIsBlank) {
            return false;
        }

        return this.verifyToken(token, req, resp);
    }

    /**
     * 是否允许不通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if (isLoginRequest(request, response)) {
            // 限制只能POST登录
            if (!req.getMethod().equalsIgnoreCase(POST_METHOD)) {
                log.warn("Method is not Allowed. request uri: {}, method: {}", req.getRequestURI(), req.getMethod());
                this.writeResponse(response, Results.fail(METHOD_NOT_ALLOWED));
                return false;
            }
            // 执行登录逻辑，需要实现createToken方法
            return executeLogin(request, response);
        }
        this.writeResponse(response, Results.fail(ShiroEnum.UNAUTHORIZED));
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken t, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        log.debug("登录成功: {}", t);
        // 生成 token 令牌
        final String token = this.onGenerateToken(t, subject, (HttpServletRequest) request, (HttpServletResponse) response);
        this.writeResponse(response, Results.ok(token));
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("登录失败. AuthenticationToken: {}, e: ", token, e);
        this.writeResponse(response, Results.fail(ResultEnum.REQUEST_ERROR.getCode(), e.getMessage()));
        return false;
    }

    /**
     * 登录成功，生成token令牌
     */
    protected abstract String onGenerateToken(AuthenticationToken t, Subject subject, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 校验token
     */
    protected abstract boolean verifyToken(String token, HttpServletRequest req, HttpServletResponse resp);

    protected String getToken(HttpServletRequest req) {
        final String tokenName = shiroProperties.getToken();
        String token = req.getHeader(tokenName);
        if (StringUtil.isBlank(token)) {
            token = req.getParameter(tokenName);
        }
        return token;
    }

    protected void writeResponse(ServletResponse response, Object resObj) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(JsonUtil.toJsonString(resObj));
        } catch (IOException e) {
            log.error("响应异常. e: ", e);
        }
    }

}
