package com.wz.push.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: AbstractPushTokenReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public abstract class AbstractPushTokenReq extends AbstractPushReq {
    private String token;
}
