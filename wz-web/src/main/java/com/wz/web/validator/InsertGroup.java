package com.wz.web.validator;

import javax.validation.groups.Default;

/**
 * @projectName: wz-component
 * @package: com.wz.web.validator
 * @className: InsertGroup
 * @description: <pre>
 * 公共插入@Validated(InsertGroup.class)
 * 例:
 * @NotBlank(groups = {InsertGroup.class})
 * private String name;
 * </pre>
 * @author: zhi
 * @date: 2021/12/6
 * @version: 1.0
 */
public interface InsertGroup extends Default {
}
