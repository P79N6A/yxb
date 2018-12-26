package com.yxbkj.yxb.system.controller;


import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.product.FuelDiscount;
import com.yxbkj.yxb.system.service.FuelDiscountService;
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
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/fuelDiscount")
public class FuelDiscountController {
    @Autowired
    private FuelDiscountService fuelDiscountService;
    /**
     * <p>
     * 获取折扣列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @GetMapping("/getDiscountList")
    public Result<List<FuelDiscount>> getDiscountList(
            @ApiParam(value = "充值类型",required = true)@RequestParam(value = "disCountType",required = true)String disCountType
    ) {
        return fuelDiscountService.getDiscountList(disCountType);
    }
    /**
     * <p>
     * 添加折扣
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @PostMapping("/incrDiscount")
    public Result<Map<String,Object>> incrDiscount(
            @ApiParam(value = "折扣类型",required = true)@RequestParam(value = "discountType",required = true)String discountType,
            @ApiParam(value = "到账总月数",required = true)@RequestParam(value = "months",required = true)Integer months,
            @ApiParam(value = "折扣大小",required = true)@RequestParam(value = "discountNum",required = true)Double discountNum,
     
            @ApiParam(value = "成本比例",required = true)@RequestParam(value = "costRate",required = true)Double costRate,
            @ApiParam(value = "佣金比例",required = true)@RequestParam(value = "commissionRate",required = true)Double commissionRate,
            @ApiParam(value = "犹豫期",required = true)@RequestParam(value = "hesiPeriod",required = true)Integer hesiPeriod
    ) {
        return fuelDiscountService.incrDiscount(discountType,months,discountNum,costRate,commissionRate,hesiPeriod);
    }
}

