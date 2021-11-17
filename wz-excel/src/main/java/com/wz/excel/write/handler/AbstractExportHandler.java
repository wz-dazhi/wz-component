package com.wz.excel.export.handler;

import com.wz.excel.annotation.Export;
import com.wz.excel.exception.ExcelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.export
 * @className: AbstractExportHandler
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
public abstract class AbstractExportHandler implements ExportHandler {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void export(Object data, MethodParameter parameter, Export export) {
        if (null == data || !List.class.isAssignableFrom(data.getClass())) {
            throw new ExcelException("当前导出数据格式不支持. ");
        }
        // List<T> 单sheet
        // List<List<T>> 多sheet, 每个sheet对应一个List<T>
        // 不属于单List<T> 也不属于 List<List<T>>
        if (!isSimpleSheet(parameter) && !isMultiSheet(parameter)) {
            throw new ExcelException("当前导出数据格式不支持. " + parameter.getGenericParameterType());
        }
        if (null == export.sheet() || export.sheet().length == 0) {
            throw new ExcelException("导出sheet页不能为空.");
        }
        this.doExport((List) data, parameter, export);
    }

    /**
     * 子类实现导出
     *
     * @param data      数据
     * @param parameter MethodParameter
     * @param export    自定义注解
     */
    protected abstract void doExport(List data, MethodParameter parameter, Export export);
}
