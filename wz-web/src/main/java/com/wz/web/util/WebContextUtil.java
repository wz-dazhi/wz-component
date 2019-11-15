package com.wz.web.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @projectName: wz-component
 * @package: com.wz.web.util
 * @className: WebContextUtil
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 17:25
 * @version: 1.0
 */
public class WebContextUtil {
    public static HttpServletRequest getRequest() {
        return servletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return servletRequestAttributes().getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    private static ServletRequestAttributes servletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
