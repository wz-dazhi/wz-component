package com.wz.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.common.model
 * @className: JwtData
 * @description:
 * @author: zhi
 * @date: 2021/3/3
 * @version: 1.0
 */
@Data
public class JwtData<T> implements Serializable {
    /**
     * jwt验证结果
     */
    private boolean success = false;

    /**
     * jwt中的数据
     */
    private T data;
}
