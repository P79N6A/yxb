package com.yxbkj.yxb.controller;


import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.product.FuelDiscount;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 折扣表 前端控制器
 * </p>
 *
 * @author ZY
 * @since 2018-12-14
 */
@Api(value = "FuelDiscountController",description = "折扣相关接口")
@RestController
@RequestMapping("/fuelDiscount")
public class FuelDiscountController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * <p>
     * 获取折扣列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @ApiOperation(value = "获取折扣列表",notes = "获取折扣列表")
    @GetMapping("/getDiscountList")
    public Result<List<FuelDiscount>> getDiscountList(
            @ApiParam(value = "充值类型",required = true)@RequestParam(value = "disCountType",required = true)String disCountType
    )  {
        return serverSystemFeignClient.getDiscountList(disCountType);
    }
    /**
     * <p>
     * 添加折扣
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @ApiOperation(value = "添加折扣",notes = "添加折扣")
    @PostMapping("/incrDiscount")
    public Result<Map<String,Object>> incrDiscount(
            @ApiParam(value = "折扣类型",required = true)@RequestParam(value = "discountType",required = true)String discountType,
            @ApiParam(value = "到账总月数",required = true)@RequestParam(value = "months",required = true)Integer months,
            @ApiParam(value = "折扣大小",required = true)@RequestParam(value = "discountNum",required = true)Double discountNum,
            
            @ApiParam(value = "成本比例",required = true)@RequestParam(value = "costRate",required = true)Double costRate,
            @ApiParam(value = "佣金比例",required = true)@RequestParam(value = "commissionRate",required = true)Double commissionRate,
            @ApiParam(value = "犹豫期",required = true)@RequestParam(value = "hesiPeriod",required = true)Integer hesiPeriod
            ) {
        return serverSystemFeignClient.incrDiscount(discountType,months,discountNum,costRate,commissionRate,hesiPeriod);
    }
}

