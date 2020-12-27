package com.wz.common.util;

import cn.hutool.core.util.StrUtil;
import com.wz.common.constant.Consts;

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

    public static String getIp(HttpServletRequest req) {
        final String comma = Consts.COMMA;
        final String unknown = Consts.UNKNOWN_LOWER;

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
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }

            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
        }

        if (ip.contains(comma)) {
            ips = ip.split(comma);
            ip = ips[0];
        }

        return ip;
    }

    private static boolean isUnknown(String checkString) {
        return StrUtil.isBlank(checkString) || Consts.UNKNOWN_LOWER.equalsIgnoreCase(checkString);
    }

}
