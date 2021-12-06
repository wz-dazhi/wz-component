package com.wz.web.validator;

import javax.validation.groups.Default;

/**
 * @projectName: wz-component
 * @package: com.wz.web.validator
 * @className: UpdateGroup
 * @description: <pre>
 * 公共更新@Validated(UpdateGroup.class)
 * 例:
 * @NotBlank(groups = {UpdateGroup.class})
 * private String id;
 * </pre>
 * @author: zhi
 * @date: 2021/12/6
 * @version: 1.0
 */
public interface UpdateGroup extends Default {
}
