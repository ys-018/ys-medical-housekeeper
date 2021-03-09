package com.ys.controller;

import com.ys.entity.Payment;
import com.ys.service.PaymentFeignService;
import com.ys.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/orderfeign")
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/getpaymentbyid/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    // 模拟测试超时，我们模拟超时定义等待 3 秒
    // openfeign 自带了 ribbon ,ribbon 客户端一般默认等待 1 秒
    @GetMapping("/feign/timeout")
    public CommonResult<String> paymentFeignTimeOut(){
        return paymentFeignService.paymentFeignTimeout();
    }
}
