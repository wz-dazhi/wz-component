package com.wz.push.bean.pushplus;

import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.enums.PushPlusTemplate;
import com.wz.push.enums.PushType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: PushPlusReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class PushPlusReq extends AbstractPushTokenReq {
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

    public PushPlusReq(String token) {
        setType(PushType.PUSH_PLUS);
        setToken(token);
    }

}
