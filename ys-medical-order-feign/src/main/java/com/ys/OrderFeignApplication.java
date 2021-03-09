package com.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
@SpringBootApplication
@EnableFeignClients
public class OrderFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFeignApplication.class, args);
    }
}
