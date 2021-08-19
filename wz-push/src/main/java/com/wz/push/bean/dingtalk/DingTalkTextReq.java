package com.wz.push.bean.dingtalk;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.dingtalk
 * @className: DingTalkTextReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DingTalkTextReq extends AbstractDingTalkReq {
    private Text text;
    private DingTalkAtReq at;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Text implements Serializable {
        private String content;
    }
}
