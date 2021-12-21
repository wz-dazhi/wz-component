package com.wz.push.util;

import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.bean.dingtalk.DingTalkResp;
import com.wz.push.builder.DingTalkBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PushUtilTest {
    private AbstractPushTokenReq msg;
    private AbstractPushResp resp;

    @BeforeEach
    public void before() {
//        String token = "ccccccccb6a74977xxxxxxxxxxxxxxxxxxx";
//        String title = "测试";
//        String content = "<h1>异常</h1>" +
//                "<p>程序发生了异常.</p>";
//
//        // pushplus+
//        msg = PushPlusBuilder.builder()
//                .token(token)
//                .title(title)
//                .content(content)
//                .build();

        // dingtalk
        String token = "d4xxxxxxxxxxxxxx584bdbf7c05c02e3278742xxxxxxxxxxxxxxxxxxxxxxx";
        //String secret = "SECxxxxxxxxxxxxxxd0abbe1878494c61facb8xxxxxxxxxxxxxxxxxxxxxxx";
        String content = "内容测试";

        msg = DingTalkBuilder.textBuilder()
                //.secret(secret)
                .token(token)
                .content(content)
                .build();
    }

    @Test
    public void push() {
        resp = PushUtil.push(msg);
    }

    @AfterEach
    public void after() {
//        PushPlusResp<String> r = (PushPlusResp) resp;
//        System.out.println(r);

        DingTalkResp r = (DingTalkResp) resp;
        System.out.println(r);
    }
}