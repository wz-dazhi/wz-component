package com.wz.push.builder;

import lombok.ToString;

/**
 * @projectName: wz-component
 * @package: com.wz.push.builder
 * @className: AbstractBuilder
 * @description:
 * @author: zhi
 * @date: 2021/8/19
 * @version: 1.0
 */
@ToString
public abstract class AbstractBuilder<Req, Builder extends AbstractBuilder> {
    protected String token;

    public Builder token(String token) {
        this.token = token;
        return (Builder) this;
    }

    public String token() {
        return token;
    }

    /**
     * 子类构建具体的请求实体
     */
    public abstract Req build();
}
