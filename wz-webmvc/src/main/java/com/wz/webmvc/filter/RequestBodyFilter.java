package com.wz.webmvc.filter;

import com.wz.webmvc.wrapper.RequestBodyWrapper;
import org.springframework.http.HttpMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @projectName: wz-component
 * @package: com.wz.web.filter
 * @className: RequestBodyFilter
 * @description:
 * @author: zhi
 * @date: 2021/1/9 下午5:12
 * @version: 1.0
 */
public class RequestBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        // 替换成包装request, 不处理GET
        if (request instanceof HttpServletRequest) {
            final HttpServletRequest req = (HttpServletRequest) request;
            if (!HttpMethod.GET.name().equals(req.getMethod())) {
                requestWrapper = new RequestBodyWrapper(req);
            }
        }
        chain.doFilter(requestWrapper == null ? request : requestWrapper, response);
    }

}
