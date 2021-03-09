package com.ys.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    //获取注册中心的所有有效的服务总数
    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
