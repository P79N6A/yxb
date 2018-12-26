package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.FuelRechargeOrder;
import com.yxbkj.yxb.feign.ServerOrderFeignClient;
import com.yxbkj.yxb.feign.ServerSystemFeignClient;
import com.yxbkj.yxb.util.JiaYouCard.JiaYouCard;
import com.yxbkj.yxb.util.MD5Util;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.aliyun.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 加油卡充值相关
 * @author zy
 * @desc
 * @since
 */
@Api(value = "JiaYouCardController",description = "加油卡充值相关")
@RestController
@RequestMapping("/jiaYouCard")
public class JiaYouCardController {
    @Autowired
    private ServerSystemFeignClient serverSystemFeignClient;
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 提交加油卡订单充值
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "提交充值订单",notes = "提交充值订单")
    @PostMapping("/onLineOrder")
    public Result<Map<String,Object>> onLineOrder(
           @ApiParam(value = "产品id",required = true)@RequestParam(value = "proid",required = true)int proid,
           @ApiParam(value = "充值数量或金额",required = true)@RequestParam(value = "cardNum",required = true)String cardNum,
           @ApiParam(value = "加油卡卡号",required = true)@RequestParam(value = "game_userid",required = true)String game_userid,
           @ApiParam(value = "持卡人手机号码",required = true)@RequestParam(value = "gasCardTel",required = true)String gasCardTel,
           @ApiParam(value = "持卡人姓名",required = false)@RequestParam(value = "gasCardName",required = false)String gasCardName,
           @ApiParam(value = "加油卡类型",required = true)@RequestParam(value = "chargeType",required = true)int chargeType,
           @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId

    ) {
        return serverSystemFeignClient.onLineOrder(proid, cardNum, game_userid, gasCardTel, gasCardName, chargeType,orderId);
    }
    /**
     * 查询订单状态
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "查询订单状态(手机号)",notes = "查询订单状态(手机号)")
    @PostMapping("/orderSta")
    public Result<Map<String,Object>> orderSta(
            @ApiParam(value = "电话号码",required = true)@RequestParam(value = "phone",required = true)String phone
    ) {
        return serverSystemFeignClient.orderStaJiaYou(phone);
    }
    @ApiOperation(value = "查询订单状态(订单号)",notes = "查询订单状态(订单号)")
    @PostMapping("/orderStaOrderId")
    public Result<Map<String, Object>> orderStaOrderId(
            @ApiParam(value = "订单号", required = true) @RequestParam(value = "orderId", required = true) String orderId
    ) {
        return serverSystemFeignClient.orderStaOrderId(orderId);
    }
    @ApiOperation(value = "根据日期查询订单",notes = "根据日期查询订单")
    @PostMapping("/orderByDate")
    public Result<Map<String,Object>> orderByDate(
            @ApiParam(value = "当前页",required = false)@RequestParam(value = "page",required = false)Integer page,
            @ApiParam(value = "每页条数",required = false)@RequestParam(value = "pageSize",required = false)Integer pageSize,
            @ApiParam(value = "开始时间",required = false)@RequestParam(value = "startTime",required = false)@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")String startTime,
            @ApiParam(value = "结束时间",required = false)@RequestParam(value = "endTime",required = false)@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")String endTime
    ) {
        if(startTime == null || startTime.equals("")) {
            startTime = StringUtil.getFirstDay();
        }
        if(endTime == null || endTime.equals("")) {
            endTime = StringUtil.getLastDay();
        }
        return serverSystemFeignClient.orderByDate(page,pageSize,startTime,endTime);
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
        return serverSystemFeignClient.yuE();
    }
    /**
     * 回调方法
     *
     * @author zy
     * @desc
     * @since
     */
    @ApiOperation(value = "回调方法",notes = "回调方法")
    @RequestMapping(value = "/callerBack", method = RequestMethod.POST)
    public Result<Map<String,Object>> callerBack(@RequestParam("sporder_id") String sporder_id, @RequestParam("orderid") String orderid,
                           @RequestParam("sta") String sta, @RequestParam("sign") String sign,@RequestParam("err_msg")String err_msg) {
        Result<Map<String,Object>> result = null;
        String local_sign = MD5Util.strToMD5(JiaYouCard.APPKEY + sporder_id + orderid);//本地sign校验值
        logger.info("充值返回参数" + orderid + sign);
        if (sign.equals(local_sign)) {
            if ("1".equals(sta)) {
                //充值成功，根据自身业务逻辑进行后续处理
                serverSystemFeignClient.updateStatus(orderid);
                logger.info("充值成功" + err_msg);
                Result<Map<String, FuelRechargeOrder>> orderCharge = serverSystemFeignClient.getOrderCharge(orderid);
                Map<String, FuelRechargeOrder> data = orderCharge.getData();
                //FuelRechargeOrder fuelRecharge = (FuelRechargeOrder) data.get("fuelRecharge");
                FuelRechargeOrder fuelRecharge = data.get("fuelRecharge");
                String fuelNumber = fuelRecharge.getFuelNumber();
                int length = fuelNumber.length();
                String substring = fuelNumber.substring(length - 4);
                BigDecimal amount = fuelRecharge.getAmount();
                int amountInt = amount.intValue();
                String amountStr = amountInt + "";
                Long orderMemberPhone = fuelRecharge.getOrderMemberPhone();
                String phone = orderMemberPhone + "";
                try {
                    SendSmsResponse sendSmsResponse = SmsUtils.sendSmsJiaYou(phone, amountStr, substring);
                    logger.info("短信发送响应" + sendSmsResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("短信发送失败");
                }
                return result = new Result<>(Code.SUCCESS,"充值成功",null,Code.IS_ALERT_YES);
            } else if ("9".equals(sta)) {
                //充值失败,根据自身业务逻辑进行后续处理
                logger.info("支付订单变更" + err_msg);
                return result = new Result<>(Code.FAIL,"充值失败",null,Code.IS_ALERT_YES);
            }
        }
        logger.info("sign错误" + err_msg);
        return result = new Result<>(Code.FAIL,"充值失败",null,Code.IS_ALERT_YES);
    }
}
