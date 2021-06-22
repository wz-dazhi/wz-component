package com.wz.excel.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;
import lombok.AllArgsConstructor;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.enums
 * @className: ExcelEnum
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
@AllArgsConstructor
public enum ExcelEnum implements IErrorCode {

    /**
     * Excel异常
     */
    EXPORT_ERROR("E0001"),

    /**
     * 导出sheet为空
     */
    EXPORT_SHEET_NULL("E0002"),

    ;

    private final String code;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return Resources.getMessage("i18n/excel/excel", this.code);
    }
}
