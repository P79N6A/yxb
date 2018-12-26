package com.yxbkj.yxb.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.FuelPayOrderService;
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
@RestController
@RequestMapping("/fuelPayOrder")
public class FuelPayOrderController {
    @Autowired
    private FuelPayOrderService fuelPayOrderService;
    /**
     * <p>
     * 生成支付订单
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
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
        return fuelPayOrderService.addPayOrder(discountNum,months,amount,token,payType,orderType,fuelNumber,monthAmount,orderSource);
    }
    /**
     * <p>
     * 获取支付订单列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @PostMapping("/payOrderList")
    public Result<Page<Map<String,Object>>> payOrderList(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "页面条数",required = true)@RequestParam(value = "limit",required = true)Integer limit,
            @ApiParam(value = "当前页",required = true)@RequestParam(value = "offset",required = true)Integer offset,
            @ApiParam(value = "支付状态",required = true)@RequestParam(value = "payStatus",required = true)String payStatus
    ){
        return fuelPayOrderService.payOrderList(token,limit,offset,payStatus);
    }
    /**
     * <p>
     * 修改支付订单状态
     * </p>
     *
     * @author ZY
     * @since 2018-12-19
     */
    @PostMapping("/updatePayOrderStatus")
    public Result<Map<String,Object>> updatePayOrderStatus(
            @ApiParam(value = "订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId
    ){
        return fuelPayOrderService.updatePayOrderStatus(payOrderId);
    }
}

