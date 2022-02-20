# 注册中心
> ### 介绍
* 自己写的注册中心，主要为了配套[netty框架项目](https://github.com/lvlq73/netty)
> ### 使用
1. 引入依赖
    ```xml
       <dependency>
         <groupId>com.llq</groupId>
         <artifactId>registry-center-discover</artifactId>
         <version>${llq.registry.version}</version>
       </dependency>
    ```
2. 初始化对象
    ```java
     //url为注册中心微服务地址和端口
     IServiceDiscovery serviceDiscovery = new DefaultServiceDiscovery("http://127.0.0.1:9000");
    ```
3. 注册服务
    ```java
     //微服务的服务id
     String serviceId = "test";
     //微服务的ip
     String host = "127.0.0.1";
     //微服务的端口
     int port = 8888;
     serviceDiscovery.registerService(serviceId, new Address(host, port));
    ```
4. 获取服务地址
    ```java
     //微服务的服务id
     String serviceId = "test";
     List<Address> addresses = serviceDiscovery.getService(serviceId);
    ```

