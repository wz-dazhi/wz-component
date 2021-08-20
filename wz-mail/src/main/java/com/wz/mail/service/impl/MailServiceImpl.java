package com.wz.mail.service.impl;

import com.wz.common.model.Result;
import com.wz.common.util.Results;
import com.wz.common.util.StringUtil;
import com.wz.mail.bean.AttachmentInlineMailMsg;
import com.wz.mail.bean.AttachmentMailMsg;
import com.wz.mail.bean.InlineMailMsg;
import com.wz.mail.bean.MailMsg;
import com.wz.mail.service.MailService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.service.impl
 * @className: MailServiceImpl
 * @description:
 * @author: Zhi
 * @date: 2019-12-05 11:28
 * @version: 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender sender;

    /**
     * 发送邮件
     */
    @Override
    public Result<Boolean> send(@NonNull MailMsg msg, boolean isHtml) {
        log.debug(">>> 开始发送邮件: {}, isHtml: {}", msg, isHtml);
        StringUtil.requireNonNull(msg.getFrom(), "发送人[from]不能为空");
        Objects.requireNonNull(msg.getTo(), "接收人[to]不能为空");
        return this.doSend(msg, isHtml);
    }

    private <S extends MailMsg> Result<Boolean> doSend(S s, boolean isHtml) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(s.getFrom());
            helper.setTo(s.getTo());
            helper.setSubject(s.getSubject());
            helper.setSentDate(new Date());
            helper.setText(s.getText(), isHtml);

            if (s instanceof AttachmentMailMsg) {
                this.addAttachment(helper, ((AttachmentMailMsg) s).getFiles());
            } else if (s instanceof InlineMailMsg) {
                this.addInline(helper, ((InlineMailMsg) s).getInlines());
            } else if (s instanceof AttachmentInlineMailMsg) {
                AttachmentInlineMailMsg aiMsg = (AttachmentInlineMailMsg) s;
                this.addAttachment(helper, aiMsg.getFiles());
                this.addInline(helper, aiMsg.getInlines());
            }

            sender.send(message);
            return Results.ok(true);
        } catch (Exception e) {
            String msg = e.getMessage();
            log.error(">>> 发送邮件发生异常. msg: {}, e: ", msg, e);
            return Results.fail(msg);
        }
    }

    private void addAttachment(MimeMessageHelper h, File[] files) throws MessagingException {
        Objects.requireNonNull(files, "附件不能为空");
        for (File f : files) {
            h.addAttachment(f.getName(), new FileSystemResource(f));
        }
    }

    private void addInline(MimeMessageHelper h, List<InlineMailMsg.Inline> inlines) throws MessagingException {
        Objects.requireNonNull(inlines, "内联文件不能为空");
        for (InlineMailMsg.Inline i : inlines) {
            h.addInline(i.getCid(), i.getFile());
        }
    }
}
