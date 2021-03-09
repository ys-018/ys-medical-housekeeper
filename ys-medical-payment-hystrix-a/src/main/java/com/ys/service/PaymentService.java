package com.ys.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id：" + id;
    }

    // commandProperties 表示该线程的超时时间为 3 秒，出错之后找 fallbackMethod 指定的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOut_Handler", commandProperties = {@HystrixProperty(
            name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.MILLISECONDS.sleep(2900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut, id：" + id;
    }

    public String paymentInfo_TimeOut_Handler(Integer id){
        return "方法 paymentInfo_TimeOut() 8005 请求出现异常，熔断降级到这里了，哦哦哦哦哦";
    }


    //*******************************************************************
    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少次后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Long id){
        if(id < 0){
            throw new RuntimeException("**** id 不能为负数 ****");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + " 调用成功 " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Long id){
        return " 谁让你传 id 是负数了 ，大胆";
    }

}
