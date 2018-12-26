package com.yxbkj.yxb.system.controller;


import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.FuelCardService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 加油卡表 前端控制器
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/fuelCardController")
public class FuelCardController {
    @Autowired
    private FuelCardService fuelCardService;
    /**
     * <p>
     * 绑定加油卡
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @PostMapping("/bindFuelCard")
    public Result<Map<String,Object>> bindFuelCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return fuelCardService.bindFuelCard(token,cardNumber);
    }
    /**
     * <p>
     * 获取加油卡列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @GetMapping("/getFuelCardList")
    public Result<Map<String,Object>> getFuelCardList(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token
    ) {
        return fuelCardService.getFuelCardList(token);
    }
    /**
     * <p>
     * 加油卡解绑
     * </p>
     *
     * @author ZY
     * @since 2018-12-14
     */
    @PostMapping("/unbindCard")
    public Result<Map<String,Object>> unbindCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return fuelCardService.unbindCard(token,cardNumber);
    }
    /**
     * <p>
     * 切换加油卡
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @PostMapping("/exChangeCard")
    public Result<Map<String,Object>> exChangeCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return fuelCardService.exChangeCard(token,cardNumber);
    }
}

