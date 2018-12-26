package com.yxbkj.yxb.controller;

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
 * @author zy
 * @desc
 * @since
 */
@Api(value = "ReChargeController",description = "获取充值金额接口")
@RestController
@RequestMapping("/reChargeController")
public class ReChargeController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    @ApiOperation(value = "查询商品信息",notes = "查询商品信息")
    @PostMapping("/reChargeMoney")
    public Result<Map<String, Object>> reChargeMoney(
            @ApiParam(value = "电话",required = true)@RequestParam(value = "phone",required = true,defaultValue = "") String  phone
            ,@ApiParam(value = "金额",required = true)@RequestParam(value = "cardNum",required = true) Integer cardNum) {
        return serverSystemFeignClient.reChargeMoney(phone,cardNum);
    }
}
