package com.wz.webmvc.util;

import com.wz.common.constant.Consts;
import com.wz.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @projectName: wz
 * @package: com.wz.common.util
 * @className: IpUtil
 * @description:
 * @author: Zhi
 * @date: 2019-09-20 17:27
 * @version: 1.0
 */
public final class IpUtil {
    private IpUtil() {
    }

    public static String getIp(HttpServletRequest req) {
        Objects.requireNonNull(req, "HttpServletRequest is cannot null.");
        final String comma = Consts.COMMA;

        String ip = req.getHeader("X-Forwarded-For");
        String[] ips;
        // 多级反向代理检测
        if (ip != null && ip.indexOf(comma) > 0) {
            ips = ip.trim().split(comma);
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        } else {
            if (isUnknown(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
            if (isUnknown(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
            if (isUnknown(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }
            if (isUnknown(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (isUnknown(ip)) {
                ip = req.getRemoteAddr();
            }
        }

        if (ip.contains(comma)) {
            ips = ip.split(comma);
            ip = ips[0];
        }

        return ip;
    }

    public static String getIp() {
        return getIp(WebContextUtil.getRequest());
    }

    private static boolean isUnknown(String ip) {
        return StringUtil.isBlank(ip) || Consts.UNKNOWN_LOWER.equalsIgnoreCase(ip);
    }

}
