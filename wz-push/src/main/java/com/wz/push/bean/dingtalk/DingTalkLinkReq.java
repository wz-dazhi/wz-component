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
 * @className: DingTalkLinkReq
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkLinkReq extends BaseDingTalkReq {
    private Link link;

    public DingTalkLinkReq() {
        super();
        setMsgType(DingTalkMsgType.link);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Link implements Serializable {
        private String title;
        private String text;
        private String messageUrl;
        private String picUrl;
    }
}
