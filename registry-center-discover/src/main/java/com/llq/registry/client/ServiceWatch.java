package com.llq.registry.client;

import com.llq.registry.api.IServiceDiscovery;
import com.llq.registry.pojo.Address;
import com.llq.registry.pojo.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lvlianqi
 * @description
 * @date 2021/12/14
 */
public class ServiceWatch {

    private Logger logger = LoggerFactory.getLogger(ServiceWatch.class);

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

    private IServiceDiscovery serviceDiscovery;

    public ServiceWatch(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public void heartbeatWatch(ServiceInfo serviceInfo) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    serviceDiscovery.keepalive(serviceInfo.getServiceId(), serviceInfo.getAddress(), serviceInfo.getTimestamp());
                } catch (Exception e) {
                    logger.error("schedule heartbeat is error", e);
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void activeServiceWatch(ServiceInfo serviceInfo) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Address> service = serviceDiscovery.getService(serviceInfo.getServiceId());
                    ServiceAddressCache.setAddressList(service);
                } catch (Exception e) {
                    logger.error("schedule getService is error", e);
                }
            }
        }, 0, 50, TimeUnit.SECONDS);
    }
}
