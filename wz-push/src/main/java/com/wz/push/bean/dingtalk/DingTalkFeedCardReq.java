package com.wz.push.bean.dingtalk;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @className: DingTalkFeedCardReq
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkFeedCardReq extends BaseDingTalkReq {
    private FeedCard feedCard;

    public DingTalkFeedCardReq() {
        super();
        setMsgType(DingTalkMsgType.feedCard);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class FeedCard implements Serializable {
        private Link[] links;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Link implements Serializable {
        private String title;
        @JsonProperty("messageURL")
        private String messageUrl;
        @JsonProperty("picURL")
        private String picUrl;
    }
}
