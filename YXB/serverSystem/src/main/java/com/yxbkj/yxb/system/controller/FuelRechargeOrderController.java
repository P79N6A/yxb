package com.yxbkj.yxb.system.controller;


import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.FuelRechargeOrder;
import com.yxbkj.yxb.system.service.FuelRechargeOrderService;
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
@RestController
@RequestMapping("/fuelRechargeOrder")
public class FuelRechargeOrderController {
    @Autowired
    private FuelRechargeOrderService fuelRechargeOrderService;
    @PostMapping("/addFuelRechargeOrders")
    public Result<Map<String,Object>> addFuelRechargeOrders(
        @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ) {
        return fuelRechargeOrderService.addFuelRechargeOrders(payOrderId);
    }
    @PostMapping("/getRechargeOrderList")
    public Result<Map<String,Object>> getRechargeOrderList(
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ) {
        return fuelRechargeOrderService.getRechargeOrderList(payOrderId);
    }
    @PostMapping("/updateStatus")
    public Result<Map<String,Object>> updateStatus(String rechargeOrderId) {
        return fuelRechargeOrderService.updateStatus(rechargeOrderId);
    }
    @PostMapping("/getOrderCharge")
    public Result<Map<String,FuelRechargeOrder>> getOrderCharge(String rechargeOrderId) {
        return fuelRechargeOrderService.getPayRechargeOrderByOrderId(rechargeOrderId);
    }
}

