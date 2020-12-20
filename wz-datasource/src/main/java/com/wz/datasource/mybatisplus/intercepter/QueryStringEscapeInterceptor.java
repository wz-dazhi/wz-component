package com.wz.datasource.mybatisplus.intercepter;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author wangzhi
 * @description: SQL拦截器. 处理like模糊查询特殊字符过滤: % _
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class QueryStringEscapeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 拦截sql
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        // 处理特殊字符
        modifyLikeSql(sql, parameterObject, boundSql);
        // 返回
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void modifyLikeSql(String sql, Object parameterObject, BoundSql boundSql) {
        if (!(parameterObject instanceof HashMap)) {
            return;
        }
        if (!sql.toLowerCase().contains(" like ") || !sql.toLowerCase().contains("?")) {
            return;
        }
        // 获取关键字的个数（去重）
        String[] strList = sql.split("\\?");
        Set<String> keyNames = new HashSet<>();
        for (int i = 0; i < strList.length; i++) {
            if (strList[i].toLowerCase().contains(" like ")) {
                String keyName = boundSql.getParameterMappings().get(i).getProperty();
                keyNames.add(keyName);
            }
        }
        // 对关键字进行特殊字符“清洗”，如果有特殊字符的，在特殊字符前添加转义字符（\）
        for (String keyName : keyNames) {
            HashMap<String, Object> parameter = (HashMap) parameterObject;
            this.setParam(parameter, keyName, sql);
        }
    }

    private String escapeChar(String before) {
        if (StringUtils.isNotBlank(before)) {
            before = before.replaceAll("\\\\", "\\\\\\\\");
            before = before.replaceAll("_", "\\\\_");
            before = before.replaceAll("%", "\\\\%");
        }
        return before;
    }

    private boolean isStringEscapeChar(Object value) {
        return value instanceof String && this.isContainsEscapeChar(value.toString());
    }

    private boolean isContainsEscapeChar(String param) {
        return null != param && (param.contains("_") || param.contains("\\") || param.contains("%"));
    }

    /**
     * 重新设置SQL参数
     */
    private void setParam(HashMap<String, Object> parameter, String name, String sql) {
        final MetaObject metaObject = SystemMetaObject.forObject(parameter);
        final Object value = metaObject.getValue(name);
        final boolean isConstructor = name.contains("ew.paramNameValuePairs.");
        final boolean isNotConstructor = !name.contains("ew.paramNameValuePairs.");
        if (isStringEscapeChar(value)) {
            // 第一种情况：在业务层进行条件构造产生的模糊查询关键字
            // 第二种情况：未使用条件构造器，但是在service层进行了查询关键字与模糊查询符`%`手动拼接
            String v = value.toString();
            if ((isConstructor || isNotConstructor) && sql.toLowerCase().contains(" like ?")) {
                // 截取 %% 中间的值,进行处理后再拼接上
                v = v.substring(1, v.length() - 1);
                metaObject.setValue(name, "%" + escapeChar(v) + "%");
            } else {
                // 第三种情况：在Mapper类的注解SQL or Mapper.xml中进行了模糊查询的拼接
                metaObject.setValue(name, escapeChar(v));
            }
        }
    }

}