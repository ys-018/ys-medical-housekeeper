package com.ys;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker // 该注解用于熔断机制
public class PaymentApplictionHystrix {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplictionHystrix.class, args);
    }

    /**
     * 此配置是为了服务监控配置，与服务容错本身无关，springcloud 升级后的坑
     * ServletRegistrationBean 因为 springboot 的默认路径不是 "/hystrix.stream",
     * 只要在自己的项目里配置上下面的 servlet 就可以了
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
