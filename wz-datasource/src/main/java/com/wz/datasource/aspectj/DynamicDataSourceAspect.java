package com.wz.datasource.aspectj;

import com.wz.datasource.annotation.Master;
import com.wz.datasource.config.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @projectName: wz
 * @package: com.wz.datasource.aspectj
 * @className: DynamicDataSourceAspect
 * @description: 动态切换数据源AOP
 * @author: Zhi Wang
 * @date: 2019/2/23 5:42 PM
 * @version: 1.0
 **/
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 查询方法前缀
     */
    private final String[] QUERY_PREFIX = {"select", "find", "get", "load", "query"};

    @Pointcut("execution( * com.*.mapper.*.*(..) ) || " +
            "execution( * com.*.*.mapper.*.*(..) ) || " +
            "execution( * com.*.*.*.mapper.*.*(..) )")
    private void mapperAspect() {
    }

    /**
     * 拦截mapper接口
     */
    @Before("mapperAspect()")
    public void switchDataSource(JoinPoint point) {
        MethodSignature sign = (MethodSignature) point.getSignature();
        Method m = sign.getMethod();
        Master master = m.getAnnotation(Master.class);
        if (master == null) {
            if (isQueryMethod(point.getSignature().getName())) {
                DbContextHolder.useSlaveDataSource();
            }
        } else {
            DbContextHolder.useMasterDataSource();
        }
        log.debug("Switch DataSource to [{}] in Method [{}]", DbContextHolder.getDataSourceKey(), point.getSignature());
    }

    /**
     * 清除当前线程数据源
     */
    @After("mapperAspect())")
    public void restoreDataSource(JoinPoint point) {
        DbContextHolder.clearDataSourceKey();
        log.debug("Restore DataSource to [{}] in Method [{}]", DbContextHolder.getDataSourceKey(), point.getSignature());
    }

    /**
     * 判断该方法是否属于查询方法
     */
    private boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
