package com.ys.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Title: MyLogGateWayFilter
 * @Description: 全局过滤器
 * @author
 * @Param
 * @date 2021/3/6 21:29
 * @return
 * @throws
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**** come in MyLogGateWayFilter " + new Date() + "****");
        // 获取 http 中的值
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        // 如果为空则踢出去，否则继续向下一个 filter 传递
        if(username == null){
            log.info("**** 用户名不能为 null ****");
            // 返回一个失败的状态
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    // 表示的是加载过滤器的顺序，一般数字越小优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
