package com.wz.mail.service;

import com.wz.common.model.Result;
import com.wz.mail.bean.AttachmentMailMsg;
import com.wz.mail.bean.MailMsg;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.service
 * @className: MailService
 * @description:
 * @author: Zhi
 * @date: 2019-12-05 10:44
 * @version: 1.0
 */
public interface MailService {

    /**
     * 发送邮件
     *
     * @param msg    邮件实体
     * @param isHtml 是否属于html邮件
     * @return 发送结果
     * @see MailMsg 普通邮件
     * @see com.wz.mail.bean.AttachmentInlineMailMsg 附件-内联静态资源 邮件
     * @see AttachmentMailMsg 附件邮件
     * @see com.wz.mail.bean.InlineMailMsg 内联静态资源邮件
     */
    Result<Boolean> send(MailMsg msg, boolean isHtml);

}
