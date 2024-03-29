package com.wz.webmvc.aspectj;

import com.google.common.base.Stopwatch;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.UUIDUtil;
import com.wz.swagger.util.LocalIpUtil;
import com.wz.webmvc.util.IpUtil;
import com.wz.webmvc.util.WebContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz-web
 * @package: com.wz.webmvc.aspectj
 * @className: LogAspect
 * @description: 日志切面
 * @author: Zhi
 * @date: 2019-09-18 10:24
 * @version: 1.0
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution( * com..controller..*.*(..) ) || execution( * cn..controller..*.*(..) ) ")
    private void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Stopwatch sw = Stopwatch.createStarted();
        HttpServletRequest req = WebContextUtil.getRequest();
        MDC.put("linkId", UUIDUtil.getLowerCase());
        MDC.put("clientIp", IpUtil.getIp());
        MDC.put("serverIp", LocalIpUtil.localIpv4Address(InetAddress.getLocalHost().getHostAddress()));
        MDC.put("api", req.getRequestURL().toString());
        String uri = req.getRequestURI();
        Object[] args = this.args(point.getArgs());
        final boolean infoEnabled = log.isInfoEnabled();
        if (infoEnabled) {
            log.info("Request method: [{}], Uri: [{}], Args: {}, Signature: {} ", req.getMethod(), uri, JsonUtil.toJson(args), point.getSignature().toShortString());
        }
        try {
            Object r = point.proceed();
            if (null != r && infoEnabled) {
                // r 如果是集合类型, 并且数据超过100个. 则不打印日志
                boolean isCollection = Collection.class.isAssignableFrom(r.getClass()) && ((Collection<?>) r).size() > 100;
                if (isCollection) {
                    log.info("Uri: [{}], size: [{}]. Return data more than 100, Not print data log. ", uri, ((Collection<?>) r).size());
                } else {
                    log.info("Uri: [{}], Return: {} ", uri, JsonUtil.toJson(r));
                }
            }
            return r;
        } finally {
            if (infoEnabled) {
                log.info("Uri: [{}], Time consuming: [{}]ms", uri, sw.stop().elapsed(TimeUnit.MILLISECONDS));
            }
            MDC.clear();
        }
    }

    private Object[] args(Object[] args) {
        Object[] returnArgs = new Object[args.length];
        int index = -1;
        for (Object a : args) {
            if (a instanceof ServletRequest || a instanceof ServletResponse || a instanceof MultipartFile) {
                continue;
            }
            returnArgs[++index] = a;
        }
        return returnArgs;
    }

}
