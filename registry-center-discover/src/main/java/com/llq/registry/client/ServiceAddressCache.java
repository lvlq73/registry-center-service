package com.llq.registry.client;

import com.llq.registry.pojo.Address;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lvlianqi
 * @description
 * @date 2021/12/15
 */
public class ServiceAddressCache {

    private static List<Address> addresses = new ArrayList<>(10);

    public static void add(Address address){
        addresses.add(address);
    }

    public static void setAddressList(List<Address> list) {
        addresses.clear();
        if (!CollectionUtils.isEmpty(list)) {
            addresses.addAll(list);
        }
    }

    public static List<Address> getList() {
        return addresses;
    }
}
