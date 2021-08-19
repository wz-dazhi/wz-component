package com.wz.push.bean.pushplus;

import com.wz.push.bean.AbstractPushResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: PushPlusResp
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PushPlusResp<T> extends AbstractPushResp {
    private T data;
    private Integer count;
}
