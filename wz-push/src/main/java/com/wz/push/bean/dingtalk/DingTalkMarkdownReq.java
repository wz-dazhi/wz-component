package com.wz.push.bean.dingtalk;

import com.wz.push.enums.DingTalkMsgType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.dingtalk
 * @className: DingTalkMarkdownReq
 * @description:
 * @author: zhi
 * @date: 2021/8/20
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkMarkdownReq extends BaseDingTalkReq {
    private Markdown markdown;
    private DingTalkAtReq at;

    public DingTalkMarkdownReq() {
        super();
        setMsgType(DingTalkMsgType.markdown);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Markdown implements Serializable {
        private String title;
        private String text;
    }
}
