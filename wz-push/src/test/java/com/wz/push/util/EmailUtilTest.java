package com.wz.push.util;

import com.wz.mail.bean.MailMsg;
import com.wz.push.bean.AbstractPushResp;
import com.wz.push.bean.email.EmailReq;
import org.junit.jupiter.api.Test;

/**
 * @projectName: wz-component
 * @package: com.wz.push.util
 * @className: EmailUtilTest
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
public class EmailUtilTest extends PushAppTest {

    @Test
    public void send() {
        final MailMsg msg = new MailMsg();
        msg.setFrom("来自邮箱");
        msg.setTo(new String[]{"发送邮箱"});
        msg.setSubject("email测试");
        msg.setText("测试内容--000");

        final EmailReq req = new EmailReq(msg);
        final AbstractPushResp resp = PushUtil.push(req);
        System.out.println(resp);
    }

}
