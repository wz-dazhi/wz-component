package com.wz.shiro.authc;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.ShortCircuitIterationException;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.authc
 * @className: ShiroModularRealmAuthenticator
 * @description:
 * @author: zhi
 * @date: 2020/12/14 下午5:21
 * @version: 1.0
 */
@Slf4j
public class ShiroModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        for (Realm realm : realms) {
            try {
                aggregate = strategy.beforeAttempt(realm, token, aggregate);
            } catch (ShortCircuitIterationException shortCircuitSignal) {
                // Break from continuing with subsequnet realms on receiving
                // short circuit signal from strategy
                break;
            }

            if (realm.supports(token)) {
                AuthenticationInfo info = null;
                Throwable t = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable throwable) {
                    t = throwable;
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);
                // 只有t不为null, 则表示有Realm较验到了异常，则立即中断后续Realm验证直接外抛
                if (t instanceof AuthenticationException) {
                    throw (AuthenticationException) t;
                }
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
