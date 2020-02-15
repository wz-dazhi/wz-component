package com.wz.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @projectName: pot-circle
 * @package: com.wz.potcircle.bean
 * @className: PageReq
 * @description:
 * @author: Zhi
 * @date: 2020-01-11 00:14
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePage extends BaseDto {
    private long current = 1;
    private long size = 10;

    @Override
    public String toString() {
        return super.toString();
    }
}
