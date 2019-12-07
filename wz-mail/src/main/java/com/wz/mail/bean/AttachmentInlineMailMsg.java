package com.wz.mail.bean;

import lombok.*;

import java.io.File;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.bean
 * @className: AttachmentInlineMailMsg
 * @description: 附件-内联
 * @author: Zhi
 * @date: 2019-12-05 19:10
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentInlineMailMsg extends MailMsg {
    /**
     * 附件列表
     */
    @NonNull
    private File[] files;
    /**
     * 静态资源-内联样式
     */
    @NonNull
    private List<InlineMailMsg.Inline> inlines;

    @Override
    public String toString() {
        return super.toString();
    }
}
