package com.wz.push.enums;

/**
 * @projectName: wz-component
 * @package: com.wz.push.enums
 * @className: PushPlusTemplate
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
public enum PushPlusTemplate {
    /**
     * 支持html文本。为空默认使用html模板
     */
    html,
    /**
     * 纯文本内容,不转义html内容,换行使用\n
     */
    txt,
    /**
     * 可视化展示json格式内容
     */
    json,
    /**
     * 内容基于markdown格式展示
     */
    markdown,
    /**
     * 阿里云监控报警定制模板
     */
    cloudMonitor
}
