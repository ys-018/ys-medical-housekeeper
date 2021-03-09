package com.ys;

import com.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */

@SpringBootApplication
@EnableEurekaClient
// 该注解的意思就是 访问 YS-MEDICAL-PAYMENT 微服务时的策略是使用自定义的策略，而不是默认的轮询
@RibbonClient(name = "YS-MEDICAL-PAYMENT",configuration = MySelfRule.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
