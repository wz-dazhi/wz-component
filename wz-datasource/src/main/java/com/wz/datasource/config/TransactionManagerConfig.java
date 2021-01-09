package com.wz.datasource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @projectName: wz-component
 * @package: com.wz.datasource.config
 * @className: TransactionManagerConfig
 * @description: 注解和配置同时生效, 注解优先级高于配置
 * @author: zhi
 * @date: 2020/12/30 下午2:18
 * @version: 1.0
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    //@Value("#{ @environment['transaction.manager.expression'] != null && @environment['transaction.manager.expression'] != '' ? @environment['transaction.manager.expression'] : 'execution( * com..bo..*BOImpl.*(..) )'}")
    //private String expression;

    @Bean
    @Primary
    @Resource
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

//    @Bean
//    public TransactionInterceptor txAdvice(PlatformTransactionManager transactionManager) {
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//        // 只读事务，不做更新操作
//        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
//        readOnlyTx.setReadOnly(true);
//        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
//        // 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
//        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
//        requiredTx.setTimeout(5);
//
//        Map<String, TransactionAttribute> txMap = new HashMap<>();
//        // 新增
//        txMap.put("add*", requiredTx);
//        txMap.put("save*", requiredTx);
//        txMap.put("insert*", requiredTx);
//        // 修改
//        txMap.put("update*", requiredTx);
//        txMap.put("modify*", requiredTx);
//        // 删除
//        txMap.put("delete*", requiredTx);
//        txMap.put("remove*", requiredTx);
//        // 查
//        txMap.put("select*", readOnlyTx);
//        txMap.put("find*", readOnlyTx);
//        txMap.put("get*", readOnlyTx);
//        txMap.put("load*", readOnlyTx);
//        txMap.put("query*", readOnlyTx);
//        txMap.put("list*", readOnlyTx);
//        txMap.put("*", readOnlyTx);
//        source.setNameMap(txMap);
//        return new TransactionInterceptor(transactionManager, source);
//    }
//
//    /**
//     * 切面拦截规则 参数会自动从容器中注入
//     */
//    @Bean
//    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice) {
//        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
//        pointcutAdvisor.setAdvice(txAdvice);
//        pointcutAdvisor.setOrder(0);
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(expression);
//        pointcutAdvisor.setPointcut(pointcut);
//        return pointcutAdvisor;
//    }

}
