package com.wz.shiro.authc;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.authc
 * @className: AbstractShiroRealm
 * @description: realm 基类
 * @author: zhi
 * @date: 2020/12/15 下午5:20
 * @version: 1.0
 */
public abstract class AbstractShiroRealm extends AuthorizingRealm {

    /**
     * 支持token类型, 例:
     * <p>
     *
     * @Override public boolean supports(AuthenticationToken token) {
     * return token instanceof ShiroToken && DefaultLoginType.SIMPLE == ((ShiroToken) token).getLoginType();
     * }
     * </p>
     */
    @Override
    public abstract boolean supports(AuthenticationToken token);

}
