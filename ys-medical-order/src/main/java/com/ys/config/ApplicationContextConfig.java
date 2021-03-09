package com.ys.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
@Configuration
public class ApplicationContextConfig {

    // @LoadBalanced 使用 @LoadBalanced 注解赋予 RestTemplate 负载均衡的能力
    @Bean
//    @LoadBalanced 需要测试手写的轮询算法，所以暂且注释
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
