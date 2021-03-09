package com.ys.controller;

import com.ys.entity.Payment;
import com.ys.service.PaymentService;
import com.ys.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    // 注入 DiscoveryClient 服务发现客户端，注意这里的 jar 包，是 cloud 的
    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/save")
    public CommonResult save(@RequestBody Payment payment){
        int result = paymentService.save(payment);
        // 注意，这里的日志打印是 lombok 的，需要安装插件
        log.info("---插入数据~~~~来了 " + serverPort);
        if(result > 0){
            return CommonResult.success(result);
        }else{
            return CommonResult.errorMsg("插入失败");
        }
    }

    @GetMapping("/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("---获取数据~~~~~来了 " + serverPort);
        if(payment != null){
            return CommonResult.success(payment);
        }else{
            return CommonResult.errorMsg("获取失败");
        }
    }

    @GetMapping("/discovery")
    public Object discovery(){
        // 获取 eureka 下的所有服务
        List<String> services = discoveryClient.getServices();
        for (String s : services) {
            log.info("**** " + s + "****");
        }

        // 获取指定 serviceId 下的服务信息
        List<ServiceInstance> instances = discoveryClient.getInstances("YS-MEDICAL-PAYMENT");
        for (ServiceInstance instance : instances){
            log.info("*** " + instance.getServiceId() + "\t" + instance.getHost() + "\t" +
                    "" + instance.getPort() + "\t" + instance.getUri() + "***");
        }
        return discoveryClient;
    }

    // 手写轮询算法
    @GetMapping("/getPayment/LB")
    public String getPaymentLB(){
        return serverPort;
    }

    //模拟请求长流程（即执行代码需要 3 秒）
    @GetMapping("/feign/timeout")
    public CommonResult<String> paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.success(serverPort);
    }
}
