package com.wz.common.util;

import cn.hutool.core.lang.TypeReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * @projectName: wz-common
 * @package: com.wz.common.util
 * @className: SpringContextUtil
 * @description: 获取Spring应用上下文
 * @author: Zhi Wang
 * @date: 2019/1/17 下午2:46
 * @version: 1.0
 **/
public final class SpringContextUtil implements BeanFactoryAware, ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringContextUtil.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtil.applicationContext = context;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 通过name获取 Bean.
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 通过类型参考返回带泛型参数的Bean
     *
     * @param reference 类型参考，用于持有转换后的泛型类型
     * @param <T>       Bean类型
     * @return 带泛型参数的Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(TypeReference<T> reference) {
        final ParameterizedType parameterizedType = (ParameterizedType) reference.getType();
        final Class<T> rawType = (Class<T>) parameterizedType.getRawType();
        final Class<?>[] genericTypes = Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> (Class<?>) type).toArray(Class[]::new);
        final String[] beanNames = applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(rawType, genericTypes));
        return getBean(beanNames[0], rawType);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param <T>  Bean类型
     * @param type 类、接口，null表示获取所有bean
     * @return 类型对应的bean，key是bean注册的name，value是Bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     *
     * @param type 类、接口，null表示获取所有bean名称
     * @return bean名称
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    /**
     * 动态向Spring注册Bean
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     */
    public static <T> void registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        context.getBeanFactory().registerSingleton(beanName, bean);
    }

    public static Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前环境
     *
     * @return dev, test, pro
     */
    public static String getActiveProfile() {
        String[] profiles = getActiveProfiles();
        return (null == profiles || 0 == profiles.length) ? null : profiles[0];
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     */
    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

}
