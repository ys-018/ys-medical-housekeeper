package com.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class PaymentHystrixDashboard {

    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixDashboard.class, args);
    }
}
