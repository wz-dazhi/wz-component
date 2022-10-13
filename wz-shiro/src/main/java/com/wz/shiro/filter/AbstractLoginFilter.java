package com.wz.shiro.filter;

import com.wz.common.exception.CommonException;
import com.wz.common.exception.SystemException;
import com.wz.common.util.CollectionUtil;
import com.wz.common.util.MapUtil;
import com.wz.common.util.StringUtil;
import com.wz.shiro.annotation.Anon;
import com.wz.shiro.bean.ShiroProperties;
import com.wz.shiro.enums.ShiroEnum;
import com.wz.swagger.util.R;
import com.wz.webmvc.filter.FilterHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wz.shiro.enums.ShiroEnum.METHOD_NOT_ALLOWED;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.filter
 * @className: AbstractLoginFilter
 * @description:
 * @author: zhi
 * @date: 2020/12/12 下午8:14
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractLoginFilter extends AuthenticatingFilter implements EnvironmentAware, ApplicationListener<ContextRefreshedEvent> {
    protected ShiroProperties shiroProperties;
    protected ApplicationContext applicationContext;
    protected List<HandlerMapping> handlerMappings;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.applicationContext = event.getApplicationContext();
        this.initHandlerMappings(applicationContext);
    }

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
        // 使用@Anon注解, 直接通过
        final boolean isAnonUri = this.isAnonUri(req);
        if (isAnonUri) {
            return true;
        }
        // 配置了anonStartUri, 直接通过
        final boolean isStartAnonUri = this.isStartAnonUri(req);
        if (isStartAnonUri) {
            return true;
        }

        final String token = this.getToken(req);
        // 判断是否登录. 如果登录请求, 不让通过. 在不通过的方法中执行登录逻辑
        final boolean isLoginRequest = isLoginRequest(request, response);
        final boolean tokenIsBlank = StringUtil.isBlank(token);
        if (isLoginRequest || tokenIsBlank) {
            return false;
        }

        // 子类校验Token
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
                FilterHelper.writeResponse(response, R.fail(METHOD_NOT_ALLOWED));
                return false;
            }
            // 执行登录逻辑，需要实现createToken方法
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                String code = null;
                String msg = null;
                if (e instanceof CommonException) {
                    CommonException ce = (CommonException) e;
                    code = ce.getCode();
                    msg = ce.getMsg();
                } else if (e instanceof SystemException) {
                    SystemException se = (SystemException) e;
                    code = se.getCode();
                    msg = se.getMsg();
                }
                if (StringUtil.isNoneBlank(code, msg)) {
                    FilterHelper.writeResponse(response, R.fail(code, msg));
                    return false;
                } else {
                    throw e;
                }
            }
        }
        FilterHelper.writeResponse(response, R.fail(ShiroEnum.UNAUTHORIZED));
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken t, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        log.debug("Shiro 登录成功: {}", t);
        // 生成 token 令牌
        final String token = this.onGenerateToken(t, subject, (HttpServletRequest) request, (HttpServletResponse) response);
        FilterHelper.writeResponse(response, R.ok(token));
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Shiro 登录失败. AuthenticationToken: {}, e: ", token, e);
        FilterHelper.writeResponse(response, R.fail(ShiroEnum.AUTHENTICATION_FAILED));
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

    /**
     * 判断是否属于@Anon的URI
     */
    protected boolean isAnonUri(HttpServletRequest req) {
        if (this.handlerMappings != null) {
            HandlerMethod m = null;
            for (HandlerMapping mapping : handlerMappings) {
                try {
                    HandlerExecutionChain handler = mapping.getHandler(req);
                    if (handler != null && handler.getHandler() instanceof HandlerMethod) {
                        m = (HandlerMethod) handler.getHandler();
                        break;
                    }
                } catch (Exception e) {
                    log.error(">>> HandlerMapping.getHandler(req) e: ", e);
                }
            }
            if (null != m) {
                // @Anon写在类上 || @Anon写在方法上
                return m.getBeanType().isAnnotationPresent(Anon.class) || m.hasMethodAnnotation(Anon.class);
            }
        }
        return false;
    }

    /**
     * 判断是否配置了start uri
     */
    protected boolean isStartAnonUri(HttpServletRequest req) {
        final Set<String> anonStartUris = shiroProperties.getAnonStartUris();
        if (anonStartUris != null && !anonStartUris.isEmpty()) {
            final String uri = req.getRequestURI();
            for (String startUri : anonStartUris) {
                if (uri.startsWith(startUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initHandlerMappings(ApplicationContext applicationContext) {
        Map<String, HandlerMapping> allRequestHandlerMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerMapping.class, true, false);
        Collection<HandlerMapping> handlerMappings;
        if (MapUtil.isNotEmpty(allRequestHandlerMappings) && CollectionUtil.isNotEmpty(handlerMappings = allRequestHandlerMappings.values())) {
            this.handlerMappings = new ArrayList<>(handlerMappings);
        }
    }

    @Override
    public final void setEnvironment(Environment environment) {
        final ConfigurationProperties p = ShiroProperties.class.getAnnotation(ConfigurationProperties.class);
        if (null != p) {
            final Binder binder = Binder.get(environment);
            this.shiroProperties = binder.bind(p.prefix(), ShiroProperties.class).orElse(new ShiroProperties());
        }
    }
}
