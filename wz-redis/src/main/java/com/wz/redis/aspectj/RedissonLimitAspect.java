package com.wz.redis.aspectj;

import com.google.common.base.Stopwatch;
import com.wz.redis.annotation.RedissonLimit;
import com.wz.redis.util.ElUtil;
import com.wz.swagger.util.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.wz.common.enums.ResultEnum.REQUEST_LIMIT;

/**
 * @projectName: wz-component
 * @package: com.wz.redis.aspectj
 * @className: RedissonLimitAspect
 * @description:
 * @author: Zhi
 * @date: 2020-05-18 17:39
 * @version: 1.0
 */
@Slf4j
@Aspect
@Component
public class RedissonLimitAspect {

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.wz.redis.annotation.RedissonLimit)")
    private void lockMethod() {
    }

    @Around("lockMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (Objects.isNull(redissonClient)) {
            return pjp.proceed();
        }
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method m = signature.getMethod();
        Object[] args = pjp.getArgs();
        final EvaluationContext context = ElUtil.setVariable(m, args);
        RedissonLimit l = m.getAnnotation(RedissonLimit.class);
        // el表达式解析
        ExpressionParser parser = new SpelExpressionParser();
        String lockKey = parser.parseExpression(l.key()).getValue(context, String.class);

        Stopwatch sw = Stopwatch.createStarted();
        // 开始抢锁
        final RLock lock = redissonClient.getLock(lockKey);
        log.info("RLock-防刷>>> 方法[{}]开始自动抢锁, lockKey: [{}], 等待时间: [{}], 过期时间: [{}] - [{}]", m.getName(), lockKey, l.waitTime(), l.expire(), l.unit());
        try {
            // 直接lock, 获取不到一直等待
            if (l.waitTime() == -1) {
                lock.lock();
                log.debug("RLock-防刷>>> 抢锁成功, lockKey: [{}].", lockKey);
                return pjp.proceed();
            } else {
                // 尝试获取锁
                if (lock.tryLock(l.waitTime(), l.expire(), l.unit())) {
                    log.debug("RLock-防刷>>> 抢锁成功, lockKey: [{}].", lockKey);
                    return pjp.proceed();
                }
                log.warn("RLock-防刷>>> 抢锁失败, lockKey: [{}].", lockKey);
            }
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
            log.info("RLock-防刷>>> 本次抢锁消耗时间: [{}]ms. key: [{}].", sw.stop().elapsed(TimeUnit.MILLISECONDS), lockKey);
        }
        return R.fail(REQUEST_LIMIT);
    }

}