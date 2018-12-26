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
 * 手机直充
 * @author zy
 * @desc
 * @since
 */
@Api(value = "ReOnlineOrderController",description = "手机直充接口")
@RestController
@RequestMapping("/reOnlineOrderController")
public class ReOnlineOrderController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    @ApiOperation(value = "手机直充订单提交",notes = "手机直充订单提交")
    @PostMapping("/onlineOrder")
    public Result<Map<String,Object>> OnlineOrder(
            @ApiParam(value = "手机号",required = true)@RequestParam(value = "phone",required = true) String phone,
            @ApiParam(value = "充值金额",required = true)@RequestParam(value = "cardNum",required = true) int cardNum
    ) {
        return serverSystemFeignClient.OnlineOrder(phone,cardNum);
    }
}