package com.wz.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.wz.common.enums.IEnum;
import com.wz.excel.annotation.ExcelEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.converter
 * @className: EnumConverter
 * @description:
 * @author: zhi
 * @date: 2021/12/6
 * @version: 1.0
 */
public class EnumConverter implements Converter<Object> {

    @Override
    public Class<Object> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Object convertToJavaData(ReadCellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String excelValue = cellData.getStringValue();
        return this.process(excelContentProperty, (codeMethod, descMethod, enumConsts) -> {
            if (descMethod.invoke(enumConsts).equals(excelValue)) {
                return codeMethod.invoke(enumConsts);
            }
            return null;
        });
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object data, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String value = this.process(excelContentProperty, (codeMethod, descMethod, enumConsts) -> {
            if (codeMethod.invoke(enumConsts).equals(data)) {
                return String.valueOf(descMethod.invoke(enumConsts));
            }
            return null;
        });
        return new WriteCellData<>(value != null ? value : "");
    }

    private <T> T process(ExcelContentProperty excelContentProperty, ProcessFunction<Method, Method, Object, T> pf) throws Exception {
        Field field = excelContentProperty.getField();
        ExcelEnum excelEnum = field.getDeclaredAnnotation(ExcelEnum.class);
        Objects.requireNonNull(excelEnum, "Current field " + field.getName() + " cannot find @ExcelEnum");
        Class<? extends IEnum<?, ?>> clazz = excelEnum.enumClass();
        Object[] enumConstants = clazz.getEnumConstants();
        Method codeMethod = clazz.getMethod("code");
        Method descMethod = clazz.getMethod("desc");
        for (Object enumConstant : enumConstants) {
            T t = pf.process(codeMethod, descMethod, enumConstant);
            if (null != t) {
                return t;
            }
        }
        return null;
    }

    @FunctionalInterface
    private interface ProcessFunction<CodeMethod, DescMethod, EnumConsts, R> {

        R process(CodeMethod codeMethod, DescMethod descMethod, EnumConsts enumConsts) throws Exception;
    }
}
