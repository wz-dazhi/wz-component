package com.wz.webmvc.filter;

import org.springframework.http.HttpMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @projectName: wz-component
 * @package: com.wz.web.filter
 * @className: SwaggerFilter
 * @description:
 * @author: zhi
 * @date: 2022/9/28
 * @version: 1.0
 */
public class SwaggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            final HttpServletRequest req = (HttpServletRequest) request;
            if (HttpMethod.GET.matches(req.getMethod())) {
                String uri = req.getRequestURI();
                if ("/doc.html".equals(uri)) {
                    final HttpServletResponse resp = (HttpServletResponse) response;
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

}
