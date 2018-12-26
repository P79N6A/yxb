package com.yxbkj.yxb.system.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.JiaYouCardService;
import com.yxbkj.yxb.util.JiaYouCard.JiaYouCard;
import com.yxbkj.yxb.util.MD5Util;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 加油卡充值相关
 * @author zy
 * @desc
 * @since
 */
@RestController
@RequestMapping("/jiaYouCard")
public class JiaYouCardController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JiaYouCardService jiaYouCardService;

    /**
     * 提交充值订单
     *
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "提交充值订单",notes = "提交充值订单")
    @PostMapping("/onLineOrder")
    public Result<Map<String, Object>> onLineOrder(
            @ApiParam(value = "产品id", required = true) @RequestParam(value = "proid", required = true) int proid,
            @ApiParam(value = "充值数量或金额", required = true) @RequestParam(value = "cardNum", required = true) String cardNum,
            @ApiParam(value = "加油卡卡号", required = true) @RequestParam(value = "game_userid", required = true) String game_userid,
            @ApiParam(value = "持卡人手机号码", required = true) @RequestParam(value = "gasCardTel", required = true) String gasCardTel,
            @ApiParam(value = "持卡人姓名", required = false) @RequestParam(value = "gasCardName", required = false) String gasCardName,
            @ApiParam(value = "加油卡类型", required = false) @RequestParam(value = "chargeType", required = false) int chargeType,
            @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId
    ) {
        return jiaYouCardService.onLineOrder(proid, cardNum, game_userid, gasCardTel, gasCardName, chargeType,orderId);
    }

    /**
     * 查询订单状态
     *
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "查询订单状态",notes = "查询订单状态")
    @PostMapping("/orderSta")
    public Result<Map<String, Object>> orderSta(
            @ApiParam(value = "电话号码", required = true) @RequestParam(value = "phone", required = true) String phone
    ) {
        return jiaYouCardService.orderSta(phone);
    }

    @ApiOperation(value = "查询订单状态(订单号)",notes = "查询订单状态(订单号)")
    @PostMapping("/orderStaOrderId")
    public Result<Map<String, Object>> orderStaOrderId(
            @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId
    ) {
        return jiaYouCardService.orderStaOrderId(orderId);
    }
    /**
     *根据日期查询订单
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "根据日期查询订单",notes = "根据日期查询订单")
    @PostMapping("/orderByDate")
    public Result<Map<String,Object>> orderByDate(
            @ApiParam(value = "当前页",required = false)@RequestParam(value = "page",required = false)Integer page,
            @ApiParam(value = "每页条数",required = false)@RequestParam(value = "pageSize",required = false)Integer pageSize,
            @ApiParam(value = "开始时间",required = false)@RequestParam(value = "startTime",required = false)@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")String startTime,
            @ApiParam(value = "结束时间",required = false)@RequestParam(value = "endTime",required = false)@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")String endTime
            ) {
        return jiaYouCardService.queryOrder(page,pageSize,startTime,endTime);
    }
    /**
     *查询账户余额
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "查询账户余额",notes = "查询账户余额")
    @GetMapping("/yuE")
    public Result<Map<String,Object>> yuE() {
        return jiaYouCardService.yuE();
    }
}
