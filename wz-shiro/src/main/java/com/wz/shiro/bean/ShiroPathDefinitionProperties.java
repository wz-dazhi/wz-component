package com.wz.shiro.bean;

import lombok.Data;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.bean
 * @className: ShiroProperties
 * @description:
 * @author: zhi
 * @date: 2020/12/9 下午6:20
 * @version: 1.0
 */
@Data
public class ShiroPathDefinitionProperties {

    /**
     * uri path
     */
    private String path;

    /**
     * 权限定义, 多个权限使用逗号分隔
     */
    private String definition;
}
