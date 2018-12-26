package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
@Api(value = "FuelPayController ",description = "加油卡易宝支付相关接口")
@RestController
@RequestMapping("/fuelPayController")
public class FuelPayController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    @PostMapping("/payFuel")
    public Result<Map<String,Object>> payFuel(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId,
            @ApiParam(value = "source",required = true)@RequestParam(value = "source",required = true)String source
            ) {
        return serverSystemFeignClient.payFuel(token,payOrderId,source);
    }
}
