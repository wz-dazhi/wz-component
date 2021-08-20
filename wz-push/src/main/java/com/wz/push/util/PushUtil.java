package com.wz.push.util;

import com.wz.common.util.StringUtil;
import com.wz.mail.util.EmailUtil;
import com.wz.push.bean.AbstractPushReq;
import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.bean.PushSuccessResp;
import com.wz.push.bean.dingtalk.BaseDingTalkReq;
import com.wz.push.bean.dingtalk.DingTalkResp;
import com.wz.push.bean.email.EmailReq;
import com.wz.push.bean.pushplus.PushPlusReq;
import com.wz.push.bean.pushplus.PushPlusResp;
import com.wz.push.enums.PushType;

import java.util.Objects;

/**
 * @projectName: wz-component
 * @package: com.wz.push.util
 * @className: PushUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
public final class PushUtil {
    private PushUtil() {
    }

    private static final IllegalArgumentException CLASS_ARGUMENT_EXCEPTION = new IllegalArgumentException("class类型错误.");

    public static <Req extends AbstractPushReq, Resp extends AbstractPushResp> Resp push(Req s) {
        verifyPushType(s);
        if (s instanceof AbstractPushTokenReq) {
            verifyToken((AbstractPushTokenReq) s);
        }
        final PushType type = s.getType();
        switch (type) {
            case PUSH_PLUS:
                return (Resp) doPushPlus(s);
            case DING_TALK:
                return (Resp) doDingTalk(s);
            case EMAIL:
                return (Resp) doEmail(s);
            default:
                throw new IllegalArgumentException("pushType错误.");
        }
    }

    private static <Req extends AbstractPushReq, T> PushPlusResp<T> doPushPlus(Req s) {
        if (!(s instanceof PushPlusReq)) {
            throw CLASS_ARGUMENT_EXCEPTION;
        }
        return PushPlusUtil.doGet((PushPlusReq) s);
    }

    private static <Req extends AbstractPushReq> DingTalkResp doDingTalk(Req s) {
        if (!(s instanceof BaseDingTalkReq)) {
            throw CLASS_ARGUMENT_EXCEPTION;
        }
        return PushDingTalkUtil.push((BaseDingTalkReq) s);
    }

    private static <Req extends AbstractPushReq> PushSuccessResp doEmail(Req s) {
        if (!(s instanceof EmailReq)) {
            throw CLASS_ARGUMENT_EXCEPTION;
        }
        EmailReq r = (EmailReq) s;
        EmailUtil.send(r.getMsg(), r.isHtml());
        return PushSuccessResp.SUCCESS;
    }

    private static void verifyToken(AbstractPushTokenReq msg) {
        StringUtil.requireNonNull(msg.getToken(), "推送Token不能为空");
    }

    private static void verifyPushType(AbstractPushReq msg) {
        Objects.requireNonNull(msg, "消息不能为空");
        Objects.requireNonNull(msg.getType(), "消息类型不能为空");
    }

}
