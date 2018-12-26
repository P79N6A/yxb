


package com.yxbkj.yxb.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.FuelPayOrder;
import com.yxbkj.yxb.entity.order.OrderPayment;
import com.yxbkj.yxb.system.mapper.FuelPayOrderMapper;
import com.yxbkj.yxb.system.mapper.MemberInfoMapper;
import com.yxbkj.yxb.system.mapper.OrderPaymentMapper;
import com.yxbkj.yxb.system.service.FuePaylService;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.wxpay.util.PayConfigUtil;
import com.yxbkj.yxb.util.wxpay.util.WeiXinPayService;
import com.yxbkj.yxb.util.yeepay.YeePayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//import com.yxbkj.yxb.order.mapper.OrderPaymentMapper;

//-------------------------------------
@Service
public class FuelPayServiceImpl implements FuePaylService {
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private OrderPaymentMapper orderPaymentMapper;
    @Autowired
    private FuelPayOrderMapper fuelPayOrderMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result<Map<String, Object>> fuelPay(String token, String payOrderId, String source) {
        Result<Map<String, Object>> result = null;
        String memberId = redisTemplateUtils.getStringValue(token);
        if (memberId == null) {
            return result = new Result<>(Code.FAIL, "token为空或者已过期", null, Code.IS_ALERT_YES);
        }
        if (payOrderId == null) {
            return result = new Result<>(Code.FAIL, "订单号为空", null, Code.IS_ALERT_YES);
        }
        MemberInfo memberInfo1 = new MemberInfo();
        memberInfo1.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        memberInfo1.setMemberId(memberId);
        MemberInfo memberInfo = memberInfoMapper.selectOne(memberInfo1);
        if (memberInfo == null) {
            return new Result<>(Code.FAIL, "不存在当前用户!", null, Code.IS_ALERT_YES);
        }
        FuelPayOrder fuelPayOrder = new FuelPayOrder();
        fuelPayOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_NO);
        fuelPayOrder.setPayOrderId(payOrderId);
        fuelPayOrder.setOrderMemberId(memberId);
        FuelPayOrder fuelPayOrder1 = fuelPayOrderMapper.selectOne(fuelPayOrder);
        if (fuelPayOrder1 == null) {
            return result = new Result<>(Code.FAIL, "订单不存在", null, Code.IS_ALERT_YES);
        }
        /*//获取用户资产信息
        EntityWrapper<MemberProperty> wrapper_property = new EntityWrapper<>();
        wrapper_property.eq("member_id", memberId);
        MemberProperty memberProperty = memberPropertyMapper.selectOne(wrapper_property);
        if (memberProperty == null) {
            return new Result<>(Code.FAIL, "无法获取用户资产信息!", null, Code.IS_ALERT_YES);
        }*/
        BigDecimal amount = fuelPayOrder1.getAmount();
        Map<String, Object> map = new HashMap<>();
        //2 判断用户余额
        if (fuelPayOrder1.getPayType().equals(YxbConstants.PAY_WAY_YIBAO)) {//易宝支付
            //String payment_id = System.currentTimeMillis() + "";
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setId(StringUtil.getUuid());
            orderPayment.setOrderId(payOrderId);
            orderPayment.setMemberId(memberId);
            orderPayment.setCreateTime(YxbConstants.sysDate());
            orderPayment.setContent("JIAYOU");
            orderPayment.setRemark("易宝订单-加油卡充值");
            orderPayment.setPayWay(YxbConstants.PAY_WAY_YIBAO);
            orderPayment.setPayAmount(amount.toString());
            orderPayment.setOrderAmount(amount.toString());
            orderPayment.setStatus(YxbConstants.ORDER_PAY_NO);//未支付
            Integer insert = orderPaymentMapper.insert(orderPayment);
            if (insert < 1) {
                return new Result<>(Code.FAIL, "获取易宝链接失败!无法生成付款订单!", null, Code.IS_ALERT_YES);
            }
            String payUrl = YeePayUtils.getPayUrl(payOrderId, amount + "", "JIAYOU", "易小保加油卡充值", source);
            if (payUrl == null) {
                return new Result<>(Code.FAIL, "获取易宝链接失败!", null, Code.IS_ALERT_YES);
            }
            Map<String, Object> yibaoParam = new HashMap<>();
            yibaoParam.put("redirectUrl", payUrl);
            logger.info("易宝链接地址" + payUrl);
            logger.info("金额" + amount.intValue());
            return new Result<>(Code.SUCCESS, "获取易宝链接成功!", yibaoParam, Code.IS_ALERT_NO);

        } else if (fuelPayOrder1.getPayType().equals(YxbConstants.PAY_WAY_WX)) {//微信支付
            //  公众号 H5 发起的
            if (source.equals(YxbConstants.MEMBER_SOURCE_WX)) {
                try {
                    //String payment_id = System.currentTimeMillis() + "";
                    String clientIP = HttpKit.getClientIP();
                    logger.info("客户端IP" + PayConfigUtil.NOTIFY_URL_RECHARGE);
                    BigDecimal bigDecimal = new BigDecimal("100");
                    JSONObject return_json = WeiXinPayService.weiXinH5PayRecharge(memberInfo.getOpenId(), payOrderId, amount.multiply(bigDecimal).intValue() + "", clientIP);
                    if (return_json.getString("package") == null) {
                        logger.info("【易小保科技】微信H5支付发生异常 统一下单失败" + return_json.toJSONString() + "  会员信息:" + memberId);
                        return new Result<>(Code.FAIL, "统一下单失败!", null, Code.IS_ALERT_YES);
                    }
                    OrderPayment orderPayment = new OrderPayment();
                    orderPayment.setId(StringUtil.getUuid());
                    orderPayment.setOrderId(payOrderId);
                    orderPayment.setMemberId(memberId);
                    orderPayment.setCreateTime(YxbConstants.sysDate());
                    orderPayment.setContent("加油卡");
                    orderPayment.setRemark("易宝订单-加油卡充值");
                    orderPayment.setPayWay(YxbConstants.PAY_WAY_WX);
                    orderPayment.setPayAmount(amount.toString());
                    orderPayment.setOrderAmount(amount.toString());
                    orderPayment.setStatus(YxbConstants.ORDER_PAY_NO);//未支付
                    Integer insert = orderPaymentMapper.insert(orderPayment);
                    if (insert < 1) {
                        return new Result<>(Code.FAIL, "获取微信参数失败,无法生成付款订单!", null, Code.IS_ALERT_YES);
                    }
                    return new Result<>(Code.SUCCESS, "请在微信端完成支付!", return_json, Code.IS_ALERT_NO);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("【易小保科技】微信H5支付发生异常" + e.getMessage() + "  会员信息:" + memberId);
                    return new Result<>(Code.FAIL, "微信支付发生异常!", null, Code.IS_ALERT_YES);
                }
            }
            // 安卓或者IOS发起的
            //组装返回参数
            Map<String, Object> wxparam = new HashMap<>();
            wxparam.put("appid", "");
            wxparam.put("par tnerid", "");
            wxparam.put("prepayid", "");
            wxparam.put("noncestr", "");
            wxparam.put("timestamp", System.currentTimeMillis());
            wxparam.put("package", "Sign=WXPay");
            String secSign = "";
            wxparam.put("sign", secSign);
            map.put("wxparam", wxparam);
            //返回微信支付所需参数
            return new Result<>(Code.SUCCESS, "请在微信端完成支付!", map, Code.IS_ALERT_NO);
        } else {
            return new Result<>(Code.FAIL, "不支持的支付类型!", null, Code.IS_ALERT_YES);
        }
    }
}












