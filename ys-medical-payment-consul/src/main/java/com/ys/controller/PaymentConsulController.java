package com.ys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentConsulController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/getconsul")
    public String paymentConsul(){
        return "springcloud with consul " + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
