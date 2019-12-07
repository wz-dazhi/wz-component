package com.wz.mail.bean;

import com.wz.common.util.JsonUtil;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.mail.bean
 * @className: InlineMailMsg
 * @description: 内联静态资源消息
 * @author: Zhi
 * @date: 2019-12-05 12:29
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InlineMailMsg extends MailMsg {
    /**
     * 静态资源-内联样式
     */
    @NonNull
    private List<Inline> inlines;

    @Override
    public String toString() {
        return super.toString();
    }

    @Data
    public static class Inline implements Serializable {
        /**
         * 内联ID
         */
        @NonNull
        private String cid;
        /**
         * 静态文件
         */
        @NonNull
        private File file;

        @Override
        public String toString() {
            return JsonUtil.toJsonString(this);
        }
    }
}
