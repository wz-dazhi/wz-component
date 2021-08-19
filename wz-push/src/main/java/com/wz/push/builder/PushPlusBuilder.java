package com.wz.push.builder;

import com.wz.push.bean.pushplus.PushPlusReq;
import com.wz.push.enums.PushPlusTemplate;
import com.wz.push.enums.PushType;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @projectName: wz-component
 * @package: com.wz.push.builder
 * @className: PushPlusBuilder
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Accessors(chain = true, fluent = true)
public class PushPlusBuilder extends AbstractBuilder<PushPlusReq, PushPlusBuilder> {
    /**
     * 否	query	无	消息标题
     */
    private String title;
    /**
     * 是	query	无	具体消息内容，根据不同template支持不同格式
     */
    private String content;
    /**
     * 否	query	html	发送消息模板
     */
    private PushPlusTemplate template = PushPlusTemplate.html;
    /**
     * 否	query	wechat	发送渠道
     */
    private String channel = "wechat";
    /**
     * 否	query	无	webhook编码，仅在channel使用webhook渠道和CP渠道时需要填写
     */
    private String webhook;
    /**
     * 否	query	无	回调地址，异步回调发送结果
     */
    private String callbackUrl;

    public static PushPlusBuilder builder() {
        return new PushPlusBuilder();
    }

    @Override
    public PushPlusReq build() {
        final PushPlusReq msg = new PushPlusReq();
        msg.setTitle(title);
        msg.setContent(content);
        msg.setTemplate(template);
        msg.setChannel(channel);
        msg.setWebhook(webhook);
        msg.setCallbackUrl(callbackUrl);
        msg.setToken(token);
        msg.setType(PushType.PUSH_PLUS);

        return msg;
    }
}
