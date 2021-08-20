package com.wz.mail.util;

import com.wz.common.util.SpringContextUtil;
import com.wz.mail.bean.MailMsg;
import com.wz.mail.service.MailService;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.util
 * @className: EmailUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
public final class EmailUtil {
    private static final MailService MAIL_SERVICE;

    static {
        MAIL_SERVICE = SpringContextUtil.getBean(MailService.class);
    }

    private EmailUtil() {
    }

    public static void send(MailMsg msg, boolean isHtml) {
        MAIL_SERVICE.send(msg, isHtml);
    }

    public static void sendText(MailMsg msg) {
        MAIL_SERVICE.send(msg, false);
    }

    public static void sendHtml(MailMsg msg) {
        MAIL_SERVICE.send(msg, true);
    }

}
