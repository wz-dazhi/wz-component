package com.wz.push.bean.dingtalk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.enums.DingTalkMsgType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.dingtalk
 * @className: AbstractDingTalkReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public abstract class AbstractDingTalkReq extends AbstractPushTokenReq {
    @JsonProperty("msgtype")
    private DingTalkMsgType msgType;
    private String secret;
}
