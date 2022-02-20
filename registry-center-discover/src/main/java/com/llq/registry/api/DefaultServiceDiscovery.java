package com.llq.registry.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.llq.registry.client.ServiceWatch;
import com.llq.registry.enums.ResultEnum;
import com.llq.registry.http.RestTemplateSingleton;
import com.llq.registry.pojo.Address;
import com.llq.registry.pojo.HttpResult;
import com.llq.registry.pojo.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lvlianqi
 * @description 默认服务发现，采用http请求服务
 * @date 2021/12/9
 */
public class DefaultServiceDiscovery implements IServiceDiscovery {

    private static Logger logger = LoggerFactory.getLogger(DefaultServiceDiscovery.class);

    private ServiceWatch serviceWatch;

    private String url;

    private boolean first = true;

    public DefaultServiceDiscovery(String url) {
        if (!StringUtils.hasText(url)) {
            throw new RuntimeException("registry url is not set value");
        }
        this.url = url;
        this.serviceWatch = new ServiceWatch(this);
    }

    @Override
    public void registerService(String serviceId, Address address) {
        try {
            HttpResult result = RestTemplateSingleton.getInstance().postForObject(url + "/registry/registerService",
                    new ServiceInfo(serviceId, address, 0), HttpResult.class);
            if (!String.valueOf(ResultEnum.SUCCESS.getCode()).equals(result.code)) {
                logger.error("{} {}, registry service is fail; {}", serviceId, address.host + ":" + address.port, result.message);
            } else {
                serviceWatch.heartbeatWatch(new ServiceInfo(serviceId, address, 0));
            }
        } catch (Exception e) {
            logger.error("请求异常", e);
            serviceWatch.heartbeatWatch(new ServiceInfo(serviceId, address, 0));
        }
    }

    @Override
    public List<Address> getService(String serviceId) {
        HttpResult<List<Address>> result = RestTemplateSingleton.getInstance().getForObject(url + "/registry/getService?serviceId=" + serviceId, HttpResult.class);
        if (String.valueOf(ResultEnum.SUCCESS.getCode()).equals(result.code)) {
            if (first) {
                serviceWatch.activeServiceWatch(new ServiceInfo(serviceId, null, 0));
                first = false;
            }
            return JSONObject.parseObject(JSONObject.toJSONString(result.result), new TypeReference<List<Address>>(){}) ;
        } else {
            logger.error(result.message);
        }
        return null;
    }

    @Override
    public void removeService(String serviceId, Address address) {
        HttpResult result = RestTemplateSingleton.getInstance().postForObject(url + "/registry/removeService",
                new ServiceInfo(serviceId, address, 0), HttpResult.class);
        if (!String.valueOf(ResultEnum.SUCCESS.getCode()).equals(result.code)) {
            logger.error("{} {}, remove service is fail; {}", serviceId, address.host + ":" + address.port, result.message);
        }
    }

    @Override
    public void keepalive(String serviceId, Address address, long timestamp) {
        HttpResult result = RestTemplateSingleton.getInstance().postForObject(url + "/registry/keepalive",
                new ServiceInfo(serviceId, address, 0), HttpResult.class);
        if (!String.valueOf(ResultEnum.SUCCESS.getCode()).equals(result.code)) {
            logger.error("{} {}, keepalive service is fail; {}", serviceId, address.host + ":" + address.port, result.message);
        }
    }
}
