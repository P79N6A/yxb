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
 * <p>
 * 充值明细订单 前端控制器
 * </p>
 *
 * @author ZY
 * @since 2018-12-19
 */
@Api(value = "FuelRechargeOrderController",description = "充值明细订单")
@RestController
@RequestMapping("/fuelRechargeOrder")
public class FuelRechargeOrderController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * <p>
     * 生成充值订单
     * </p>
     *
     * @author ZY
     * @since 2018-12-19
     */
    /*@PostMapping("/addFuelRechargeOrders")
    public Result<Map<String,Object>> addFuelRechargeOrders(@ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ) {
        return serverSystemFeignClient.addFuelRechargeOrders(payOrderId);
    }*/
    @PostMapping("/getRechargeOrderList")
    public Result<Map<String,Object>> getRechargeOrderList(
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ) {
        return serverSystemFeignClient.getRechargeOrderList(payOrderId);
    }
    @PostMapping("/updateStatus")
    public Result<Map<String,Object>> updateStatus(String rechargeOrderId) {
        return serverSystemFeignClient.updateStatus(rechargeOrderId);
    }
}

