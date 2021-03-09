package com.ys.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// 需要容器扫描的到相应的内容
@Component
public class MyLoadB implements LoadBalancer{

    // 初始原子类为 0
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current; // 当前的
        int next; // 累计的值（下次的初始值）
        do{
            current = this.atomicInteger.get();
            // 这里做三目运算是防止越界，因为轮询算法每次都是加 1
            next = (current >= 2147483647) ? 0 :current + 1;

            // 使用 CAS + 自旋锁 ，期望值 current、修改值 next
            // 这里为 current 和 next 不相等时继续循环
        }while (!this.atomicInteger.compareAndSet(current,next));

        System.out.println("**** next = " + next);
        return next;
    }

    // 负载均衡算法：rest 接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标，
    // 每次服务重启后 rest 接口计数从 1 开始
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
