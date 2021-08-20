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
 * @className: DingTalkActionCardReq
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkActionCardReq extends AbstractDingTalkReq {
    private ActionCard actionCard;

    public DingTalkActionCardReq() {
        super();
        setMsgType(DingTalkMsgType.actionCard);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ActionCard implements Serializable {
        private String title;
        private String text;
        private String btnOrientation;
        /**
         * 整体跳转需要的参数-------------设置singleURL后btns无效
         */
        private String singleTitle;
        @JsonProperty("singleURL")
        private String singleUrl;
        /**
         * 独立跳转需要的参数--------------
         */
        private Btn[] btns;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Btn implements Serializable {
        private String title;
        @JsonProperty("actionURL")
        private String actionUrl;
    }
}
