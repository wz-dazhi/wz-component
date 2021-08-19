package com.wz.push.bean;

import com.wz.push.enums.PushType;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: AbstractPushReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@Data
public abstract class AbstractPushReq implements Serializable {
    private PushType type;
}
