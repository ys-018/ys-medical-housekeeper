package com.ys.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ys.service.OrderHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

// DefaultProperties 全局兜底方法，如果方法上没有 HystrixCommand 注解中的内容，就默认找 DefaultProperties 注解指定的方法
@RestController
@Slf4j
@RequestMapping("/order")
@DefaultProperties(defaultFallback = "payment_Global_Fallback_Method")
public class OrderController {

    @Resource
    private OrderHystrixService orderHystrixService;

    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_timeout_fallback", commandProperties = {@HystrixProperty(
//            name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    @HystrixCommand
    public String paymentInfo_timeout(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    public String paymentInfo_timeout_fallback(@PathVariable("id") Integer id){
        return "84 端口报错返回的信息，方法为 paymentInfo_timeout_fallback()";
    }

    // 全局 fallback 方法
    public String payment_Global_Fallback_Method(){
        return "出错了，这里是 84 端口的全局兜底方法 payment_Global_Fallback_Method() ";
    }
}
