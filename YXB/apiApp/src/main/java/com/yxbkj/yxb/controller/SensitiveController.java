package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "SensitiveController",description = "敏感字过滤")
@RestController
@RequestMapping("/Sensitive")

public class SensitiveController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;

    @ApiOperation(value = "敏感字过滤",notes = "敏感字过滤")
    @GetMapping("/getSensitive")
    public Result<Map<String, Object>> getSensitive(String content){
        return serverSystemFeignClient.getSensitive(content);
    }

}
