package com.yxbkj.yxb.order.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.order.service.*;
import com.yxbkj.yxb.util.AccessToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/carorder")
public class CarorderController {
    @Autowired
    private CarorderService carorderService;
    @Autowired
    private ProtectorderService protectorderService;
    @Autowired
    private AccidentService accidentService;
    @Autowired
    private DelectService delectService;
    @Autowired
    private FuelRechargeOrderService fuelRechargeOrderService;

    @AccessToken
    @GetMapping("/carorder")
    @ApiOperation(value = "获取订单",notes = "获取订单")
    public Result<Map<String, Object>> selectOrder(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token, String payStatus, String orderStatus, Integer limit, Integer offset){
        return carorderService.selectOrder(token, orderStatus, payStatus, limit, offset);
    }

    @AccessToken
    @GetMapping("/yiliao")
    @ApiOperation(value = "获取医疗订单",notes = "获取医疗订单")
    public Result<Map<String, Object>> selectMedical(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token, String payStatus, String orderStatus, Integer limit, Integer offset){
        return protectorderService.selectMedical(token, orderStatus, payStatus, limit, offset);
    }

    @AccessToken
    @GetMapping("/accident")
    @ApiOperation(value = "获取意外险订单",notes = "获取意外险订单")
    public Result<Map<String, Object>> selectaccident(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token, String orderStatus, String payStatus, Integer limit, Integer offset){
        return accidentService.selectAccident(token, orderStatus, payStatus, limit, offset);
    }

    @GetMapping("/delect")
    @ApiOperation(value = "删除订单",notes = "删除订单")
    public Result<Map<String,Object>> delect(String orderId){
        return delectService.delectOrder(orderId);
    }
    @GetMapping("/aaa")
    @ApiOperation(value = "删除订单",notes = "删除订单")
    public Result<Map<String,Object>> aaa(String orderId){
        return fuelRechargeOrderService.addFuelRechargeOrders(orderId);
    }
}
