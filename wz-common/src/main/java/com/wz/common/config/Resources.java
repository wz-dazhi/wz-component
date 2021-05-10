package com.wz.common.config;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: wz
 * @package: com.common.config
 * @className: Resources
 * @description: 国际化资源文件
 * @author: Zhi Wang
 * @createDate: 2018/9/9 下午1:11
 **/
public final class Resources {

    private static final Map<String, ResourceBundle> MESSAGES = new ConcurrentHashMap<>();

    public static String getMessage(String baseName, String key, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        String bundlingKey = baseName + locale.toString();
        ResourceBundle message = MESSAGES.get(bundlingKey);
        if (message == null) {
            message = MESSAGES.computeIfAbsent(bundlingKey, k -> ResourceBundle.getBundle(baseName, locale));
        }
        if ((params != null) && (params.length > 0)) {
            return String.format(message.getString(key), params);
        }
        return message.getString(key);
    }

    public static String getMessage(String key, Object... params) {
        return getMessage("i18n/messages/messages", key, params);
    }

    public static void flushMessage() {
        MESSAGES.clear();
    }

}

