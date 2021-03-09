package com.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableDiscoveryClient 该注解用于向使用 consul 或者 zookeeper 作为注册中心时注册服务
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentApplicationZK {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationZK.class, args);
    }
}
