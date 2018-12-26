package com.yxbkj.yxb.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Config;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.FuelPayOrder;
import com.yxbkj.yxb.entity.product.FuelCard;
import com.yxbkj.yxb.entity.product.FuelDiscount;
import com.yxbkj.yxb.system.mapper.*;
import com.yxbkj.yxb.system.service.ConfigService;
import com.yxbkj.yxb.system.service.FuelPayOrderService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author ZY
 * @since 2018-12-17
 */
@Service
public class FuelPayOrderServiceImpl extends ServiceImpl<FuelPayOrderMapper, FuelPayOrder> implements FuelPayOrderService {
    @Autowired
    private FuelPayOrderMapper fuelPayOrderMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private FuelDiscountMapper fuelDiscountMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private FuelCardMapper fuelCardMapper;
    @Override
    public Result<Map<String, Object>> addPayOrder(Double discountNum, Integer months, Double amount, String token, String payType,String orderType,String fuelNumber,Double monthAmount,String orderSource) {
        Result<Map<String,Object>> result = null;
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
            return result = new Result<>(Code.FAIL,"token为空或者已过期",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelCard> efc = new EntityWrapper<>();
        efc.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).eq("member_id",memberId);
        List<FuelCard> fuelCardList = fuelCardMapper.selectList(efc);
        int fuelNums = 0;
        for (int i = 0; i < fuelCardList.size(); i++) {
            if(fuelCardList.get(i).getCardNumber().equals(fuelNumber)) {
                fuelNums = 1;
            }
        }
        if(fuelNums == 0) {
            return new Result<>(Code.FAIL,"不存在的加油卡号",new HashMap<>(),Code.IS_ALERT_YES);
        }
        //查询出折扣
        EntityWrapper<FuelDiscount> efs = new EntityWrapper<>();
        efs.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).eq("discount_type",orderType);
        List<FuelDiscount> fuelDiscountList = fuelDiscountMapper.selectList(efs);
        ArrayList<BigDecimal> discountList = new ArrayList<>();
        ArrayList<Integer> monthList = new ArrayList<>();
        for (int i = 0; i < fuelDiscountList.size(); i++) {
            discountList.add(fuelDiscountList.get(i).getDiscountNum());
            monthList.add(fuelDiscountList.get(i).getMonths());
        }
        int cant = 0;
        for (int i = 0; i < discountList.size(); i++) {
            if(discountList.get(i).doubleValue() == discountNum) {
               cant = 1;
               break;
            }
        }
        Long monthss = null;
        for (int i = 0; i < monthList.size(); i++) {
            if((monthList.get(i)+ "").equals(months + "")) {
                monthss = Long.parseLong(months + "");
            }
        }
        if(cant == 0) {
            return new Result<>(Code.FAIL,"折扣大小不存在",new HashMap<>(),Code.IS_ALERT_YES);
        }
        if(monthss == null) {
            return new Result<>(Code.FAIL,"到账月数不存在",new HashMap<>(),Code.IS_ALERT_YES);
        }
        String recharge = configService.getConfigValue("recharge");
        JSONObject jsonObject = JSONObject.parseObject(recharge);
        Object fuel = jsonObject.get("fuel");
        String s = fuel.toString();
        JSONObject jsonObject1 = JSONObject.parseObject(s);
        List<Integer> configList = (List<Integer>)jsonObject1.get("price");
        BigDecimal monthAmounts = null;
        for (int i = 0; i < configList.size(); i++) {
            if(configList.get(i)  == monthAmount.intValue() ) {
                monthAmounts = new BigDecimal(monthAmount + "");
            }
        }
        if(monthAmounts == null) {
            return new Result<>(Code.FAIL,"每月到账金额不存在",new HashMap<>(),Code.IS_ALERT_YES);
        }
        Double amounts = (discountNum / 10) * months * monthAmount;
        NumberFormat nf=new DecimalFormat( "0.00");
        amounts = Double.parseDouble(nf.format(amounts));
        if(amounts.doubleValue() != amount.doubleValue()) {
            return new Result<>(Code.FAIL,"支付总金额不正确",new HashMap<>(),Code.IS_ALERT_YES);
        }
        FuelPayOrder fuelPayOrder = new FuelPayOrder();
        fuelPayOrder.setId(StringUtil.getUuid());
        String payOrderId = "OPAY" + StringUtil.getCurrentDateStr();
        fuelPayOrder.setPayOrderId(payOrderId);
        fuelPayOrder.setOrderType(orderType);
        fuelPayOrder.setFuelNumber(fuelNumber);
        fuelPayOrder.setMonthAmount(monthAmounts);
        fuelPayOrder.setCommission_status(YxbConstants.ORDER_FENYONG_NO);
        fuelPayOrder.setOrderMemberId(memberId);
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
        fuelPayOrder.setOrderMemberName(memberInfo1.getMemberName());
        fuelPayOrder.setOrderMemberPhone(Long.parseLong(memberInfo1.getPhone()));
        fuelPayOrder.setAmount(new BigDecimal(amount + ""));
        fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_NO);
		fuelPayOrder.setOrderSource(orderSource);
        fuelPayOrder.setMonths(monthss);
        fuelPayOrder.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);
        fuelPayOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelPayOrder.setPayType(payType);
        fuelPayOrder.setCreatorTime(YxbConstants.sysDate());
        Integer insert = fuelPayOrderMapper.insert(fuelPayOrder);
        if(insert != 1) {
            return result = new Result<>(Code.FAIL, "添加失败", null, Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",insert);
        map.put("payOrderId",payOrderId);
        return result = new Result<>(Code.SUCCESS,"订单生成成功",map,Code.IS_ALERT_YES);
    }

    @Override
    public Result<Page<Map<String,Object>>> payOrderList(String token, Integer limit, Integer offset,String payStatus) {
        Page<Map<String, Object>> mapPage = new Page<Map<String, Object>>();
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
            return new Result<>(Code.FAIL,"token为空或者已过期",mapPage,Code.IS_ALERT_YES);
        }
        if(limit == null) {
            limit = 5;
        }
        if(offset == null) {
            offset = 1;
        }
        Page<FuelPayOrder> page = new Page(offset,limit);
        EntityWrapper<FuelPayOrder> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("order_member_id",memberId);
        wrapper.eq("pay_status",payStatus);
        page.setOrderByField("creator_time");
        page.setAsc(false);
        Page<FuelPayOrder> fuelPayOrderPage = selectPage(page, wrapper);
        List<FuelPayOrder> records = fuelPayOrderPage.getRecords();
        if(!records.isEmpty() && records.size() > 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("pageList",records);
            mapPage.setCondition(map);
            return  new Result<>(Code.SUCCESS,"分页查询成功",mapPage,Code.IS_ALERT_YES);
        }
        return new Result<>(Code.SUCCESS,"查询失败",mapPage,Code.IS_ALERT_YES);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> updatePayOrderStatus(String payOrderId) {
        Result<Map<String,Object>> result = null;
        if(payOrderId == null) {
            return result = new Result<>(Code.FAIL,"订单号为空",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelPayOrder> ef = new EntityWrapper<>();
        ef.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).eq("pay_status",YxbConstants.ORDER_PAY_NO).eq("pay_order_id",payOrderId);
        FuelPayOrder fuelPayOrder = selectOne(ef);
        if(fuelPayOrder == null) {
            return result = new Result<>(Code.FAIL,"订单不存在或已被支付",null,Code.IS_ALERT_YES);
        }
        fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_YES);
        boolean flag = updateById(fuelPayOrder);
        if(flag) {
            return result = new Result<>(Code.SUCCESS,"支付订单修改成功",null,Code.IS_ALERT_NO);
        } else {
            return result = new Result<>(Code.FAIL,"支付订单状态修改失败",null,Code.IS_ALERT_YES);
        }
    }
}
