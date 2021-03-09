package com.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
// @EnableEurekaServer 表明该服务为 eureka 的服务注册中心，管理配置和注册
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplicationA {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationA.class, args);
    }
}
