package com.yxbkj.yxb.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.entity.order.OrderProtect;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProtectCard;
import com.yxbkj.yxb.product.mapper.*;
import com.yxbkj.yxb.product.service.CardService;
import com.yxbkj.yxb.util.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, ProtectCard> implements CardService {
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProtectMapper protectMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    public List<ProtectCard> selectCard(String productId){

        EntityWrapper<ProtectCard> protectCardEntityWrapper = new EntityWrapper<>();
        protectCardEntityWrapper.eq("sell_type", "10000522");
        protectCardEntityWrapper.eq("product_id", productId);

        List<ProtectCard> protectCards = cardMapper.selectList(protectCardEntityWrapper);

        return protectCards;
    }
    public MemberInfo selectMember(String memberId){
        MemberInfo memberInfo1 = new MemberInfo();
        memberInfo1.setMemberId(memberId);
        MemberInfo memberInfo2 = memberMapper.selectOne(memberInfo1);
        return memberInfo2;
    }
    public ProductInfo selectProduct(String productId){

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        ProductInfo pp = productInfoMapper.selectOne(productInfo);
        return pp;
    }

    @Override
        public Result<Map<String, Object>> insertCard(
            String productId, BigDecimal amount, String plateNumber,
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token,
            String policyHolder , String policyCard, String chassisNumber,
            String policyPhone, String number, String source
            ) {

        if (selectCard(productId).size() == 0) {
            return new Result<>(Code.FAIL, "暂无卡售!", null, Code.IS_ALERT_YES);
        }

        String memberId = redisTemplateUtils.getStringValue(token);

        if (memberId.equals("") || memberId == null){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        // 卡单
        List<ProtectCard> protectCards = selectCard(productId);
        String cardPwd = protectCards.get(0).getCardPwd();
        String cardNo = protectCards.get(0).getCardNo();
        String startTime = protectCards.get(0).getStartTime();
        String endTime = protectCards.get(0).getEndTime();

        //用户数据
        MemberInfo memberInfo = selectMember(memberId);
        String phone = memberInfo.getPhone();
        String memberName = memberInfo.getMemberName();

        //产品名称
        ProductInfo productInfo = selectProduct(productId);
        String productName = productInfo.getProductName();

        String orderId = "OPRO"+StringUtil.getCurrentDateStr();
        OrderProtect orderProtect = new OrderProtect();
        Order order = new Order();
        order.setId(StringUtil.getUuid()); //生成UUID
        order.setProductId(productId); //产品ID
        order.setOrderId(orderId); //订单号
        order.setOrderType("10000442"); //非车险
        order.setOrderMemberId(memberId); //会员ID
        order.setOrderMemberName(memberName); //会员名称
        order.setAmount(amount); //交易金额
        order.setPayStatus("10000522"); //是否支付
        order.setOrderSource(source);
        order.setCommissionStatus("10000392");
        order.setOrderStatus("10000532");
        order.setValidity("10000001");
        order.setOrderMemberPhone(Long.parseLong(phone));
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(DateUtils.getSysDate());
        order.setRemark(productName);

        orderProtect.setId(StringUtil.getUuid());  //生成UUID
        orderProtect.setPolicyHolder(policyHolder);   //投保人名字
        orderProtect.setPolicyCard(policyCard);         //投保人身份证
        orderProtect.setPolicyPhone(policyPhone);     //投保人手机号
        orderProtect.setOrderId(orderId);             //生成订单号
        orderProtect.setPlateNumber(plateNumber);    //车牌号
        orderProtect.setChassisNumber(chassisNumber); //车架号
        orderProtect.setNumber(number);              //核定载客人数

        orderMapper.insert(order);
        protectMapper.insert(orderProtect);
        Map<String, Object> maps = new HashMap<>();
        maps.put("text", orderProtect);
        maps.put("test", order);


        ProtectCard protectCard = new ProtectCard();
        Order orders = new Order();

        order.setOrderId(orderId);

        protectCard.setCardNo(cardNo);

        orderProtect.setOrderId(orderId);


        order = orderMapper.selectOne(order); //查询该订单ID的数据
        protectCard = cardMapper.selectOne(protectCard);  // 查询该卡号的数据
        orderProtect = protectMapper.selectOne(orderProtect);

        orderProtect.setCardNo(cardNo);  //卡号
        orderProtect.setCardPwd(cardPwd); //密码
        Integer protectInt = protectMapper.updateById(orderProtect);


        if (protectCard == null){
            return new Result<>(Code.FAIL, "暂无卡售!", null, Code.IS_ALERT_YES);
        }

        protectCard.setSellType("10000521");//出售状态 -> 已出售
        protectCard.setStartTime(startTime);//开始时间
        protectCard.setEndTime(endTime);//结束时间
        Integer cardInt = cardMapper.updateById(protectCard);

        orders.setPayStatus("10000521"); //支付状态 -> 已支付
        Integer orderInt = orderMapper.updateById(order);

        Map<String, Object> map = new HashMap<>();
        map.put("order", orderInt);
        map.put("card", cardInt);
        map.put("protect", protectInt);
        return new Result<>(Code.SUCCESS, "下单成功", map, Code.IS_ALERT_NO);

    }

}
