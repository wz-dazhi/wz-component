package com.wz.web.aspectj;

import com.google.common.base.Stopwatch;
import com.wz.common.util.IpUtil;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.UUIDUtil;
import com.wz.web.util.WebContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz-web
 * @package: com.wz.web.aspectj
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

//    private static final String LOG_EXECUTION = "execution( * com..controller..*.*(..) ) " +
//            "|| execution( * com..*.controller..*.*(..) ) " +
//            "|| execution( * com..*.*.controller..*.*(..) ) " +
//            "|| execution( * com..*.*.*.controller..*.*(..) ) " +
//            "|| execution( * com..*.*.*.*.controller..*.*(..) ) " +
//            "|| execution( * cn..controller..*.*(..) ) " +
//            "|| execution( * cn..*.controller..*.*(..) ) " +
//            "|| execution( * cn..*.*.controller..*.*(..) ) " +
//            "|| execution( * cn..*.*.*.controller..*.*(..) ) " +
//            "|| execution( * cn..*.*.*.*.controller..*.*(..) )";

    //@Pointcut(LOG_EXECUTION)
    @Pointcut("execution( * com..controller..*.*(..) ) || execution( * cn..controller..*.*(..) ) ")
    private void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest req = WebContextUtil.getRequest();
        String uri = req.getRequestURI();
        Stopwatch sw = Stopwatch.createStarted();
        MDC.put("linkId", UUIDUtil.getUUIDLowerCase());
        Object[] args = point.getArgs();
        log.info("Request method: [{}], Uri: [{}], Args: {}, Signature: {} ", req.getMethod(), uri, JsonUtil.toJsonString(args), point.getSignature().toShortString());
        try {
            MDC.put("clientIp", IpUtil.getIp(req));
            MDC.put("serverIp", InetAddress.getLocalHost().getHostAddress());
            MDC.put("api", req.getRequestURL().toString());
            Object r = point.proceed();
            log.info("Uri: [{}], Return: {} ", uri, JsonUtil.toJsonString(r));
            return r;
        } finally {
            log.info("Uri: [{}], Time consuming: [{}]ms", uri, sw.stop().elapsed(TimeUnit.MILLISECONDS));
            MDC.clear();
        }
    }

}
