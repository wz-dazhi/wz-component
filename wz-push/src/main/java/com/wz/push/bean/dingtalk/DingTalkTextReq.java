package com.wz.push.bean.dingtalk;

import com.wz.push.enums.DingTalkMsgType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.dingtalk
 * @className: DingTalkTextReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkTextReq extends BaseDingTalkReq {
    private Text text;
    private DingTalkAtReq at;

    public DingTalkTextReq() {
        super();
        setMsgType(DingTalkMsgType.text);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Text implements Serializable {
        private String content;
    }
}
