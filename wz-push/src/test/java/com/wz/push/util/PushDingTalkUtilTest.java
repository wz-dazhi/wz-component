package com.wz.push.util;

import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.dingtalk.BaseDingTalkReq;
import com.wz.push.builder.DingTalkBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushDingTalkUtilTest {
    private BaseDingTalkReq req;
    private AbstractPushResp resp;

    @Before
    public void before() {
        String token = "xxxxxxxxxxx565e436dd584bdbf7c0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        //String secret = "Sxxxxxxxxxxx0258c267e3d0abbe1878494c61faxxxxxxxxxxxxxxxxxxxxxxxxx";
        String content = "内容测试";

        // text
        req = DingTalkBuilder.textBuilder()
                //.secret(secret)
                .token(token)
                .content(content)
                .build();

        // link
//        req = DingTalkBuilder.linkBuilder()
//                .token(token)
//                .title("时代的火车向前开")
//                .text("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林")
//                .messageUrl("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI")
//                .picUrl("https://img1.baidu.com/it/u=3819562502,3764730389&fm=253&fmt=auto&app=120&f=GIF?w=600&h=300")
//                .build();
//
//        // markdown
//        req = DingTalkBuilder.markdownBuilder()
//                .token(token)
//                .title("杭州天气")
//                .text("#### 杭州天气 @185xxxxxxxx \n" +
//                        " > 9度，西北风1级，空气良89，相对温度73%\n " +
//                        "> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n" +
//                        " > ###### 10点20分发布 [天气](https://www.dingtalk.com) \n")
//                //.atMobiles(new String[]{"185xxxxxxxx"})
//                //.atUserIds(new String[]{"userIds"})
//                .build();
//
//        // actionCard
//        req = DingTalkBuilder.actionCardBuilder()
//                .token(token)
//                .title("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
//                .text("![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) \n" +
//                        " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
//                        " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
//                .btnOrientation("0")
//                .singleTitle("阅读全文")
//                .singleUrl("https://www.dingtalk.com/")
//                .build();
//
//        // actionCard
//        req = DingTalkBuilder.actionCardBuilder()
//                .token(token)
//                .title("我 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
//                .text("![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png) \n\n" +
//                        " #### 乔布斯 20 年前想打造的苹果咖啡厅 \n\n " +
//                        "Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
//                .btnOrientation("0")
//                .btns(new DingTalkActionCardReq.Btn[]{
//                        new DingTalkActionCardReq.Btn("内容不错", "https://www.dingtalk.com/"),
//                        new DingTalkActionCardReq.Btn("不感兴趣", "https://www.dingtalk.com/")
//                }).build();
//
//        // feedCard
//        req = DingTalkBuilder.feedCardBuilder()
//                .token(token)
//                .links(new DingTalkFeedCardReq.Link[]{
//                        new DingTalkFeedCardReq.Link("时代的火车向前开1", "https://www.dingtalk.com/", "https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png"),
//                        new DingTalkFeedCardReq.Link("时代的火车向前开2", "https://www.dingtalk.com/", "https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png")
//                })
//                .build();
    }

    @Test
    public void push() {
        resp = PushDingTalkUtil.push(req);
    }

    @After
    public void after() {
        System.out.println(resp);
    }
}