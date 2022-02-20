package com.llq.registry.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

/**
 * @author llianqi@linewell.com
 * @description
 * @since 2021/9/10
 */
public class RestTemplateSingleton {
    private static Logger logger = LoggerFactory.getLogger(RestTemplateSingleton.class);

    //整个连接池最大连接数
    private static final int MAX_TOTAL = 10;
    //每个路由最大连接数
    private static final int DEFAULT_MAX_PRE_ROUTE = 5;
    //连接超时时间
    private static final int TIMEOUT = 30000;
//    private static PoolingHttpClientConnectionManager connectionManager;
    private static Registry<ConnectionSocketFactory> registry;
    private static RequestConfig requestConfig;
    private static RestTemplate REST_TEMPLATE = null;

    private RestTemplateSingleton() {}

    //静态块 初始化一些配置
    static {
        registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        /*connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(MAX_TOTAL);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PRE_ROUTE);*/
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT) //服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setConnectTimeout(TIMEOUT)//连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectionRequestTimeout(TIMEOUT)//从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();
    }


    public static RestTemplate getInstance() {
        if (REST_TEMPLATE == null) {
            synchronized (RestTemplate.class) {
                if (REST_TEMPLATE == null) {
                    try {
                        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
                        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

                        SSLConnectionSocketFactory connectionSocketFactory =
                                new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

                        HttpClientBuilder httpClientBuilder = HttpClients.custom();
                        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
                        CloseableHttpClient httpClient = httpClientBuilder.setDefaultRequestConfig(requestConfig)
                                .setConnectionManager(connectionManager())
                                .build();
                        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
                        REST_TEMPLATE = new RestTemplate(factory);
                    } catch (Exception e) {
                        logger.error("ssl loadTrustMaterial 异常", e);
                    }
                }
            }
        }
        return REST_TEMPLATE;
    }

    private static PoolingHttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(MAX_TOTAL);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PRE_ROUTE);
        return connectionManager;
    }
}
