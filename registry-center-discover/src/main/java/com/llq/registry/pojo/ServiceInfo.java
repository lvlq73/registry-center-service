package com.llq.registry.pojo;

/**
 * @author lvlianqi
 * @description
 * @date 2021/11/30
 */
public class ServiceInfo {

    private String serviceId;

    private Address address;

    private long timestamp;


    public ServiceInfo() {

    }

    public ServiceInfo(String serviceId, Address address, long timestamp) {
        this.serviceId = serviceId;
        this.address = address;
        this.timestamp = timestamp;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
