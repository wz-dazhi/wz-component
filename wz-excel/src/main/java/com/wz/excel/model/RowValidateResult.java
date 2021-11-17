package com.wz.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.model
 * @className: RowValidateResult
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowValidateResult {
    private Integer sheetNo;
    private Integer rowIndex;
    private String classSimpleName;
    private List<ValidateField> fields;

    @Data
    @AllArgsConstructor
    public static class ValidateField {
        private String field;
        private String message;
    }
}
