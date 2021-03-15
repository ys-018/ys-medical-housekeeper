package com.ys.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
// 需要手动刷新，在 cmd 发送 post 请求 curl -X POST "http://localhost:3355/actuator/refresh"
@RefreshScope // 刷新动态配置（动态获取 github 上的统一配置）
@RequestMapping("/config")
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
