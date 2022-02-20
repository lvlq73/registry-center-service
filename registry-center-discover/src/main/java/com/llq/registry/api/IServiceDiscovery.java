package com.llq.registry.api;

import com.llq.registry.pojo.Address;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lvlianqi
 * @description
 * @date 2021/11/30
 */
public interface IServiceDiscovery {

    void registerService(String serviceId, Address address);

    List<Address> getService(String serviceId);

    void removeService(String serviceId, Address address);

    void keepalive(String serviceId, Address address, long timestamp);
}
