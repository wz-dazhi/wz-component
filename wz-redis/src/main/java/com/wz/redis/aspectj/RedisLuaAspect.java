package com.wz.redis.aspectj;

import com.google.common.base.Stopwatch;
import com.wz.common.util.ResultUtil;
import com.wz.redis.annotation.LuaLock;
import com.wz.redis.util.RedisLuaLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static com.wz.common.enums.ResultEnum.REQUEST_ERROR;

/**
 * @projectName: wz-component
 * @package: com.wz.redis.aspectj
 * @className: RedisLuaAspect
 * @description:
 * @author: Zhi
 * @date: 2020-05-18 17:39
 * @version: 1.0
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class RedisLuaAspect {
    private final RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.wz.redis.annotation.LuaLock)")
    private void lockMethod() {
    }

    @Around("lockMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 方法签名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method m = signature.getMethod();
        Object[] args = pjp.getArgs();
        LuaLock l = m.getAnnotation(LuaLock.class);
        // el表达式解析
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        EvaluationContext context = new StandardEvaluationContext();

        String[] params = discoverer.getParameterNames(m);
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        String lockKey = parser.parseExpression(l.key()).getValue(context, String.class);
        String lockValue = parser.parseExpression(l.requestId()).getValue(context, String.class);

        Stopwatch sw = Stopwatch.createStarted();
        // 开始抢锁
        long waitTime = System.currentTimeMillis() + l.waitTime() * 1000;
        RedisLuaLock lock = new RedisLuaLock(redisTemplate, lockKey, lockValue, l.expire(), l.unit());
        log.debug(">>> 方法[{}]开始自动抢锁, 等待时间: [{}]s, 相关参数设置: {}", m.getName(), l.waitTime(), lock);
        try {
            do {
                if (lock.lock()) {
                    log.info("抢锁成功, key: [{}]. value: [{}]", lockKey, lockValue);
                    return pjp.proceed();
                }
                Thread.sleep(Math.min(200, l.waitTime() * 100));
            } while (System.currentTimeMillis() <= waitTime);
            log.debug("抢锁失败, key: [{}]. value: [{}]", lockKey, lockValue);
        } finally {
            log.info(">>> 本次抢锁消耗时间: [{}]ms. key: [{}]. value: [{}]", sw.stop().elapsed(TimeUnit.MILLISECONDS), lockKey, lockValue);
            lock.unLock();
        }
        return ResultUtil.fail(REQUEST_ERROR);
    }

}