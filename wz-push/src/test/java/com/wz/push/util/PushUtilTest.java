package com.wz.push.util;

import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.AbstractPushTokenReq;
import com.wz.push.bean.dingtalk.DingTalkResp;
import com.wz.push.builder.DingTalkBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushUtilTest {
    private AbstractPushTokenReq msg;
    private AbstractPushResp resp;

    @Before
    public void before() {
//        String token = "e319cfc4b6a7497795618303a57b854e";
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
        String token = "c6c0125d7f846a67ebf2ce7bcc7dcbe1235756b3760e55efde2880d94270ac1d";
        String secret = "SEC95cd8f28b0258c267e3d0abbe1878494c61facb8f4326f5df8bab34e04cfb3df";
        String content = "内容测试";

        msg = DingTalkBuilder.textBuilder()
                .secret(secret)
                .token(token)
                .content(content)
                .build();
    }

    @Test
    public void push() {
        resp = PushUtil.push(msg);
    }

    @After
    public void after() {
//        PushPlusResp<String> r = (PushPlusResp) resp;
//        System.out.println(r);

        DingTalkResp r = (DingTalkResp) resp;
        System.out.println(r);
    }
}