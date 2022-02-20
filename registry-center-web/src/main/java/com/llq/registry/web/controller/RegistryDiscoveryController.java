package com.llq.registry.web.controller;

import com.llq.registry.pojo.Address;
import com.llq.registry.pojo.HttpResult;
import com.llq.registry.pojo.ServiceInfo;
import com.llq.registry.web.service.ServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lvlianqi
 * @description
 * @date 2021/11/30
 */
@RestController
@RequestMapping("/registry")
public class RegistryDiscoveryController {

    @Autowired
    private ServiceDiscovery serviceDiscovery;

    @PostMapping("registerService")
    public HttpResult<String> registerService(@RequestBody ServiceInfo serviceInfo) {
        serviceDiscovery.registerService(serviceInfo.getServiceId(), serviceInfo.getAddress());
        return HttpResult.success("success");
    }

    @GetMapping("getService")
    public HttpResult<List<Address>> getService(@RequestParam("serviceId") String serviceId) {
        List<Address> address = serviceDiscovery.getService(serviceId);
        return HttpResult.success(address);
    }

    @PostMapping("removeService")
    public HttpResult<String> removeService(@RequestBody ServiceInfo serviceInfo) {
        serviceDiscovery.removeService(serviceInfo.getServiceId(), serviceInfo.getAddress());
        return HttpResult.success("success");
    }

    @PostMapping("keepalive")
    public HttpResult<String> keepalive(@RequestBody ServiceInfo serviceInfo) {
        serviceDiscovery.keepalive(serviceInfo.getServiceId(), serviceInfo.getAddress(), serviceInfo.getTimestamp());
        return HttpResult.success("success");
    }
}
