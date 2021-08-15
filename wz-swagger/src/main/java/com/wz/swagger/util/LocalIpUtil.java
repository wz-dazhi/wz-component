package com.wz.swagger.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: LocalIpUtil
 * @description:
 * @author: zhi
 * @date: 2021/8/15
 * @version: 1.0
 */
@Slf4j
public final class LocalIpUtil {
    private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
    private static final String LOCAL_DEFAULT_IP = "127.0.0.1";

    private LocalIpUtil() {
    }

    public static String localIpv4Address() {
        return localIpv4Address(LOCAL_DEFAULT_IP);
    }

    public static String localIpv4Address(String defaultIpv4) {
        final Set<String> localIpv4Addresses = localIpv4Addresses();
        return localIpv4Addresses.stream().findFirst().orElse(defaultIpv4);
    }

    public static Set<String> localIpv4Addresses() {
        final Set<String> ipAddresses = localIpAddresses();
        return ipAddresses.stream().filter(LocalIpUtil::filterLocalIpv4).collect(Collectors.toSet());
    }

    private static boolean filterLocalIpv4(String ip) {
        return !LOCAL_DEFAULT_IP.equals(ip) && ip.matches(REGX_IP);
    }

    public static Set<String> localIpAddresses() {
        try {
            Set<String> localIpv4Addresses = null;
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface.isUp()) {
                    Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                    while (addressEnumeration.hasMoreElements()) {
                        String ip = addressEnumeration.nextElement().getHostAddress();
                        if (StringUtils.hasLength(ip)) {
                            if (localIpv4Addresses == null) {
                                localIpv4Addresses = new HashSet<>();
                            }
                            localIpv4Addresses.add(ip);
                        }
                    }
                }
            }
            return localIpv4Addresses == null ? Set.of() : localIpv4Addresses;
        } catch (Exception e) {
            log.error(">>> 获取ip address列表异常, e: ", e);
            return Set.of();
        }
    }
}
