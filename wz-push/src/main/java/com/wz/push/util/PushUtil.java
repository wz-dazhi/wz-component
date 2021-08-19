package com.wz.push.util;
import com.wz.common.util.StringUtil;
import com.wz.push.bean.AbstractPushReq;
import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.bean.dingtalk.AbstractDingTalkReq;
import com.wz.push.bean.pushplus.PushPlusReq;
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
                if (!(s instanceof PushPlusReq)) {
                    throw CLASS_ARGUMENT_EXCEPTION;
                }
                return (Resp) PushPlusUtil.doGet((PushPlusReq) s);
            case DING_TALK:
                return (Resp) PushDingTalkUtil.pushSign((AbstractDingTalkReq) s);
            default:
                throw new IllegalArgumentException("pushType错误.");
        }
    }

    private static void verifyToken(AbstractPushTokenReq msg) {
        StringUtil.requireNonNull(msg.getToken(), "推送Token不能为空");
    }

    private static void verifyPushType(AbstractPushReq msg) {
        Objects.requireNonNull(msg, "消息不能为空");
        Objects.requireNonNull(msg.getType(), "消息类型不能为空");
    }

}
