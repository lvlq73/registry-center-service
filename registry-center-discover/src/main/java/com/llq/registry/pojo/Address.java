package com.llq.registry.pojo;

/**
 * @author lvlianqi
 * @description 服务地址
 * @createDate 2020/5/21
 */
public class Address {
    public String host;

    public int port;

    public Address(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
