package com.yxbkj.yxb.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付订单 前端控制器
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
@Api(value = "FuelPayOrderController",description = "支付订单相关接口")
@RestController
@RequestMapping("/fuelPayOrder")
public class FuelPayOrderController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * <p>
     * 生成支付订单
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @ApiOperation(value = "生成支付订单",notes = "生成支付订单")
    @PostMapping("/addPayOrder")
    public Result<Map<String,Object>> addPayOrder(
            @ApiParam(value = "折扣大小",required = true)@RequestParam(value = "discountNum",required = true)Double discountNum,
            @ApiParam(value = "到账总月数",required = true)@RequestParam(value = "months",required = true)Integer months,
            @ApiParam(value = "支付总金额",required = true)@RequestParam(value = "amount",required = true)Double amount,
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "支付类型",required = true)@RequestParam(value = "payType",required = true)String payType,
            @ApiParam(value = "订单类型",required = true)@RequestParam(value = "orderType",required = true)String orderType,
            @ApiParam(value = "选中加油卡卡号",required = true)@RequestParam(value = "fuelNumber",required = true)String fuelNumber,
            @ApiParam(value = "每月到账金额",required = true)@RequestParam(value = "monthAmount",required = true)Double monthAmount,
			@ApiParam(value = "订单来源(APP/微信)",required = true)@RequestParam(value = "orderSource",required = true)String orderSource

    ) {
        return serverSystemFeignClient.addPayOrder(discountNum,months,amount,token,payType,orderType,fuelNumber,monthAmount,orderSource);
    }
    /**
     * <p>
     * 获取支付订单列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @ApiOperation(value = "获取支付订单列表",notes = "获取支付订单列表")
    @PostMapping("/payOrderList")
    public Result<Page<Map<String,Object>>> payOrderList(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "页面条数",required = true)@RequestParam(value = "limit",required = true)Integer limit,
            @ApiParam(value = "当前页",required = true)@RequestParam(value = "offset",required = true)Integer offset,
            @ApiParam(value = "支付状态",required = true)@RequestParam(value = "payStatus",required = true)String payStatus
    ){
        return serverSystemFeignClient.payOrderList(token,limit,offset,payStatus);
    }
    /**
     * <p>
     * 修改支付订单状态
     * </p>
     *
     * @author ZY
     * @since 2018-12-19
     */
    @ApiOperation(value = "修改支付订单状态",notes = "修改支付订单状态")
    @PostMapping("/updatePayOrderStatus")
    public Result<Map<String,Object>> updatePayOrderStatus(
            @ApiParam(value = "订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ){
        return serverSystemFeignClient.updatePayOrderStatus(payOrderId);
    }
}

