package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerProductFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "PremiumController",description = "获取保费")
@RestController
@RequestMapping("/premium")
public class PremiumController {
    @Autowired
    private ServerProductFeignClient serverProductFeignClient;

    @ApiOperation(value = "获取保费详情", notes = "获取保费详情")
    @GetMapping("/premium")
    public Result<Map<String, Object>> premium(String productId, String minAge, String maxAge, String socialSecurityType, String insuranceScale, String timeLimit) {
        return serverProductFeignClient.premium(productId, minAge, maxAge, socialSecurityType, insuranceScale, timeLimit);
    }
}