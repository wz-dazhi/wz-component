package com.wz.push.builder;

import com.wz.push.bean.dingtalk.AbstractDingTalkReq;
import com.wz.push.bean.dingtalk.DingTalkAtReq;
import com.wz.push.bean.dingtalk.DingTalkTextReq;
import com.wz.push.bean.dingtalk.DingTalkTextReq.Text;
import com.wz.push.enums.DingTalkMsgType;
import com.wz.push.enums.PushType;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @projectName: wz-component
 * @package: com.wz.push.builder
 * @className: DingTalkBuilder
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
public class DingTalkBuilder {

    public static TextBuilder textBuilder() {
        return new TextBuilder();
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class TextBuilder extends SecretBuilder<DingTalkTextReq, TextBuilder> {
        private String content;
        private String[] atMobiles;
        private String[] atUserIds;
        private boolean isAtAll;

        @Override
        public DingTalkTextReq build() {
            final DingTalkTextReq req = new DingTalkTextReq();
            req.setSecret(secret);
            req.setText(new Text(content));
            req.setAt(new DingTalkAtReq(atMobiles, atUserIds, isAtAll));
            req.setMsgType(DingTalkMsgType.text);
            req.setToken(token);
            req.setType(PushType.DING_TALK);

            return req;
        }

    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    private abstract static class SecretBuilder<Req extends AbstractDingTalkReq, Builder extends SecretBuilder> extends AbstractBuilder<Req, Builder> {
        protected String secret;

        public Builder secret(String secret) {
            this.secret = secret;
            return (Builder) this;
        }

        public String secret() {
            return secret;
        }
    }

}
