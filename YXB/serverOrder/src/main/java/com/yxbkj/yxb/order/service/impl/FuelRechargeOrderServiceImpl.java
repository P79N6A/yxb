package com.yxbkj.yxb.order.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.FuelPayOrder;
import com.yxbkj.yxb.entity.order.FuelRechargeOrder;
import com.yxbkj.yxb.order.mapper.FuelPayOrderMapper;
import com.yxbkj.yxb.order.mapper.FuelRechargeOrderMapper;
import com.yxbkj.yxb.order.service.FuelRechargeOrderService;
import com.yxbkj.yxb.order.service.JiaYouCardService;
import com.yxbkj.yxb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 充值订单 服务实现类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
@Service
public class FuelRechargeOrderServiceImpl extends ServiceImpl<FuelRechargeOrderMapper, FuelRechargeOrder> implements FuelRechargeOrderService {
    @Autowired
    private FuelPayOrderMapper fuelPayOrderMapper;
    @Autowired
    private FuelRechargeOrderMapper fuelRechargeOrderMapper;
    @Autowired
    private JiaYouCardService jiaYouCardService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public Result<Map<String, Object>> addFuelRechargeOrders(String payOrderId) {
        Result<Map<String,Object>> result = null;
        logger.info("支付订单:" + payOrderId);
        if(payOrderId == null) {
            logger.info("订单号为空或错误");
            return result = new Result<>(Code.FAIL,"订单号为空",null,Code.IS_ALERT_YES);
        }
        FuelPayOrder fuelPayOrder = new FuelPayOrder();
        fuelPayOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelPayOrder.setPayOrderId(payOrderId);
        fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_YES);
        FuelPayOrder fuelPayOrder1 = fuelPayOrderMapper.selectOne(fuelPayOrder);
        if(fuelPayOrder1 == null) {
            logger.info("订单号为空或错误");
            return result = new Result<>(Code.FAIL,"订单不存在",null,Code.IS_ALERT_YES);
        }
        String payType = fuelPayOrder1.getPayType();
        String orderMemberId = fuelPayOrder1.getOrderMemberId();
        String orderMemberName = fuelPayOrder1.getOrderMemberName();
        Long orderMemberPhone = fuelPayOrder1.getOrderMemberPhone();
        String fuelNumber = fuelPayOrder1.getFuelNumber();
        String orderType = fuelPayOrder1.getOrderType();
        String orderSource = fuelPayOrder1.getOrderSource();
        int months = fuelPayOrder1.getMonths().intValue();
        BigDecimal monthAmount = fuelPayOrder1.getMonthAmount();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dataTime = null;
        dataTime = StringUtil.dateNowMonth();
        FuelRechargeOrder fuelRechargeOrder = new FuelRechargeOrder();
        fuelRechargeOrder.setId(StringUtil.getUuid());
        fuelRechargeOrder.setPayOrderId(payOrderId);
        fuelRechargeOrder.setRechargeOrderId(StringUtil.getUuid());
        fuelRechargeOrder.setOrderType(orderType);
        fuelRechargeOrder.setArrivalDate(dataTime);
        fuelRechargeOrder.setFuelNumber(fuelNumber);
        fuelRechargeOrder.setOrderSource(orderSource);
        fuelRechargeOrder.setOrderMemberId(orderMemberId);
        fuelRechargeOrder.setOrderMemberName(orderMemberName);
        fuelRechargeOrder.setTime(new Long(1));
        fuelRechargeOrder.setOrderMemberPhone(orderMemberPhone);
        fuelRechargeOrder.setAmount(monthAmount);
        fuelRechargeOrder.setPayStatus(YxbConstants.ORDER_PAY_NO);
        fuelRechargeOrder.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);
        fuelRechargeOrder.setPayType(payType);
        fuelRechargeOrder.setCreatorTime(YxbConstants.sysDate());
        fuelRechargeOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        Integer insert = fuelRechargeOrderMapper.insert(fuelRechargeOrder);
        if(insert < 1) {
            return result = new Result<>(Code.FAIL,"订单生成失败",null,Code.IS_ALERT_YES);
        }
        if(months > 1) {
            for (int i = 1; i < months; i++) {
                String dataTime1 = null;
                int a = 0;
                a++;
                month++;
                if(month > 12) {
                    month = 1;
                    year++;
                }
                int day1 = getDay(month, year);
                if(day > day1) {
                    day = day1;
                }
                if(month < 10) {
                    dataTime1 = year + "-0" + month + "-" + day;
                } else {
                    dataTime1 = year + "-" + month + "-" + day;
                }
                FuelRechargeOrder fuelRechargeOrder1 = new FuelRechargeOrder();
                fuelRechargeOrder1.setId(StringUtil.getUuid());
                fuelRechargeOrder1.setPayOrderId(payOrderId);
                fuelRechargeOrder1.setRechargeOrderId(StringUtil.getUuid());
                fuelRechargeOrder1.setOrderType(orderType);
                fuelRechargeOrder1.setArrivalDate(dataTime1);
                fuelRechargeOrder1.setFuelNumber(fuelNumber);
                fuelRechargeOrder1.setOrderSource(orderSource);
                fuelRechargeOrder1.setOrderMemberId(orderMemberId);
                fuelRechargeOrder1.setOrderMemberName(orderMemberName);
                fuelRechargeOrder1.setTime(new Long(a));
                fuelRechargeOrder1.setOrderMemberPhone(orderMemberPhone);
                fuelRechargeOrder1.setAmount(monthAmount);
                fuelRechargeOrder1.setPayStatus(YxbConstants.ORDER_PAY_NO);
                fuelRechargeOrder1.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);
                fuelRechargeOrder1.setPayType(payType);
                fuelRechargeOrder1.setCreatorTime(YxbConstants.sysDate());
                fuelRechargeOrder1.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
                Integer insert1 = fuelRechargeOrderMapper.insert(fuelRechargeOrder1);
                if(insert1 < 1) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("orderId",payOrderId);
                    logger.info("生成订单明细失败");
                    return  new Result<>(Code.FAIL,"生成明细订单失败",map,Code.IS_ALERT_YES);
                }
            }
        }
        FuelRechargeOrder fuelRechargeOrder1 = new FuelRechargeOrder();
        fuelRechargeOrder1.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelRechargeOrder1.setPayOrderId(payOrderId);
        //fuelRechargeOrder1.setPayStatus(YxbConstants.ORDER_PAY_NO);
        fuelRechargeOrder1.setArrivalDate(YxbConstants.sysDateNow());
        FuelRechargeOrder fuelRechargeOrder2 = fuelRechargeOrderMapper.selectOne(fuelRechargeOrder1);
        if(fuelRechargeOrder2 == null) {
            logger.info("不存在待支付的订单" + payOrderId);
            return new Result<>(Code.FAIL,"订单不存在",null,Code.IS_ALERT_NO);
        }
        BigDecimal amount = fuelRechargeOrder2.getAmount();
        int amountInt = amount.intValue();
        String rechargeOrderId = fuelRechargeOrder2.getRechargeOrderId();
        String fuelNumber1 = fuelRechargeOrder2.getFuelNumber();
        int cardType = 0;
        Map<Integer,Integer> map = new HashMap<>();
        if(fuelNumber1.startsWith("9")) {
            cardType = 2;
            jiaYouCardService.onLineOrder(10008,amountInt + "",fuelNumber1,orderMemberPhone + "",null,cardType,rechargeOrderId);
        } else {
            cardType = 1;
            map.put(50,10000);
            map.put(100,10001);
            map.put(200,10002);
            map.put(500,10003);
            map.put(1000,10004);
            jiaYouCardService.onLineOrder(map.get(amountInt),"1",fuelNumber1,orderMemberPhone + "",null,cardType,rechargeOrderId);
        }
        return result = new Result<>(Code.SUCCESS,"生成所有明细成功",null,Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> getRechargeOrderList(String payOrderId) {
        Result<Map<String,Object>> result = null;
        if(payOrderId == null) {
            return  new Result<>(Code.FAIL,"订单号为空",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelRechargeOrder> eor = new EntityWrapper<>();
        eor.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).eq("pay_order_id",payOrderId).orderBy("arrival_date ASC");
        List<FuelRechargeOrder> fuelRechargeOrderList = selectList(eor);
        if(fuelRechargeOrderList.size() == 0) {
            return new Result<>(Code.FAIL,"明细查询异常",null,Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("fuelRechargeOrderList",fuelRechargeOrderList);
        return new Result<>(Code.FAIL,"明细查询成功",map,Code.IS_ALERT_YES);
    }

    @Override
    public Result<Map<String, Object>> getPayRechargeOrderByOrderId(String orderId) {
        Result<Map<String,Object>> result = null;
        FuelRechargeOrder fuelRechargeOrder = new FuelRechargeOrder();
        fuelRechargeOrder.setRechargeOrderId(orderId);
        fuelRechargeOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        FuelRechargeOrder fuelRechargeOrder1 = fuelRechargeOrderMapper.selectOne(fuelRechargeOrder);
        Map<String,Object> map = new HashMap<>();
        if(fuelRechargeOrder1 != null) {
            map.put("fuelRecharge",fuelRechargeOrder1);
            return  result = new Result<>(Code.SUCCESS,"查询成功",map,Code.IS_ALERT_YES);
        } else {
            return  result = new Result<>(Code.FAIL,"查询失败",map,Code.IS_ALERT_YES);
        }
    }

    private int getDay(int month,int year) {
        int[] days = null;
        if(year%4==0&&year%100!=0||year%400==0) {
            days = new int[]{31,29,31,30,31,30,31,31,30,31,30,31};
        } else {
            days = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        }
        return days[month - 1];
    }
}
