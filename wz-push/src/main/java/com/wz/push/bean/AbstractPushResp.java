package com.wz.push.bean;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @projectName: wz-component
 * @package: com.wz.push.bean
 * @className: AbstractPushResp
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@Data
public abstract class AbstractPushResp {
    @JsonAlias({"errcode"})
    private String code;
    @JsonAlias({"errmsg"})
    private String msg;
}
