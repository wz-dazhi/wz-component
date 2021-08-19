package com.wz.push.bean.dingtalk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean.dingtalk
 * @className: DingTalkAtReq
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingTalkAtReq implements Serializable {
    private String[] atMobiles;
    private String[] atUserIds;
    private boolean isAtAll;
}
