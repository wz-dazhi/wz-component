package com.wz.mail.bean;

import com.wz.common.util.JsonUtil;
import lombok.*;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.bean
 * @className: MailMsg
 * @description: 基本消息
 * @author: Zhi
 * @date: 2019-12-05 11:32
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailMsg implements Serializable {
    /**
     * 发送人
     */
    @NonNull
    private String from;
    /**
     * 接收者
     */
    @NonNull
    private String[] to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 内容/html
     */
    private String text;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
