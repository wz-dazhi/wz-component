package com.wz.push.builder;

import com.wz.push.bean.dingtalk.AbstractDingTalkReq;
import com.wz.push.bean.dingtalk.DingTalkActionCardReq;
import com.wz.push.bean.dingtalk.DingTalkAtReq;
import com.wz.push.bean.dingtalk.DingTalkFeedCardReq;
import com.wz.push.bean.dingtalk.DingTalkLinkReq;
import com.wz.push.bean.dingtalk.DingTalkMarkdownReq;
import com.wz.push.bean.dingtalk.DingTalkTextReq;
import com.wz.push.bean.dingtalk.DingTalkTextReq.Text;
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

    public static LinkBuilder linkBuilder() {
        return new LinkBuilder();
    }

    public static MarkdownBuilder markdownBuilder() {
        return new MarkdownBuilder();
    }

    public static ActionCardBuilder actionCardBuilder() {
        return new ActionCardBuilder();
    }

    public static FeedCardBuilder feedCardBuilder() {
        return new FeedCardBuilder();
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
            req.setToken(token);

            return req;
        }

    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class LinkBuilder extends SecretBuilder<DingTalkLinkReq, LinkBuilder> {
        private String title;
        private String text;
        private String messageUrl;
        private String picUrl;

        @Override
        public DingTalkLinkReq build() {
            final DingTalkLinkReq req = new DingTalkLinkReq();
            req.setSecret(secret);
            req.setToken(token);
            req.setLink(new DingTalkLinkReq.Link(title, text, messageUrl, picUrl));

            return req;
        }

    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class MarkdownBuilder extends SecretBuilder<DingTalkMarkdownReq, MarkdownBuilder> {
        private String title;
        private String text;
        private String[] atMobiles;
        private String[] atUserIds;
        private boolean isAtAll;

        @Override
        public DingTalkMarkdownReq build() {
            final DingTalkMarkdownReq req = new DingTalkMarkdownReq();
            req.setSecret(secret);
            req.setToken(token);
            req.setMarkdown(new DingTalkMarkdownReq.Markdown(title, text));
            req.setAt(new DingTalkAtReq(atMobiles, atUserIds, isAtAll));

            return req;
        }

    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class ActionCardBuilder extends SecretBuilder<DingTalkActionCardReq, ActionCardBuilder> {
        private String title;
        private String text;
        private String btnOrientation;
        private String singleTitle;
        private String singleUrl;
        private DingTalkActionCardReq.Btn[] btns;

        @Override
        public DingTalkActionCardReq build() {
            final DingTalkActionCardReq req = new DingTalkActionCardReq();
            req.setSecret(secret);
            req.setToken(token);
            req.setActionCard(new DingTalkActionCardReq.ActionCard(title, text, btnOrientation, singleTitle, singleUrl, btns));

            return req;
        }

    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class FeedCardBuilder extends SecretBuilder<DingTalkFeedCardReq, FeedCardBuilder> {
        private DingTalkFeedCardReq.Link[] links;

        @Override
        public DingTalkFeedCardReq build() {
            final DingTalkFeedCardReq req = new DingTalkFeedCardReq();
            req.setSecret(secret);
            req.setToken(token);
            req.setFeedCard(new DingTalkFeedCardReq.FeedCard(links));

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
