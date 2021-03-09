package com.ys.controller;

import com.ys.entity.Payment;
import com.ys.lb.LoadBalancer;
import com.ys.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderController {

    // 单机版可以，集群不可以
    // public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://YS-MEDICAL-PAYMENT";

    @Resource
    private RestTemplate restTemplate;

    // 注入自定义轮询算法
    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/save")
    public CommonResult<Payment> save(Payment payment){
        log.info("---请");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/save", payment, CommonResult.class);
    }

    @GetMapping("/payment/postForEntity/save")
    public CommonResult<Payment> save_a(Payment payment){
        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL + "/payment/save", payment, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return CommonResult.errorMsg("插入失败");
        }
    }

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
    }

    @GetMapping("/payment/getPaymentById/getForEntity/{id}")
    public CommonResult<Payment> getPaymentById_a(@PathVariable("id") Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);

        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return CommonResult.errorMsg("获取失败");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLoadB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("YS-MEDICAL-PAYMENT");
        if(instances == null || instances.size() <= 0){
            return null;
        }

        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/getPaymentLB", String.class);
    }
}
