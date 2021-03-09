package com.ys.service;

import com.ys.entity.Payment;
import com.ys.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "YS-MEDICAL-PAYMENT")
public interface PaymentFeignService {

    @GetMapping("/payment/getPaymentById/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    CommonResult<String> paymentFeignTimeout();
}
