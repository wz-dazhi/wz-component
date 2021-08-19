package com.wz.push.util;

import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.dingtalk.AbstractDingTalkReq;
import com.wz.push.builder.DingTalkBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushDingTalkUtilTest {
    private AbstractDingTalkReq req;
    private AbstractPushResp resp;

    @Before
    public void before() {
        String token = "c6c0125d7f846a67ebf2ce7bcc7dcbe1235756b3760e55efde2880d94270ac1d";
        String secret = "SEC95cd8f28b0258c267e3d0abbe1878494c61facb8f4326f5df8bab34e04cfb3df";
        String content = "内容测试";

        req = DingTalkBuilder.textBuilder()
                .secret(secret)
                .token(token)
                .content(content)
                .build();
    }

    @Test
    public void pushSign() {
        resp = PushDingTalkUtil.pushSign(req);
    }

    @After
    public void after() {
        System.out.println(resp);
    }
}