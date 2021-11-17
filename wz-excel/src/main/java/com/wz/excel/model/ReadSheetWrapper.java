package com.wz.excel.model;

import com.alibaba.excel.read.listener.ReadListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.model
 * @className: ReadSheetWrapper
 * @description:
 * @author: zhi
 * @date: 2021/11/17
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadSheetWrapper<T> {
    private Integer sheetNo;
    private Class<T> head;
    private ReadListener<T> readListener;
}
