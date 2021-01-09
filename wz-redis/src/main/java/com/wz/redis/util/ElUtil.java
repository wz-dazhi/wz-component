package com.wz.redis.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @projectName: wz-component
 * @package: com.wz.redis.util
 * @className: ElUtil
 * @description:
 * @author: zhi
 * @date: 2020/12/31 上午10:17
 * @version: 1.0
 */
public final class ElUtil {
    private ElUtil() {
    }

    public static EvaluationContext setVariable(Method m, Object[] args) {
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        EvaluationContext context = new StandardEvaluationContext();

        String[] params = discoverer.getParameterNames(m);
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }

}
