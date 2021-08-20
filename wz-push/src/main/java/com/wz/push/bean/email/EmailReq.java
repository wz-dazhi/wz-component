package com.wz.push.bean.email;

import com.wz.mail.bean.MailMsg;
import com.wz.push.bean.AbstractPushReq;
import com.wz.push.enums.PushType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.email
 * @className: EmailReq
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailReq extends AbstractPushReq {
    private final MailMsg msg;
    private boolean html;

    public EmailReq(MailMsg msg) {
        this.msg = msg;
        setType(PushType.EMAIL);
    }

    public MailMsg getMsg() {
        return msg;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }
}
