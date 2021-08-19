package com.wz.push.util;

import com.wz.push.bean.pushplus.PushPlusReq;
import com.wz.push.bean.pushplus.PushPlusResp;
import com.wz.push.enums.PushPlusTemplate;

import static com.wz.push.constant.PushConst.REST;

/**
 * @className: PushPlusUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/10
 * @version: 1.0
 */
public final class PushPlusUtil {
    private PushPlusUtil() {
    }

    private static final String BASE_URL = "http://www.pushplus.plus";
    private static final String SEND_URI = BASE_URL + "/send";
    private static final String SEND_JSON_URI = "?token=%s&title=%s&content=%s&template=json&channel=%s&webhook=%s&callbackUrl=%s";

    public static final String SUCCESS_CODE = "200";

    public static <T> T get(String token, String title, String content) {
        final PushPlusResp<Object> res = doGet(token, title, content, null);
        return (T) res.getData();
    }

    public static <T> T get(String token, String title, String content, PushPlusTemplate template) {
        final PushPlusResp<Object> res = doGet(token, title, content, template);
        return (T) res.getData();
    }

    public static <T> PushPlusResp<T> doGet(String token, String title, String content, PushPlusTemplate template) {
        return doGet(pushPlusMsg(token, title, content, template));
    }

    private static PushPlusReq pushPlusMsg(String token, String title, String content, PushPlusTemplate template) {
        final PushPlusReq msg = new PushPlusReq();
        msg.setToken(token);
        msg.setTitle(title);
        msg.setContent(content);
        if (template != null) {
            msg.setTemplate(template);
        }
        return msg;
    }

    public static <T> PushPlusResp<T> doGet(final PushPlusReq req) {
        PushPlusResp<T> PushPlusResp;
        if (PushPlusTemplate.json != req.getTemplate()) {
            PushPlusResp = REST.get(SEND_URI, req, PushPlusResp.class);
        } else {
            final String params = String.format(SEND_JSON_URI, req.getToken(), req.getTitle(), "{json}", req.getChannel(), req.getWebhook(), req.getCallbackUrl());
            final String url = SEND_URI + params;
            PushPlusResp = REST.getVariables(url, PushPlusResp.class, req.getContent());
        }

        return PushPlusResp;
    }

    public static <T> T post(String token, String title, String content) {
        final PushPlusResp<Object> res = doPost(token, title, content, null);
        return (T) res.getData();
    }

    public static <T> T post(String token, String title, String content, PushPlusTemplate template) {
        final PushPlusResp<Object> res = doPost(token, title, content, template);
        return (T) res.getData();
    }

    public static <T> PushPlusResp<T> doPost(String token, String title, String content, PushPlusTemplate template) {
        return doPost(pushPlusMsg(token, title, content, template));
    }

    public static <T> PushPlusResp<T> doPost(final PushPlusReq req) {
        return REST.post(SEND_URI, req, PushPlusResp.class);
    }

}
