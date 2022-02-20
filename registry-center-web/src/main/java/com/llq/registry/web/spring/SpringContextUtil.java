package com.llq.registry.web.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: spring上下文工具类
 * @author: llianqi@linewell.com
 * @since: 2021/5/17
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获得spring上下文
     *
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取bean
     *
     * @param name service注解方式name为小驼峰格式
     * @return Object bean的实例对象
     */
    public static <T> T getBean(String name, Class<T> type) throws BeansException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(applicationContext.getBean(name), type);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) throws BeansException {
        return applicationContext.getBean(type);
    }
}

