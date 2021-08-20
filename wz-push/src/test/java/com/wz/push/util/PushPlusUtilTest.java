package com.wz.push.util;

import org.junit.Test;

public class PushPlusUtilTest {

    @Test
    public void get() {
        String token = "ccccccccc4b6a7497795xxxxxxxxxxxxxx";
        String title = "测试";
        //String content = "{\"test\":\"vaaa1\", \"key\",\"value\", \"method\",\"post\"}";
        String content = "<h1>异常</h1>" +
                "<p>程序发生了异常.</p>";
        final String data = PushPlusUtil.get(token, title, content);
//        final String data = get(token, title, content, Template.json);
//        final String data = post(token, title, content, Template.json);
//        final String data = post(token, title, content);
        System.out.println(data);
    }
}