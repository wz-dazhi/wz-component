package com.wz.common.model;

import com.wz.common.util.JsonUtil;

import java.io.Serializable;

/**
 * @projectName: pot-circle
 * @package: com.wz.potcircle.dto
 * @className: BaseDto
 * @description:
 * @author: Zhi
 * @date: 2019-12-08 13:58
 * @version: 1.0
 */
public class BaseDto implements Serializable {
    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
