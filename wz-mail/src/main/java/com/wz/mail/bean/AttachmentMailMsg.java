package com.wz.mail.bean;

import lombok.*;

import java.io.File;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.bean
 * @className: AttachmentMailMsg
 * @description: 附件消息
 * @author: Zhi
 * @date: 2019-12-05 12:00
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentMailMsg extends MailMsg {
    /**
     * 附件列表
     */
    @NonNull
    private File[] files;

    @Override
    public String toString() {
        return super.toString();
    }
}
