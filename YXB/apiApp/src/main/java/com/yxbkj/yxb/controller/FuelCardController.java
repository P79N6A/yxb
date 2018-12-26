package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "FuelCardController",description = "加油卡相关接口")
@RestController
@RequestMapping("/fuelCardController")
public class FuelCardController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * <p>
     * 绑定加油卡
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @ApiOperation(value = "绑定加油卡",notes = "绑定加油卡")
    @PostMapping("/bindFuelCard")
    public Result<Map<String,Object>> bindFuelCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return serverSystemFeignClient.bindFuelCard(token,cardNumber);
    }
    /**
     * <p>
     * 获取加油卡列表
     * </p>
     *
     * @author ZY
     * @since 2018-12-13
     */
    @ApiOperation(value = "获取加油卡列表",notes = "获取加油卡列表")
    @GetMapping("/getFuelCardList")
    public Result<Map<String,Object>> getFuelCardList(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token
    ) {
        return serverSystemFeignClient.getFuelCardList(token);
    }
    /**
     * <p>
     * 加油卡解绑
     * </p>
     *
     * @author ZY
     * @since 2018-12-14
     */
    @ApiOperation(value = "加油卡解绑",notes = "加油卡解绑")
    @PostMapping("/unbindCard")
    public Result<Map<String,Object>> unbindCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return serverSystemFeignClient.unbindCard(token,cardNumber);
    }
    /**
     * <p>
     * 切换加油卡
     * </p>
     *
     * @author ZY
     * @since 2018-12-17
     */
    @ApiOperation(value = "切换加油卡",notes = "切换加油卡")
    @PostMapping("/exChangeCard")
    public Result<Map<String,Object>> exChangeCard(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token",required = true)String token,
            @ApiParam(value = "加油卡号",required = true)@RequestParam(value = "cardNumber",required = true)String cardNumber
    ) {
        return serverSystemFeignClient.exChangeCard(token,cardNumber);
    }
}

