package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerOrderFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(value = "CarorderController",description = "获取订单")
@RestController
@RequestMapping("/carorder")
public class CarorderController {
    @Autowired
    private ServerOrderFeignClient serverOrderFeignClient;
    @GetMapping("/carorder")
    @AccessToken
    @ApiOperation(value = "获取车险订单",notes = "获取车险订单")
    public Result<Map<String, Object>> selectOrder(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                   @ApiParam(value = "出单状态")@RequestParam(value = "orderStatus",required = false)String orderStatus,
                                                   @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
                                                   @ApiParam(value = "页码",required = true)@RequestParam(value = "limit")Integer limit,
                                                   @ApiParam(value = "条数",required = true)@RequestParam(value = "offset")Integer offset){
        return serverOrderFeignClient.carOrder(token, orderStatus,orderStatus , limit, offset);
    }
    @GetMapping("/yiliao")
    @AccessToken
    @ApiOperation(value = "获取医疗订单",notes = "获取医疗订单")
    public Result<Map<String, Object>> selectMedical(
             @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
             @ApiParam(value = "出单状态")@RequestParam(value = "orderStatus",required = false)String orderStatus,
             @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
             @ApiParam(value = "页码",required = true)@RequestParam(value = "limit")Integer limit,
             @ApiParam(value = "条数",required = true)@RequestParam(value = "offset")Integer offset)
    {
        return serverOrderFeignClient.selectMedical(token, orderStatus, payStatus, limit, offset);
    }
    @GetMapping("/accident")
    @AccessToken
    @ApiOperation(value = "获取意外险订单",notes = "获取意外险订单")
    public Result<Map<String, Object>> selectaccident(@ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
                                                      @ApiParam(value = "出单状态")@RequestParam(value = "orderStatus",required = false)String orderStatus,
                                                      @ApiParam(value = "支付状态")@RequestParam(value = "payStatus",required = false)String payStatus,
                                                      @ApiParam(value = "页码",required = true)@RequestParam(value = "limit")Integer limit,
                                                      @ApiParam(value = "条数",required = true)@RequestParam(value = "offset")Integer offset){
        return serverOrderFeignClient.selectAccident(token, orderStatus, payStatus, limit, offset);
    }
    @GetMapping("/delect")
    @ApiOperation(value = "删除订单",notes = "删除订单")
    public Result<Map<String,Object>> delect(@ApiParam(value = "订单ID",required = true)@RequestParam(value = "orderId")String orderId){
        return serverOrderFeignClient.delectOrder(orderId);
    }
}
