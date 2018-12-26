package com.yxbkj.yxb.controller;

import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机充值回调接口
 * @author zy
 * @desc
 * @since
 */
@Api(value = "ReNotifyController",description = "手机充值回调接口")
@RestController
@RequestMapping("/reNotifyController")
public class ReNotifyController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    /**
     * 回调方法
     *
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "回调方法",notes = "回调方法")
    @RequestMapping(value = "/callerBack", method = RequestMethod.POST)
    public String callerBack(@RequestParam("sporder_id") String sporder_id, @RequestParam("orderid") String orderid,
                           @RequestParam("sta") String sta, @RequestParam("sign") String sign) {
        return serverSystemFeignClient.callerBack(sporder_id,orderid,sta,sign);
    }
}
