package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.OrderStaService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/reOrderStaController")
public class ReOrderStaController {
    @Autowired
    private OrderStaService orderStaService;
    @ApiOperation(value = "查询订单",notes = "查询订单")
    @PostMapping("/orderSta")
    public Result<Map<String,Object>> orderSta(
            @ApiParam(value = "手机号",required = true)@RequestParam(value = "phone",required = true)String phone
    ) {
       return orderStaService.orderSta(phone);
    }
}
