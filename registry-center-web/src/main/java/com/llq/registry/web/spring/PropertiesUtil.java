package com.llq.registry.web.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @description: 配置文件上下文
 * @author: llianqi@linewell.com
 * @since: 2021/6/8
 */
@Component
public class PropertiesUtil implements EmbeddedValueResolverAware {

    private static StringValueResolver stringValueResolver;

    private PropertiesUtil() {}

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }

    public static String getPropertiesValue(String name) {
        return stringValueResolver.resolveStringValue("${"+name+"}");
    }

    public static <T> T getPropertiesValue(String name, Class<T> cls) {
        ObjectMapper om = new ObjectMapper();
        return om.convertValue(stringValueResolver.resolveStringValue("${"+name+"}"), cls);
    }
}
