package com.ys.service.hystrix;

import com.ys.service.OrderHystrixService;
import org.springframework.stereotype.Component;

@Component
public class OrderHystrix implements OrderHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务异常";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务异常";
    }
}
