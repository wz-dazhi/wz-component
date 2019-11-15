package com.wz.common.util;

import javax.servlet.http.HttpServletRequest;

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

    public static String getIpAddress(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        String[] ips;
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.length() > 15) {
                ips = ip.split(",");
                for (String strIp : ips) {
                    if (!"unknown".equalsIgnoreCase(strIp)) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } else {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
        }

        if (ip.contains(",")) {
            ips = ip.split(",");
            ip = ips[0];
        }

        return ip;
    }
}
