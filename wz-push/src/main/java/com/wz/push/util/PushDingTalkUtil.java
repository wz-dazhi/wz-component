package com.wz.push.util;

import com.wz.common.exception.ExceptionUtil;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.StringUtil;
import com.wz.push.bean.dingtalk.AbstractDingTalkReq;
import com.wz.push.bean.dingtalk.DingTalkResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.wz.push.constant.PushConst.REST;

/**
 * @projectName: wz-component
 * @package: com.wz.push.util
 * @className: PushDingTalkUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@Slf4j
public final class PushDingTalkUtil {

    private PushDingTalkUtil() {
    }

    /**
     * 接口文档: https://developers.dingtalk.com/document/robots/custom-robot-access
     */
    private static final String BASE_URL = "https://oapi.dingtalk.com";
    private static final String SEND_URI = BASE_URL + "/robot/send?access_token=%s";
    private static final String SEND_SIGN_URI = SEND_URI + "&timestamp=%s&sign=%s";

    public static <Req extends AbstractDingTalkReq> DingTalkResp pushSign(Req req) {
        final String token = req.getToken();
        final String secret = req.getSecret();
        StringUtil.requireNonNull(secret, "密钥不能为空");
        Long timestamp = System.currentTimeMillis();
        final String url = String.format(SEND_SIGN_URI, token, timestamp, sign(secret, timestamp));
        return doPush(url, req);
    }

    public static <Req extends AbstractDingTalkReq> DingTalkResp push(Req req) {
        // 不加签
        if (StringUtil.isBlank(req.getSecret())) {
            final String token = req.getToken();
            final String url = String.format(SEND_URI, token);
            return doPush(url, req);
        }
        // 加签
        return pushSign(req);
    }

    public static <Req extends AbstractDingTalkReq> DingTalkResp doPush(String url, Req req) {
        try {
            //verifyInstance(req);
            final String res = REST.post(url, req, String.class);
            return JsonUtil.toBean(res, DingTalkResp.class);
        } catch (Exception e) {
            log.error(">>> 调用钉钉机器人通知接口发生异常. req: {}, errMsg: {}", req, e.getMessage());
            throw ExceptionUtil.wrap(e);
        }
    }

    private static String sign(String secret, Long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(">>> 调用钉钉机器人通知接口拼装sign签名发生异常. " +
                    "\n secret: {}, timestamp: {}, errMsg: {}", secret, timestamp, e.getMessage());
            throw ExceptionUtil.wrap(e);
        }
    }

//    private static <Req extends AbstractDingTalkReq> void verifyInstance(Req r) {
//        if (r instanceof DingTalkTextReq) {
//            DingTalkTextReq t = (DingTalkTextReq) r;
//            StringUtil.requireNonNull(t.getText().getContent(), "消息内容不能为空.");
//        } else if (r instanceof DingTalkLinkReq) {
//            DingTalkLinkReq l = (DingTalkLinkReq) r;
//            final DingTalkLinkReq.Link link = l.getLink();
//            StringUtil.requireNonNull(link.getTitle(), "消息标题不能为空.");
//            StringUtil.requireNonNull(link.getText(), "消息内容不能为空.");
//            StringUtil.requireNonNull(link.getMessageUrl(), "点击消息跳转的URL不能为空.");
//        }
//    }

}
