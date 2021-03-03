package com.wz.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.shiro.util
 * @className: ShiroUtil
 * @description:
 * @author: zhi
 * @date: 2021/3/3
 * @version: 1.0
 */
public final class ShiroUtil {
    private ShiroUtil() {
    }

    public static String simpleHash(String hashAlgorithmName, int hashIterations, String password, String salt) {
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, password, salt);
        simpleHash.setIterations(hashIterations);
        return simpleHash.toHex();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static <S> S getPrincipal() {
        return (S) SecurityUtils.getSubject().getPrincipal();
    }

    public static <K, V> void setSessionAttribute(K k, V v) {
        getSession().setAttribute(k, v);
    }

    public static <K, V> V getSessionAttribute(K k) {
        return (V) getSession().getAttribute(k);
    }

    public static <K, V> V removeSessionAttribute(K k) {
        return (V) getSession().removeAttribute(k);
    }

    public static <K, V> void setSessionAttribute(Map<K, V> map) {
        final Session session = getSession();
        map.forEach(session::setAttribute);
    }

    public static <K> Collection<K> getSessionAttributeKeys() {
        return (Collection<K>) getSession().getAttributeKeys();
    }

    public static boolean isLogin() {
        return getPrincipal() != null;
    }

    public static void bindSubject(Object principal, String realmName, Serializable sessionId) {
        final SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(principal, realmName);
        final Subject loginSubject = new Subject.Builder()
                .principals(principalCollection)
                .sessionId(sessionId)
                .sessionCreationEnabled(true)
                .authenticated(true)
                .buildSubject();
        ThreadContext.bind(loginSubject);
    }
}
