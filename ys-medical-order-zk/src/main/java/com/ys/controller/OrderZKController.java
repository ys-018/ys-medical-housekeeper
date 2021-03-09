package com.ys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/orderzk")
public class OrderZKController {

    public static final String INVOKE_URL = "http://ys-medical-payment-zk";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/paymentinfo")
    public String paymentInfo(){
        String result = restTemplate.getForObject(INVOKE_URL + "/paymentzk/getZK", String.class);
        return result;
    }
}
