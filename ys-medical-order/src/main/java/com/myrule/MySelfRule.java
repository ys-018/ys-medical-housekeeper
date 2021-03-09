package com.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如何替换 ribbon 的策略
 * 官方文档说明：
 *  这个自定义配置类不能放在 @ComponentScan 所扫描的当前包下以及子包下，
 *  否则我们自定义的这个配置类就会被所有的 Ribbon 客户端共享，达不到特殊化定制的目的了
 * 这里定义完成后需要在主启动类上加注解 @RibbonClient
 */

// 需要 springboot 整体识别
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new RandomRule(); // 定义随机
    }
}
