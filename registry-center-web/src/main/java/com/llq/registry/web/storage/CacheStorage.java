package com.llq.registry.web.storage;

import com.llq.registry.pojo.Address;
import com.llq.registry.pojo.ServiceInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lvlianqi
 * @description
 * @date 2021/12/9
 */
@Component
public class CacheStorage implements IStorage<Address> {

    private static Map<String, List<ServiceInfo>> cache = new ConcurrentHashMap();

    private int expire = 60 * 1000;

    @Override
    public void add(String key, Address address) {
        ServiceInfo serviceInfo = new ServiceInfo(key, address, getExpireTime());
        List<ServiceInfo> serviceInfos = cache.get(key);
        if (CollectionUtils.isEmpty(serviceInfos)) {
            List<ServiceInfo> infos = new ArrayList<>();
            infos.add(serviceInfo);
            cache.put(key, infos);
        } else {
            boolean isNotExist = true;
            for (ServiceInfo info : serviceInfos) {
                Address infoAddress = info.getAddress();
                if (infoAddress.host.equals(address.host) && infoAddress.port == address.port) {
                    isNotExist = false;
                    break;
                }
            }
            if (isNotExist) {
                serviceInfos.add(serviceInfo);
            }
        }
    }

    @Override
    public void remove(String key, Address address) {
        List<ServiceInfo> serviceInfos = cache.get(key);
        if (CollectionUtils.isEmpty(serviceInfos)) {
            return;
        }
        int index = -1;
        for (int i = 0; i < serviceInfos.size(); i++) {
            Address infoAddress = serviceInfos.get(i).getAddress();
            if (infoAddress.host.equals(address.host) && infoAddress.port == address.port) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            serviceInfos.remove(index);
        }
    }

    @Override
    public List<Address> getList(String key) {
        List<ServiceInfo> serviceInfos = cache.get(key);
        if (CollectionUtils.isEmpty(serviceInfos)) {
            return null;
        }
        long now = new Date().getTime();
        List<Address> collect = serviceInfos.stream().filter(o -> o.getTimestamp() > now)
                .map(ServiceInfo::getAddress).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void update(String key, Address address, long timestamp) {
        List<ServiceInfo> serviceInfos = cache.get(key);
        if (CollectionUtils.isEmpty(serviceInfos)) {
            add(key, address);
        }
        timestamp = timestamp == 0 ? getExpireTime() : timestamp;
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < serviceInfos.size(); i++) {
            ServiceInfo info = serviceInfos.get(i);
            Address infoAddress = info.getAddress();
            if (infoAddress.host.equals(address.host) && infoAddress.port == address.port) {
                info.setTimestamp(timestamp);
            } else if (info.getTimestamp() < new Date().getTime()) {
                indexs.add(i);
            }
        }
        if (indexs.size() > 0) {
            for (Integer index : indexs) {
                serviceInfos.remove(index);
            }
        }
    }

    private long getExpireTime() {
        return new Date().getTime() + expire;
    }

}
