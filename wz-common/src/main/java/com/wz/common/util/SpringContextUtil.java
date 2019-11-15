package com.wz.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @projectName: wz-common
 * @package: com.wz.common.util
 * @className: SpringContextUtil
 * @description: 获取Spring应用上下文
 * @author: Zhi Wang
 * @date: 2019/1/17 下午2:46
 * @version: 1.0
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtil.context = context;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取当前环境
     *
     * @return dev, test, pro
     */
    public static String getActiveProfile() {
        String[] profiles = getApplicationContext().getEnvironment().getActiveProfiles();
        return 0 == profiles.length ? "" : profiles[0];
    }

}
