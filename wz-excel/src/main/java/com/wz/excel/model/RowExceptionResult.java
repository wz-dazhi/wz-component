package com.wz.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.model
 * @className: RowExceptionResult
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowExceptionResult {
    private Integer sheetNo;
    private Integer rowIndex;
    private Exception exception;
}
