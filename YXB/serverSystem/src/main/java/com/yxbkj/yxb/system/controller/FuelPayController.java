package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.FuePaylService;
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
@RestController
@RequestMapping("/fuelPayController")
public class FuelPayController {
    @Autowired
    private FuePaylService fuePaylService;
    @PostMapping("/payFuel")
    public Result<Map<String,Object>> payFuel(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "支付订单号",required = true)@RequestParam(value = "payOrderId",required = true)String payOrderId,
            @ApiParam(value = "source",required = true)@RequestParam(value = "source",required = true)String source
            ) {
        return fuePaylService.fuelPay(token,payOrderId,source);
    }
}
