package com.wz.excel.export.handler;

import com.wz.excel.annotation.Export;
import com.wz.excel.exception.ExcelException;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.export
 * @className: ExportHandler
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
public interface ExportHandler {

    void export(Object data, MethodParameter parameter, Export export);

    /**
     * 是否属于单sheet
     */
    default boolean isSimpleSheet(MethodParameter p) {
        // 获取method 返回值List<T> 具体的泛型类型
        final Type type = p.getGenericParameterType();
        final Method m = p.getMethod();
        if (null != m && type instanceof ParameterizedType && List.class.isAssignableFrom(m.getReturnType())) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            final Type[] args = parameterizedType.getActualTypeArguments();
            // List<T> 中T(args[0])不属于泛型, T(args[0])属于具体的Class 类型
            return null != args && args.length > 0 && args[0] instanceof Class;
        }
        return false;
    }

    /**
     * 是否属于多sheet
     */
    default boolean isMultiSheet(MethodParameter p) {
        // 获取method 返回值List<List<T>> 具体的泛型类型
        final Type type = p.getGenericParameterType();
        final Method m = p.getMethod();
        if (null != m && type instanceof ParameterizedType && List.class.isAssignableFrom(m.getReturnType())) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            final Type[] args = parameterizedType.getActualTypeArguments();
            // List<List<T>> 中List<T>(args[0])属于List泛型, List<T>(args[0])继承于List接口, 并且 T 属于Class类型
            return null != args && args.length > 0 &&
                    args[0] instanceof ParameterizedType &&
                    List.class.isAssignableFrom((Class<?>) ((ParameterizedType) args[0]).getRawType()) &&
                    ((ParameterizedType) args[0]).getActualTypeArguments()[0] instanceof Class;
        }
        return false;
    }

    /**
     * 获取泛型具体的class类型
     */
    default Class<?> getDataClass(MethodParameter p) {
        final Type type = p.getGenericParameterType();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        final Type[] args = parameterizedType.getActualTypeArguments();
        final Type t = args[0];
        // 单sheet List<T> 获取T
        if (!(t instanceof ParameterizedType)) {
            return (Class<?>) t;
        } else {
            // 多sheet 同一个Bean的情况下 List<List<T>> 获取T
            // 注意: 只能获取同一个Bean的Class, 如果是不同的Bean, 但是泛型是Object, 那么 List<List<Object>> 获取的类型是 Object.class
            ParameterizedType tt = (ParameterizedType) t;
            if (tt.getActualTypeArguments()[0] instanceof Class) {
                return (Class<?>) tt.getActualTypeArguments()[0];
            }
            // List 多层嵌套, 直接抛异常
            throw new ExcelException("Excel导出不支持的数据类型: " + type);
        }
    }

}
