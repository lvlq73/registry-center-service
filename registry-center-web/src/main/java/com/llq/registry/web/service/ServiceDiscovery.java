package com.llq.registry.web.service;

import com.llq.registry.api.IServiceDiscovery;
import com.llq.registry.pojo.Address;
import com.llq.registry.web.storage.IStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lvlianqi
 * @description
 * @date 2021/11/30
 */
@Service
public class ServiceDiscovery implements IServiceDiscovery {

    private Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);

    @Autowired
    @Qualifier("cacheStorage")
    private IStorage<Address> storage;

    @Override
    public void registerService(String serviceId, Address address) {
        storage.add(serviceId, address);
        logger.info("register {} service is success", serviceId);
    }

    @Override
    public List<Address> getService(String serviceId) {
        return storage.getList(serviceId);
    }

    @Override
    public void removeService(String serviceId, Address address) {
        storage.remove(serviceId, address);
        logger.info("remove {} service is success", serviceId);
    }

    @Override
    public void keepalive(String serviceId, Address address, long timestamp) {
        storage.update(serviceId, address, timestamp);
        logger.info("keepalive {} service is success", serviceId);
    }
}
