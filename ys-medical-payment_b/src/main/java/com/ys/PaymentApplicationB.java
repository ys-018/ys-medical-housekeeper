package com.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentApplicationB {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationB.class,args);
    }
}
