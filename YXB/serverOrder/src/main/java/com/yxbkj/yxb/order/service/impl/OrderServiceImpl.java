package com.yxbkj.yxb.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Constants;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.*;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProductInvestment;
import com.yxbkj.yxb.entity.vo.*;
import com.yxbkj.yxb.order.mapper.*;
import com.yxbkj.yxb.order.service.*;
import com.yxbkj.yxb.util.*;
import com.yxbkj.yxb.util.aliyun.SmsUtils;
import com.yxbkj.yxb.util.anxin.DesUtil;
import com.yxbkj.yxb.util.pingan.EPay;
import com.yxbkj.yxb.util.pingan.PingAnUtils;
import com.yxbkj.yxb.util.yian.YiAnUtils;
import com.yxbkj.yxb.util.zhongan.RC4Util;
import com.yxbkj.yxb.util.zhongan.ZhongAnUtils;
import com.yxbkj.yxb.util.zrbx.ZRBXProductUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-07-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Autowired
    private FuelPayOrderMapper fuelPayOrderMapper;
    @Autowired
    private OrderProtectService orderProtectService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private FuelCardMapper fuelCardMapper;
    @Autowired
    private FuelRechargeOrderService fuelRechargeOrderService;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductInvestmentMapper productInvestmentMapper;

    @Autowired
    private OrderFundMapper orderFundMapper;
    @Autowired
    private OrderPaymentMapper orderPaymentMapper;
    @Autowired
    private ConfigService configService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> createOrder(OrderParam orderParams) {
        if(orderParams.token==null){
            return new Result<Map<String, Object>>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        String memeberId = redisTemplateUtils.getStringValue(orderParams.token);
        if(memeberId==null){
            return  new Result<>(Code.FAIL, "token不存在或者已经过期!", null, Code.IS_ALERT_YES);

        }
        JSONObject orderParam = (JSONObject) JSON.toJSON(orderParams);
        logger.info("【易小保科技】核保实体入参信息"+orderParam.toJSONString());
        String result = null;
        Map<String, Object> map = new HashMap<>();
        try{
            result = PingAnUtils.sendDataToPingAnByType(3, orderParam);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】结果信息"+result);
            logger.info("【易小保科技】异常信息"+e.getMessage());
        }
        logger.info("【易小保科技】核保响应信息"+result);
        if(result==null){
            return new Result<Map<String, Object>>(Code.FAIL, "链接第三方服务异常!", null, Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(!"00".equals(jsonObject.getString("returnCode"))){
            map.put("data",PingAnUtils.decryptData(jsonObject.getString("data")));
            return new Result<Map<String, Object>>(Code.FAIL, jsonObject.getString("returnMsg"), map, Code.IS_ALERT_YES);
        }
        String data = PingAnUtils.decryptData(jsonObject.getString("data"));
        logger.info("【易小保科技】核保响应信息 解密后"+data);
        JSONObject jsonPolicy = JSON.parseObject(data);
        //返回00 已经调用成功
        // 1 创建订单
        Order order = new Order();
        order.setId(StringUtil.getUuid());
        order.setProductId(orderParams.productIdSelf);
        order.setOrderId(orderParams.outChannelOrderId);
        order.setOrderSource(orderParams.orderSource);
        order.setOrderMemberId(memeberId);
        MemberInfo member = findMemberByMemberId(memeberId);
        if(member!=null){
            order.setOrderMemberName(member.getMemberName());
            try{
                order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
            }catch (Exception e){
                e.printStackTrace();
                return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
            }
        }
        order.setOrderType(YxbConstants.ORDER_TYPE_PRO);//订单类型 非车险的码表值
        //设置订单金额
        try{
            order.setAmount(new BigDecimal(orderParams.totalPremium));
        }catch (Exception e){
            e.printStackTrace();
            logger.info("金额转换异常"+orderParams.totalPremium);
            return new Result<Map<String, Object>>(Code.FAIL, "订单金额转换异常!", null, Code.IS_ALERT_YES);
        }
        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_NO);//未支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);//未出单
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(YxbConstants.sysDate());
        order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        order.setOrderMemberId(redisTemplateUtils.getStringValue(orderParams.token));
        order.setRemark(orderParams.remark);
        insert(order);
        // 2 创建非车险订单
        OrderProtect orderProtect = new OrderProtect();
        orderProtect.setId(StringUtil.getUuid());
        orderProtect.setOrderId(order.getOrderId());
        // 投保人
        if(orderParams.applicant!=null){
            orderProtect.setPolicyHolder(orderParams.applicant.name);
            orderProtect.setPolicyCard(orderParams.applicant.idno);
        }
        List<OrderParam.Insurants> insurants = orderParams.insurants;
        // 被保人
        if(insurants!=null && insurants.size()>0){
            OrderParam.Insurants insurant = insurants.get(0);
            orderProtect.setProtectHolder(insurant.name);
            orderProtect.setProtectCard(insurant.idno);
            if(insurant.contactInfo!=null){
                orderProtect.setProtectPhone(insurant.contactInfo.mobile);//被保人手机号
                orderProtect.setProtectEmail(insurant.contactInfo.email);//被保人邮箱
            }

        }
        //orderProtect.setPolicyNum(orderParams.applicant.idno);//保单号
        orderProtect.setRemark(orderParams.remark);//备注
        //orderProtect.setPolicyUrl(orderParams.remark);//保单地址
        //orderProtect.setBankNo(orderParams.remark);//银行代码
        //orderProtect.setBankAccountNo(orderParams.remark);//银行卡号
        //orderProtect.setBankAccountName(orderParams.remark);//开户人
        //投保人联系方式
        if(orderParams.applicant!=null && orderParams.applicant.contactInfo!=null){
            orderProtect.setPolicyPhone(orderParams.applicant.contactInfo.mobile);//投保人手机号
            orderProtect.setPolicyEmail(orderParams.applicant.contactInfo.email);//投保人邮箱
        }
        //orderProtect.setProtectPhone(orderParams.remark);//被保人手机号
        //orderProtect.setProtectEmail(orderParams.remark);//被保人邮箱
        orderProtect.setPolicyNo(jsonPolicy.getString("orderId"));//投保单号
        orderProtectService.insert(orderProtect);
        map.put("data",PingAnUtils.decryptData(jsonObject.getString("data")));
        map.put("order",order);
        return new Result<Map<String, Object>>(Code.SUCCESS, "核保成功!", map, Code.IS_ALERT_NO);
    }

    public MemberInfo findMemberByMemberId(String memberId) {
        EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
        wraper.eq("member_id",memberId);
        return memberInfoService.selectOne(wraper);
    }


    @Override
    public Result<Map<String, Object>> calculatePremium(CalculatePremiumParam calculatePremiumParam) {
        JSONObject calculatePremiumParamJson = (JSONObject) JSON.toJSON(calculatePremiumParam);
        logger.info("【易小保科技】核保试算实体入参信息"+calculatePremiumParamJson.toJSONString());
        String result = null;
        Map<String, Object> map = new HashMap<>();
        try{
            result = PingAnUtils.sendDataToPingAnByType(2, calculatePremiumParamJson);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】结果信息"+result);
            logger.info("【易小保科技】异常信息"+e.getMessage());
        }
        logger.info("【易小保科技】核保试算响应信息"+result);
        if(result==null){
            return new Result<Map<String, Object>>(Code.FAIL, "链接第三方服务异常!", null, Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(!"00".equals(jsonObject.getString("returnCode"))){
            map.put("data",PingAnUtils.decryptData(jsonObject.getString("data")));
            return new Result<Map<String, Object>>(Code.FAIL, jsonObject.getString("returnMsg"), map, Code.IS_ALERT_YES);
        }
        //返回00 已经调用成功
        String data = PingAnUtils.decryptData(jsonObject.getString("data"));
        logger.info("【易小保科技】核保响应信息"+data);
        map.put("data",data);
        map.put("id",StringUtil.getCurrentDateStr());
        return new Result<Map<String, Object>>(Code.SUCCESS, "核保试算正常!", map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> orderPay(String orderId) {
        Map<String, Object> map = null;
        EntityWrapper<Order> wraper = new EntityWrapper<>();
        wraper.eq("order_id",orderId);
        wraper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        Order order = selectOne(wraper);
        if(order==null){
            return new Result<Map<String, Object>>(Code.FAIL, "当前订单不存在!", null, Code.IS_ALERT_YES);
        }
        Map<String,String> param = new HashMap<>();
        //param.put("channel_order_no","88000000023959547"+order.getOrderId());
        //获取E生宝的投保单号
        EntityWrapper<OrderProtect> wrapper = new EntityWrapper<>();
        wrapper.eq("order_id", order.getOrderId());
        OrderProtect orderProtect = orderProtectService.selectOne(wrapper);
        //获取E生宝的投保单号   0913修改为走从表获取投保单号  此单号为E生宝核保时 返回的订单
        if(order.getOrderId()!=null)param.put("channel_order_no",orderProtect.getPolicyNo());
        if(order.getRemark()!=null)param.put("goods_desc",order.getRemark());
        BigDecimal bigDecimal = new BigDecimal("100");
        if(order.getAmount()!=null) param.put("total_fee", order.getAmount().multiply(bigDecimal).intValue()+"");
        String pay_url = EPay.pay(param);
        map = new HashMap<>();
        map.put("redirectUrl",pay_url);
        return new Result<Map<String, Object>>(Code.SUCCESS, "获取跳转参数成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> zrProductRediect(ZrProductParam zrProductParam) {
        if(zrProductParam.getToken()==null){
            return new Result<Map<String, Object>>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        String memeberId = redisTemplateUtils.getStringValue(zrProductParam.getToken());
        if(memeberId==null){
            return  new Result<>(Code.FAIL, "token不存在或者已经过期!", null, Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        String url = "";
        logger.info("【易小保科技】异常信息"+memeberId+"   "+zrProductParam.getProduct_id()+"   "+zrProductParam.getSource());
        try{
            url = ZRBXProductUtils.getProductRedirectUrl(zrProductParam.getProductCode(),"{'memberId':'"+memeberId+"','productId':'"+zrProductParam.getProduct_id()+"','source':'"+zrProductParam.getSource()+"'}");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】错误参数"+zrProductParam.getProductCode()+"    memeberId"+memeberId);
            logger.info("【易小保科技】异常信息"+e.getMessage());
            return  new Result<>(Code.FAIL, "第三方服务异常!", null, Code.IS_ALERT_YES);
        }
        map.put("redirectUrl",url);
        return new Result<Map<String, Object>>(Code.SUCCESS, "获取跳转参数成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> znProductRediect(ZnProductParam znProductParam) {
        if(znProductParam.getToken()==null){
            return new Result<Map<String, Object>>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        String memeberId = redisTemplateUtils.getStringValue(znProductParam.getToken());
        if(memeberId==null){
            return  new Result<>(Code.FAIL, "token不存在或者已经过期!", null, Code.IS_ALERT_YES);
        }
        ProductInfo productInfo  = new ProductInfo();
        productInfo.setProductId(znProductParam.getProduct_id());
        ProductInfo productInfoDb = productInfoMapper.selectOne(productInfo);
        if(productInfoDb==null){
            return  new Result<>(Code.FAIL, "不存在的产品信息!", null, Code.IS_ALERT_YES);
        }
        if(productInfoDb.getExtensionLink()==null){
            return  new Result<>(Code.FAIL, "不存在的产品推广链接!", null, Code.IS_ALERT_YES);
        }
        Map<String,Object> map = new HashMap<>();
        String redirectUrl = ZhongAnUtils.getRedirectUrl(memeberId,productInfoDb.getProductId(),znProductParam.getSource(),productInfoDb.getExtensionLink());
        map.put("redirectUrl",redirectUrl);
        return new Result<Map<String, Object>>(Code.SUCCESS, "获取跳转参数成功!", map, Code.IS_ALERT_NO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String,Object>> acceptOrder(AcceptParam acceptParam) {
        JSONObject entityParam = (JSONObject) JSON.toJSON(acceptParam);
        logger.info("【易小保科技】核保回调实体入参信息"+entityParam.toJSONString());
        String result = null;
        Map<String, Object> map = new HashMap<>();
        // 1  发起承保信息
        try{
            JSONObject requestJson = new JSONObject();
            requestJson.put("orderId",acceptParam.orderId);
            result = PingAnUtils.sendDataToPingAnByType(4, requestJson);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】结果信息"+result);
            logger.info("【易小保科技】异常信息"+e.getMessage());
        }
        logger.info("【易小保科技】承保响应信息"+result);
        if(result==null){
            return new Result<Map<String, Object>>(Code.FAIL, "链接第三方服务异常!", null, Code.IS_ALERT_YES);
        }
        // 2 解析承保信息
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(!"00".equals(jsonObject.getString("returnCode"))){
            map.put("data",PingAnUtils.decryptData(jsonObject.getString("data")));
            String returnMsg = jsonObject.getString("returnMsg");
            logger.info("【易小保科技】承保错误 错误信息"+returnMsg);
            return new Result<Map<String, Object>>(Code.FAIL,returnMsg , map, Code.IS_ALERT_YES);
        }
        String data = PingAnUtils.decryptData(jsonObject.getString("data"));
        logger.info("【易小保科技】承保响应信息 解密后"+data);
        JSONObject jsonPolicy = JSON.parseObject(data);
        // 3 处理承保成功信息 返回00 已经调用成功
        // 3.1  先查从表  在获取订单Id
        EntityWrapper<OrderProtect> wrapper_protect = new EntityWrapper<>();
        wrapper_protect.eq("policy_no", acceptParam.orderId);
        OrderProtect orderProtectDb = orderProtectService.selectOne(wrapper_protect);
        if(orderProtectDb==null){
            logger.info("【易小保科技】回调处理失败 未知投保单");
            return new Result<Map<String, Object>>(Code.FAIL,"" , null, Code.IS_ALERT_YES);
        }
        EntityWrapper<Order> wraper = new EntityWrapper<>();
        wraper.eq("order_id",orderProtectDb.getOrderId());
        Order order = selectOne(wraper);
        if(order==null){
            return new Result<Map<String, Object>>(Code.FAIL,"不存在的订单信息" ,null , Code.IS_ALERT_YES);
        }
        boolean sendMsg = true;
        if(YxbConstants.ORDER_PAY_YES.equals(order.getPayStatus())){
            sendMsg = false;
        }
        //支付类型
        if("zfb".equals(acceptParam.payType)){
            order.setPayType(YxbConstants.PAY_WAY_ZFB);
        }else if("wx".equals(acceptParam.payType)){
            order.setPayType(YxbConstants.PAY_WAY_WX);
        }else if("yqb".equals(acceptParam.payType)){
            order.setPayType(YxbConstants.PAY_WAY_YQB);
        }
        order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已出单
        order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已支付
        updateById(order);
        // 修改非车险订单
        String policyNo = jsonPolicy.getString("policyNo");
        EntityWrapper<OrderProtect> wraperProtect = new EntityWrapper<>();
        wraperProtect.eq("order_id",orderProtectDb.getOrderId());
        OrderProtect orderProtect = orderProtectService.selectOne(wraperProtect);
        if(orderProtect!=null){
            logger.info("【易小保科技】执行修改平安订单");
            orderProtect.setPolicyUrl(PingAnUtils.getOutChannel(policyNo));
            orderProtect.setPolicyNum(policyNo);
            orderProtectService.updateById(orderProtect);
        }else{
            logger.info("【易小保科技】执行修改平安订单失败，不存在的订单号");
        }
        //发送订单通知
        try{
            String orderMemberId = order.getOrderMemberId();
            if(orderMemberId!=null && sendMsg){
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_id",orderMemberId);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo memberInfo = memberInfoService.selectOne(wrapper);
                if(memberInfo!=null){
                    ProductInfo pro  = new ProductInfo();
                    pro.setProductId(order.getProductId());
                    ProductInfo productInfo = productInfoMapper.selectOne(pro);
                    String productName = null;
                    if(productInfo!=null){
                        productName =  productInfo.getProductName();
                    }
                    MemberUtils.sendPayOrderInfo(memberInfo.getOpenId(),order.getOrderId(),productName!=null?productName:"保险产品");
                }
            }
        }catch (Exception e){
            logger.info("发送通知失败"+e.getMessage());
            e.printStackTrace();
        }
        return new Result<Map<String, Object>>(Code.SUCCESS,"承保成功!订单状态已改变" ,map , Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> znProductNotify(String result) {
        logger.info("众安回调信息"+result);
        if(StringUtil.isEmpty(result)){
            return  new Result<>(Code.FAIL, "结果为空!", null, Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        if("1".equals(jsonObject.getString("notifyType"))){
            logger.info("众安处理承保信息"+result);
        }else if("3".equals(jsonObject.getString("notifyType"))){
            logger.info("众安处理退保信息"+result);
            return new Result<Map<String, Object>>(Code.SUCCESS, "众安处理退保信息!", null, Code.IS_ALERT_NO);
        }else{
            logger.info("众安回调暂不处理"+result);
            return new Result<Map<String, Object>>(Code.SUCCESS, "众安回调暂不处理!", null, Code.IS_ALERT_NO);
        }
        //判断保单号
        EntityWrapper<OrderProtect> wrapper_pro = new EntityWrapper<>();
        wrapper_pro.eq("policy_num",jsonObject.getString("policyNo"));
        int count = orderProtectService.selectCount(wrapper_pro);
        if(count>0){
            logger.info("已经存在保单号信息!"+result);
            return new Result<Map<String, Object>>(Code.SUCCESS, "众安回调暂不处理!", null, Code.IS_ALERT_NO);
        }
        String bizContent = jsonObject.getString("bizContent");
        String bizStr = RC4Util.decryRC4(bizContent, RC4Util.key);
        JSONObject bizObject = JSONObject.parseObject(bizStr);
        String memberId = bizObject.getString("memberId");
        String source = bizObject.getString("source");
        // 1 创建订单
        Order order = new Order();
        order.setId(StringUtil.getUuid());
        order.setProductId(bizObject.getString("productId"));
        order.setOrderId("OPRO"+StringUtil.getCurrentDateStr());
        order.setOrderSource(source);
        order.setOrderMemberId(memberId);
        MemberInfo member = findMemberByMemberId(memberId);
        if(member!=null){
            order.setOrderMemberName(member.getMemberName());
            try{
                order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
            }catch (Exception e){
                e.printStackTrace();
                logger.info("手机号码出现异常");
                //return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
            }
        }
        order.setOrderType(YxbConstants.ORDER_TYPE_PRO);//订单类型 非车险的码表值
        //设置订单金额
        try{
            order.setAmount(new BigDecimal(jsonObject.getString("premium")));
        }catch (Exception e){
            e.printStackTrace();
            logger.info("金额转换异常");
            order.setAmount(BigDecimal.ZERO);
           // return new Result<Map<String, Object>>(Code.FAIL, "订单金额转换异常!", null, Code.IS_ALERT_YES);
        }
        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已经出单
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(YxbConstants.sysDate());
        order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        order.setOrderMemberId(memberId);
        order.setRemark("众安订单");
        insert(order);
        String vehicleType = jsonObject.getString("vehicleType");//如果是车险 此值不为空
        if(vehicleType!=null){
            //创建车险订单
        }else{
            // 2 创建非车险订单
            OrderProtect orderProtect = new OrderProtect();
            orderProtect.setId(StringUtil.getUuid());
            orderProtect.setOrderId(order.getOrderId());
            //orderProtect.setPolicyNum(orderParams.applicant.idno);//保单号
            orderProtect.setRemark("众安订单");//备注
            //orderProtect.setPolicyUrl(orderParams.remark);//保单地址
            //orderProtect.setBankNo(orderParams.remark);//银行代码
            //orderProtect.setBankAccountNo(orderParams.remark);//银行卡号
            //orderProtect.setBankAccountName(orderParams.remark);//开户人
            //orderProtect.setProtectPhone(orderParams.remark);//被保人手机号
            //orderProtect.setProtectEmail(orderParams.remark);//被保人邮箱
            orderProtect.setPolicyNum(jsonObject.getString("policyNo"));//投保单号
            orderProtectService.insert(orderProtect);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("order",order);
        //发送订单通知
        try {
            String orderMemberId = order.getOrderMemberId();
            if(orderMemberId!=null){
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_id",orderMemberId);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo memberInfo = memberInfoService.selectOne(wrapper);
                if(memberInfo!=null){
                    ProductInfo pro  = new ProductInfo();
                    pro.setProductId(order.getProductId());
                    ProductInfo productInfo = productInfoMapper.selectOne(pro);
                    String productName = null;
                    if(productInfo!=null){
                        productName =  productInfo.getProductName();
                    }
                    MemberUtils.sendPayOrderInfo(memberInfo.getOpenId(),order.getOrderId(),productName!=null?productName:"保险产品");
                }
            }
        }catch (Exception e){
            logger.info("发送订单通知失败"+e.getMessage());
        }
        return new Result<Map<String, Object>>(Code.SUCCESS, "订单创建成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> handBorrowProduct(String result) {
        logger.info("理财产品回调信息"+result);
        if(StringUtil.isEmpty(result)){
            return  new Result<>(Code.FAIL, "结果为空!", null, Code.IS_ALERT_YES);
        }
        JSONArray objects = JSONObject.parseArray(result);
        for(int i  = 0 ; i <objects.size();i++){
            JSONObject jsonObject = (JSONObject) objects.get(i);
            String account_tender = jsonObject.getString("account_tender");
            String borrow_nid = jsonObject.getString("borrow_nid");
            String platform_uid = jsonObject.getString("platform_uid");
            String real_account = jsonObject.getString("real_account");
            String recover_account_capital = jsonObject.getString("recover_account_capital");
            String recover_account_interest = jsonObject.getString("recover_account_interest");
            String red_money = jsonObject.getString("red_money");
            String tender_nid = jsonObject.getString("tender_nid");
            String tender_time = jsonObject.getString("tender_time");
            String user_id = jsonObject.getString("user_id");
            //查找会员
            EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
            wraper.eq("old_user_id",user_id);
            MemberInfo memberInfo = memberInfoService.selectOne(wraper);
            if(memberInfo==null){
                logger.info("理财产品回调信息 会员信息不存在"+user_id);
                continue;
                //return  new Result<>(Code.FAIL, "理财产品回调信息 会员信息不存在!", null, Code.IS_ALERT_YES);
            }
            //查找产品
            ProductInvestment productInfo = new ProductInvestment();
            productInfo.setOtherPid(borrow_nid);
            ProductInvestment productInvestment = productInvestmentMapper.selectOne(productInfo);
            if(productInvestment==null){
                logger.info("理财产品回调信息 产品信息不存在"+borrow_nid);
                continue;
                // return  new Result<>(Code.FAIL, "理财产品回调信息 产品信息不存在!", null, Code.IS_ALERT_YES);
            }
            //订单
            // 1 创建订单
            Order order = new Order();
            order.setId(StringUtil.getUuid());
            order.setProductId(productInvestment.getProductId());
            order.setOrderId("OFUN"+StringUtil.getCurrentDateStr());
            order.setOrderSource("10000012");//微信 会员来源
            order.setOrderMemberId(memberInfo.getMemberId());
            MemberInfo member = findMemberByMemberId(memberInfo.getMemberId());
            if(member!=null){
                order.setOrderMemberName(member.getMemberName());
                try{
                    order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("手机号码出现异常");
                    //return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
                }
            }
            order.setOrderType("10000443");//订单类型 理财产品的码表值
            //设置订单金额
            try{
                order.setAmount(new BigDecimal(account_tender));
            }catch (Exception e){
                e.printStackTrace();
                logger.info("金额转换异常");
                order.setAmount(BigDecimal.ZERO);
                // return new Result<Map<String, Object>>(Code.FAIL, "订单金额转换异常!", null, Code.IS_ALERT_YES);
            }
            //支付状态
            order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已支付
            //分佣状态
            order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
            //订单状态
            order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已经出单
            order.setCreatorIp(HttpKit.getClientIP());
            order.setCreatorTime(YxbConstants.sysDate());
            order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            order.setOrderMemberId(memberInfo.getMemberId());
            order.setRemark("理财产品订单");
            insert(order);
            if(false){
            }else{
                // 2 创建理财订单
                OrderFund orderFund = new OrderFund();
                orderFund.setId(StringUtil.getUuid());
                orderFund.setOrderId(order.getOrderId());
                orderFund.setPlatformUid(platform_uid);
                try{
                    orderFund.setAccountTender(new BigDecimal(account_tender));
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("金额转换异常");
                }
                orderFund.setRecoverAccountInteres(new BigDecimal(recover_account_interest));
                orderFund.setRecoverAccountCapital(new BigDecimal(recover_account_capital));
                try{
                    orderFund.setTenderTime(YxbConstants.sysDate(Long.parseLong(tender_time)));
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("时间转换异常");
                    orderFund.setTenderTime(tender_time);
                }

                orderFundMapper.insert(orderFund);
            }
        }
        return  new Result<>(Code.SUCCESS, "数据添加成功!", null, Code.IS_ALERT_YES);

    }


    @Override
    public Result<Map<String, Object>> createBorrowProduct(String result) {
        logger.info("理财产品回调信息"+result);
        if(StringUtil.isEmpty(result)){
            return  new Result<>(Code.FAIL, "结果为空!", null, Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        String borrow_account = jsonObject.getString("borrow_account");
        String borrow_account_scale = jsonObject.getString("borrow_account_scale");
        String borrow_account_yes = jsonObject.getString("borrow_account_yes");
        String borrow_apr = jsonObject.getString("borrow_apr");
        String borrow_apr_max = jsonObject.getString("borrow_apr_max");
        String borrow_name = jsonObject.getString("borrow_name");
        String borrow_nid = jsonObject.getString("borrow_nid");
        String borrow_period = jsonObject.getString("borrow_period");
        String borrow_period_long = jsonObject.getString("borrow_period_long");
        String borrow_post_type = jsonObject.getString("borrow_post_type");
        String borrow_reward = jsonObject.getString("borrow_reward");
        String borrow_status = jsonObject.getString("borrow_status");
        String borrow_style = jsonObject.getString("borrow_style");
        String borrow_verify_time = jsonObject.getString("borrow_verify_time");
        String borrow_reverify_time = jsonObject.getString("borrow_reverify_time");
        // 获取理财产品从表
        ProductInvestment investment = new ProductInvestment();
        investment.setOtherPid(borrow_nid);
        investment.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        ProductInvestment productInvestment = productInvestmentMapper.selectOne(investment);
        if(productInvestment!=null){
            //更新理财产品从表
            //investment.setProductId(productInvestment.getProductId());
            productInvestment.setModifierTime(YxbConstants.sysDate());
            if(!StringUtil.isEmpty(borrow_apr))
                productInvestment.setFundApr(new BigDecimal(borrow_apr));
            if(!StringUtil.isEmpty(borrow_account))
                productInvestment.setFundAccount(new BigDecimal(borrow_account));
            if(!StringUtil.isEmpty(borrow_account_yes))
                productInvestment.setFundAccountTotal(new BigDecimal(borrow_account_yes));
            if(!StringUtil.isEmpty(borrow_period))
                productInvestment.setFundPeriod(borrow_period);
            if(!StringUtil.isEmpty(borrow_style))
                productInvestment.setRepaymentType("1000048"+borrow_style);//还款方式
            //investment.setFundType("");//标的类型
            if(!StringUtil.isEmpty(borrow_reverify_time))
                productInvestment.setValuedateTime(YxbConstants.sysDate(borrow_reverify_time+"000"));//复审及起息时间
            if(!StringUtil.isEmpty(borrow_verify_time))
                productInvestment.setBiddingTime(YxbConstants.sysDate(borrow_verify_time+"000"));//发标及初审时间
            if(!StringUtil.isEmpty(borrow_account_scale))
                productInvestment.setFundFinishrate(new BigDecimal(borrow_account_scale));
            productInvestmentMapper.updateById(productInvestment);
        }else{
            //新增产品主表
            String productId = "PFUN"+productInfoMapper.getNextProductId();
            ProductInfo product = new ProductInfo();
            product.setId(StringUtil.getUuid());
            product.setProductId(productId);
            product.setCompanyCode("C00005");
            product.setProductCatalcode("1");
            product.setProductName(borrow_name);
            product.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            product.setCreatorIp(HttpKit.getClientIP());
            product.setCreatorTime(YxbConstants.sysDate());
            product.setShelveStatus("10000462");
            productInfoMapper.insert(product);
            //新增理财产品从表
            investment.setId(StringUtil.getUuid());
            investment.setProductId(product.getProductId());
            investment.setCreatorIp(HttpKit.getClientIP());
            investment.setRemark("PHP生成产品");
            investment.setCreatorTime(YxbConstants.sysDate());
            if(!StringUtil.isEmpty(borrow_apr))
                investment.setFundApr(new BigDecimal(borrow_apr));
            if(!StringUtil.isEmpty(borrow_account))
                investment.setFundAccount(new BigDecimal(borrow_account));
            if(!StringUtil.isEmpty(borrow_account_yes))
                investment.setFundAccountTotal(new BigDecimal(borrow_account_yes));
            if(!StringUtil.isEmpty(borrow_period))
                investment.setFundPeriod(borrow_period);
            if(!StringUtil.isEmpty(borrow_style))
                investment.setRepaymentType("1000048"+borrow_style);//还款方式
            //investment.setFundType("");//标的类型
            if(!StringUtil.isEmpty(borrow_reverify_time))
                investment.setValuedateTime(YxbConstants.sysDate(borrow_reverify_time+"000"));//复审及起息时间
            if(!StringUtil.isEmpty(borrow_verify_time))
                investment.setBiddingTime(YxbConstants.sysDate(borrow_verify_time+"000"));//发标及初审时间
            if(!StringUtil.isEmpty(borrow_account_scale))
                investment.setFundFinishrate(new BigDecimal(borrow_account_scale));
            productInvestmentMapper.insert(investment);
        }
        return  new Result<>(Code.SUCCESS, "数据添加成功!", null, Code.IS_ALERT_YES);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> buyMemberNotifyForYiBao(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String orderId = jsonObject.getString("orderId");
        String paySuccessDate = jsonObject.getString("paySuccessDate");
        String payAmount = jsonObject.getString("payAmount");
        String orderAmount = jsonObject.getString("orderAmount");
        String paymentProduct = jsonObject.getString("paymentProduct");
        String status = jsonObject.getString("status");
        if("SUCCESS".equals(status)){
            OrderPayment orderPayment = new OrderPayment();
               orderPayment.setOrderId(orderId);
            OrderPayment orderPaymentDb = orderPaymentMapper.selectOne(orderPayment);
            if(orderPaymentDb==null){
                return  new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
            }
            orderPaymentDb.setStatus(YxbConstants.ORDER_PAY_YES);//已经支付
            orderPaymentDb.setUpdateTime(YxbConstants.sysDate());//设置更新时间
            orderPaymentDb.setExtra(result);//设置附加信息
            //修改为已经支付
            orderPaymentMapper.updateById(orderPaymentDb);
            //修改会员等级
            EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
            wraper.eq("member_id",orderPaymentDb.getMemberId());
            MemberInfo memberInfo = memberInfoService.selectOne(wraper);
            if(memberInfo==null){
                return  new Result<>(Code.FAIL, "修改会员信息失败!", null, Code.IS_ALERT_NO);
            }
            String memberLevel = orderPaymentDb.getContent();
            String buyMember = configService.getConfigValue(YxbConstants.BUYMEMBER_CONFIG);
            JSONObject parse = (JSONObject) JSONObject.parse(buyMember);
            JSONObject buyMemberJson = parse.getJSONObject(memberLevel);
            int  limitTime  = buyMemberJson.getIntValue(YxbConstants.LIMIT_TIME_FOR_MONEY);
            memberInfo.setModifierTime(YxbConstants.sysDate());
            // 1 如果是续费
            if (memberLevel.equals(memberInfo.getMemberlevel())){
                orderPaymentDb.setExtra(orderPaymentDb.getExtra()+"易宝会员续费");//设置附加信息
                //修改为已经支付
                orderPaymentMapper.updateById(orderPaymentDb);
                String memberLimitTime = memberInfo.getMemberLimitTime();
                // 1.1  判断是否过期  如果过期  则 当前时间+续费时间  如果没有过期  则 过期时间+续费时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    long time = sdf.parse(memberLimitTime).getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if(time<currentTimeMillis){
                        //已经过期
                        memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
                    }else{
                        //没有过期  过期时间加上  续费时间
                        memberInfo.setMemberLimitTime(YxbConstants.sysAddDate(time,limitTime*24L*60L*60L*1000L));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("日期转换异常"+memberLimitTime+"异常信息"+e.getMessage());
                    memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
                }
            }else{
                memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
            }
            memberInfo.setMemberlevel(memberLevel);
            memberInfoService.updateById(memberInfo);
        }
        return  new Result<>(Code.FAIL, "会员信息购买成功!", null, Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> zrProductNotify(String result) {
        logger.info("回调业务层处理"+result);
        JSONObject jsonObject = null;
        String json_str = null;
       try{
           json_str = ZRBXProductUtils.des_zhongrui(result);
           jsonObject = JSONObject.parseObject(json_str);
       }catch (Exception e){
           e.printStackTrace();
           return new Result<Map<String, Object>>(Code.SUCCESS, "!", null, Code.IS_ALERT_YES);
       }
        JSONObject orderForm = jsonObject.getJSONObject("orderForm");
        JSONObject orderinfo = jsonObject.getJSONObject("orderinfo");
        JSONObject extendBody = orderinfo.getJSONObject("extendBody");


        String orderID = orderForm.getString("OrderID").replace(ZRBXProductUtils.orgNo, "");

        // 1 创建订单
        Order order = new Order();
        order.setId(StringUtil.getUuid());
        order.setOrderId(orderID);
        order.setOrderSource(extendBody.getString("source"));//来源
        order.setOrderMemberId(extendBody.getString("memberId"));//会员ID
        order.setProductId(extendBody.getString("productId"));//产品ID
        MemberInfo member = findMemberByMemberId(order.getOrderMemberId());
        if(member!=null){
            order.setOrderMemberName(member.getMemberName());
            try{
                order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
            }catch (Exception e){
                e.printStackTrace();
                logger.info("手机号码出现异常");
                //return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
            }
        }
        order.setAmount(new BigDecimal(orderForm.getString("premium")));//订单金额
        order.setOrderType(YxbConstants.ORDER_TYPE_PRO);//订单类型 非车险的码表值
        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已经支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已经出单
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(YxbConstants.sysDate());
        order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        order.setRemark("中瑞订单");
        order.setExt4(json_str);
        insert(order);
        // 2 创建非车险订单
        OrderProtect orderProtect = new OrderProtect();
        orderProtect.setId(StringUtil.getUuid());
        orderProtect.setOrderId(order.getOrderId());
        orderProtect.setRemark("中瑞订单");//备注
        orderProtect.setPolicyUrl(orderForm.getString("policyurl"));//保单地址

        orderProtect.setProtectHolder(orderForm.getString("InsuredName"));
        orderProtect.setProtectCard(orderForm.getString("InsuredNum"));
        orderProtect.setProtectPhone(orderForm.getString("applicantPhone"));//被保人手机号
        orderProtect.setRemark(json_str);//备注


        orderProtect.setPolicyHolder(orderForm.getString("applicantName"));
        orderProtect.setPolicyPhone(orderForm.getString("applicantPhone"));//投保人手机号
        orderProtect.setPolicyCard(orderForm.getString("applicantNum"));//身份证
        orderProtect.setPolicyNum(orderForm.getString("insurannumber"));//投保单号


        orderProtectService.insert(orderProtect);
        Map<String,Object> map = new HashMap<>();
        map.put("order",order);


        //发送订单通知
        try{
            String orderMemberId = order.getOrderMemberId();
            if(orderMemberId!=null){
                EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
                wrapper.eq("member_id",orderMemberId);
                wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                MemberInfo memberInfo = memberInfoService.selectOne(wrapper);
                if(memberInfo!=null){
                    ProductInfo pro  = new ProductInfo();
                    pro.setProductId(order.getProductId());
                    ProductInfo productInfo = productInfoMapper.selectOne(pro);
                    String productName = null;
                    if(productInfo!=null){
                        productName =  productInfo.getProductName();
                    }
                    MemberUtils.sendPayOrderInfo(memberInfo.getOpenId(),order.getOrderId(),productName!=null?productName:"保险产品");
                }
            }
        }catch (Exception e){
            logger.info("发送通知失败"+e.getMessage());
            e.printStackTrace();
        }

        return new Result<Map<String, Object>>(Code.SUCCESS, "订单创建成功!", map, Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> buyMemberNotifyForWxH5(String orderId) {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderId(orderId);
        OrderPayment orderPaymentDb = orderPaymentMapper.selectOne(orderPayment);
        if(orderPaymentDb==null){
            return  new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
        }
        orderPaymentDb.setStatus(YxbConstants.ORDER_PAY_YES);//已经支付
        orderPaymentDb.setUpdateTime(YxbConstants.sysDate());//设置更新时间
        orderPaymentDb.setExtra(orderId);//设置附加信息
        //修改为已经支付
        orderPaymentMapper.updateById(orderPaymentDb);
        //修改会员等级
        EntityWrapper<MemberInfo> wraper = new EntityWrapper<>();
        wraper.eq("member_id",orderPaymentDb.getMemberId());
        MemberInfo memberInfo = memberInfoService.selectOne(wraper);
        if(memberInfo==null){
            return  new Result<>(Code.FAIL, "修改会员信息失败!", null, Code.IS_ALERT_NO);
        }
        String memberLevel = orderPaymentDb.getContent();
        String buyMember = configService.getConfigValue(YxbConstants.BUYMEMBER_CONFIG);
        JSONObject parse = (JSONObject) JSONObject.parse(buyMember);
        JSONObject buyMemberJson = parse.getJSONObject(memberLevel);
        int  limitTime  = buyMemberJson.getIntValue(YxbConstants.LIMIT_TIME_FOR_MONEY);
        memberInfo.setModifierTime(YxbConstants.sysDate());
        // 1 如果是续费
        if (memberLevel.equals(memberInfo.getMemberlevel())){

            orderPaymentDb.setExtra(orderPaymentDb.getExtra()+"微信会员续费");//设置附加信息
            //修改为已经支付
            orderPaymentMapper.updateById(orderPaymentDb);

            String memberLimitTime = memberInfo.getMemberLimitTime();
            // 1.1  判断是否过期  如果过期  则 当前时间+续费时间  如果没有过期  则 过期时间+续费时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                long time = sdf.parse(memberLimitTime).getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if(time<currentTimeMillis){
                    //已经过期
                    memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
                }else{
                    //没有过期  过期时间加上  续费时间
                    memberInfo.setMemberLimitTime(YxbConstants.sysAddDate(time,limitTime*24L*60L*60L*1000L));
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.info("日期转换异常"+memberLimitTime+"异常信息"+e.getMessage());
                memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
            }
        }else{
            memberInfo.setMemberLimitTime(YxbConstants.sysNextDayDate(limitTime*24L*60L*60L*1000L));
        }
        memberInfo.setMemberlevel(memberLevel);
        memberInfoService.updateById(memberInfo);
        //发送升级推送
       try{
           if(!StringUtil.isEmpty(memberInfo.getPid())){
               EntityWrapper<MemberInfo> wrapers = new EntityWrapper<>();
               wrapers.eq("member_id",memberInfo.getPid());
               wrapers.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
               MemberInfo memberInfoParent = memberInfoService.selectOne(wrapers);
               if(memberInfoParent!=null){
                   MemberUtils.sendBuyMemberInfo(memberInfo,memberInfoParent);
               }
           }
       }catch (Exception e){
           e.printStackTrace();
           logger.info("发送升级推送失败!");
       }
        return  new Result<>(Code.FAIL, "会员信息购买成功!", null, Code.IS_ALERT_NO);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> rechargeNotifyForWxH5Recharge(String orderId) {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderId(orderId);
        OrderPayment orderPaymentDb = orderPaymentMapper.selectOne(orderPayment);
        if(orderPaymentDb==null){
            return  new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
        }
        orderPaymentDb.setStatus(YxbConstants.ORDER_PAY_YES);//已经支付
        orderPaymentDb.setUpdateTime(YxbConstants.sysDate());//设置更新时间
        orderPaymentDb.setExtra(orderId);//设置附加信息
        //修改为已经支付
        orderPaymentMapper.updateById(orderPaymentDb);
        FuelPayOrder fuelPayOrder = new FuelPayOrder();
        fuelPayOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelPayOrder.setPayOrderId(orderId);
        fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_NO);
        FuelPayOrder fuelPayOrder1 = fuelPayOrderMapper.selectOne(fuelPayOrder);
        if (fuelPayOrder1 == null) {
            logger.info("订单不存在或已完成支付：" + orderId);
            return new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
        }
        String orderMemberId = fuelPayOrder1.getOrderMemberId();
        Long orderMemberPhone = fuelPayOrder1.getOrderMemberPhone();
        fuelPayOrder1.setPayStatus(YxbConstants.ORDER_PAY_YES);
        fuelPayOrder1.setOrderStatus(YxbConstants.ORDER_CHUDAN);
        fuelPayOrderMapper.updateById(fuelPayOrder1);
        fuelRechargeOrderService.addFuelRechargeOrders(orderId);
        Map<String,Object> map = new HashMap<>();
        map.put("activityNo","ACTI20180921181818003");
        map.put("memberId",orderMemberId);
        map.put("activity_type","10000444");
        Map<String, Object> stringObjectMap = fuelCardMapper.elifeActivity(map);
        String code = stringObjectMap.get("code") + "";
        String money = stringObjectMap.get("money") + "";
        String msg = (String) stringObjectMap.get("msg");
        logger.info("orderId:" + orderId +"code:" + code + "money:"+ money + "msg:" + msg);
        if(code.equals("0")) {
            try {
                SendSmsResponse sendSmsResponse = SmsUtils.sendSms5(orderMemberPhone + "",money);
                logger.info("发短信" + sendSmsResponse);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("发短信失败");
            }
        }
        logger.info("支付回调完成确认");
        return  new Result<>(Code.SUCCESS, "加油卡充值成功!", null, Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<JSONObject> yiAnCreateOrder(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        YiAnParam yiAnParam = JSONObject.parseObject(jsonStr, YiAnParam.class);
        String token = jsonObject.getString("token");
        String memeberId = redisTemplateUtils.getStringValue(token);
        if(memeberId==null){
            return  new Result<>(Code.FAIL, "token不存在或者已经过期!", null, Code.IS_ALERT_YES);
        }
        if(StringUtil.isEmpty(yiAnParam.productId)){
            return  new Result<>(Code.FAIL, "产品ID不能为空!", null, Code.IS_ALERT_YES);
        }
        String orderId = "OPRO"+StringUtil.getCurrentDateStr();
        jsonObject.put("outBusinessCode",orderId);//第三方订单号
        //获取token
        String  access_token = redisTemplateUtils.getStringValue("yian_access_token");
        String  open_id = redisTemplateUtils.getStringValue("yian_open_id");
        if(access_token==null || open_id==null){
            JSONObject accessToken = YiAnUtils.getAccessToken();
            access_token = accessToken.getString("access_token");
            open_id = accessToken.getString("open_id");
            redisTemplateUtils.stringAdd("yian_access_token",access_token,Constants.YIAN_TOKEN_MAX_TIME);
            redisTemplateUtils.stringAdd("yian_open_id",open_id,Constants.YIAN_TOKEN_MAX_TIME);
        }
        //向第三方发出请求
        JSONObject yian_order = null;
        try{
            yian_order = YiAnUtils.createOrder(jsonObject.toJSONString(), access_token, open_id);
            logger.info("【易小保科技】易安订单响应信息"+yian_order.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】易安订单创建异常"+e.getMessage());
            return  new Result<>(Code.FAIL, "易安订单创建异常!", null, Code.IS_ALERT_YES);
        }
        if(!"0000".equals(yian_order.getString("code"))){
            return  new Result<>(Code.FAIL, yian_order.getString("message"), null, Code.IS_ALERT_YES);
        }



        // 1 创建订单
        Order order = new Order();
        order.setId(StringUtil.getUuid());
        order.setOrderId(orderId);
        order.setOrderSource(jsonObject.getString("source"));//来源
        order.setOrderMemberId(memeberId);//会员ID
        order.setProductId(jsonObject.getString("productId"));//产品ID
        MemberInfo member = findMemberByMemberId(memeberId);
        if(member!=null){
            order.setOrderMemberName(member.getMemberName());
            try{
                order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
            }catch (Exception e){
                e.printStackTrace();
                logger.info("手机号码出现异常");
                //return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
            }
        }
        order.setAmount(new BigDecimal(jsonObject.getString("premium")));//订单金额
        order.setOrderType(YxbConstants.ORDER_TYPE_PRO);//订单类型 非车险的码表值
        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_NO);//未支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);//未出单
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(YxbConstants.sysDate());
        order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        order.setRemark("易安订单");
        order.setExt4(yian_order.toJSONString());
        insert(order);
        // 2 创建非车险订单
        OrderProtect orderProtect = new OrderProtect();
        orderProtect.setId(StringUtil.getUuid());
        orderProtect.setOrderId(order.getOrderId());
        orderProtect.setRemark("易安订单");//备注
        orderProtect.setPolicyUrl(yian_order.getString("policyUrl"));//保单地址


        orderProtect.setRemark(yian_order.toJSONString());//备注



        try{
             orderProtect.setProtectHolder(yiAnParam.data.createOrderReq.orderList.get(0).itemAcciList.get(0).acciInsuredList.get(0).customerName);//被保人姓名
             orderProtect.setProtectCard(yiAnParam.data.createOrderReq.orderList.get(0).itemAcciList.get(0).acciInsuredList.get(0).docNo);
             orderProtect.setProtectPhone(yiAnParam.data.createOrderReq.orderList.get(0).itemAcciList.get(0).acciInsuredList.get(0).phoneNo);//被保人手机号
             orderProtect.setPolicyHolder(yiAnParam.data.createOrderReq.orderList.get(0).customerList.get(0).customerName);
             orderProtect.setPolicyPhone(yiAnParam.data.createOrderReq.orderList.get(0).customerList.get(0).phoneNo);//投保人手机号
             orderProtect.setPolicyCard(yiAnParam.data.createOrderReq.orderList.get(0).customerList.get(0).docNo);//身份证
             //orderProtect.setPolicyNo(orderForm.getString("insurannumber"));//投保单号
        }catch (Exception e){
            e.printStackTrace();
            logger.info("易安创建订单解析json异常");
        }


        orderProtectService.insert(orderProtect);

        return new Result<JSONObject>(Code.SUCCESS, "核保成功!", JSONObject.parseObject(yian_order.toJSONString()), Code.IS_ALERT_NO);

    }

    @Override
    public Result<JSONObject> yiAnOrderPay(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //获取token
        String  access_token = redisTemplateUtils.getStringValue("yian_access_token");
        String  open_id = redisTemplateUtils.getStringValue("yian_open_id");
        if(access_token==null || open_id==null){
            JSONObject accessToken = YiAnUtils.getAccessToken();
            access_token = accessToken.getString("access_token");
            open_id = accessToken.getString("open_id");
            redisTemplateUtils.stringAdd("yian_access_token",access_token,Constants.YIAN_TOKEN_MAX_TIME);
            redisTemplateUtils.stringAdd("yian_open_id",open_id,Constants.YIAN_TOKEN_MAX_TIME);
        }
        //向第三方发出请求
        JSONObject yian_order = null;
        try{
            yian_order = YiAnUtils.orderPay(jsonObject, access_token, open_id);
            logger.info("【易小保科技】易安支付响应信息"+yian_order.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            logger.info("【易小保科技】易安支付创建异常"+e.getMessage());
            return  new Result<>(Code.FAIL, "易安支付创建异常!", null, Code.IS_ALERT_YES);
        }
        return  new Result<>(Code.SUCCESS, "获取支付信息成功!", yian_order, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Boolean> axOrderCallBack(String str) {
        logger.info("【易小保科技API 安心订单入参密文】"+str);
        String decrypt = null;
        try {
              decrypt = DesUtil.decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【易小保科技API 安心订单创建异常】"+e.getMessage());
            return new Result<>(Code.FAIL,"安心订单创建异常",false,Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(decrypt);
        logger.info("【易小保科技API 安心订单入参明文】"+jsonObject.toJSONString());
        String paramInfo = jsonObject.getJSONObject("policyListVo").getJSONObject("channelInfo").getString("param1");
        JSONObject jsonParam = JSONObject.parseObject(paramInfo);
        // 1 创建订单
        String orderId = jsonParam.getString("orderId");
        Order order = new Order();
        order.setId(StringUtil.getUuid());
        order.setOrderId(orderId);
        order.setOrderSource(jsonParam.getString("source"));//来源
        String memeberId = jsonParam.getString("memeberId");
        order.setOrderMemberId(memeberId);//会员ID
        order.setProductId(jsonParam.getString("productId"));//产品ID
        MemberInfo member = findMemberByMemberId(memeberId);
        if(member!=null){
            order.setOrderMemberName(member.getMemberName());
            try{
                order.setOrderMemberPhone(Long.parseLong(member.getPhone()));
            }catch (Exception e){
                e.printStackTrace();
                logger.info("手机号码出现异常");
                //return new Result<Map<String, Object>>(Code.FAIL, "手机号码出现异常!", null, Code.IS_ALERT_YES);
            }
        }
        order.setAmount(new BigDecimal(jsonObject.getJSONObject("policyListVo").getJSONObject("policy").getString("premiums")));//订单金额
        order.setOrderType(YxbConstants.ORDER_TYPE_PRO);//订单类型 非车险的码表值
        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_NO);//未支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_NO_CHUDAN);//未出单
        order.setCreatorIp(HttpKit.getClientIP());
        order.setCreatorTime(YxbConstants.sysDate());
        order.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        order.setRemark("易安订单");
        order.setExt4(jsonObject.toJSONString());
        insert(order);
        // 2 创建非车险订单
        OrderProtect orderProtect = new OrderProtect();
        orderProtect.setId(StringUtil.getUuid());
        orderProtect.setOrderId(order.getOrderId());
        orderProtect.setRemark("易安订单");//备注
        orderProtect.setPolicyUrl(jsonObject.getString("policyUrl"));//保单地址

        // orderProtect.setProtectHolder(orderForm.getString("InsuredName"));
        // orderProtect.setProtectCard(orderForm.getString("InsuredNum"));
        // orderProtect.setProtectPhone(orderForm.getString("applicantPhone"));//被保人手机号
        orderProtect.setRemark(jsonObject.toJSONString());//备注


//        orderProtect.setPolicyHolder(orderForm.getString("applicantName"));
//        orderProtect.setPolicyPhone(orderForm.getString("applicantPhone"));//投保人手机号
//        orderProtect.setPolicyCard(orderForm.getString("applicantNum"));//身份证
//        orderProtect.setPolicyNo(orderForm.getString("insurannumber"));//投保单号


        orderProtectService.insert(orderProtect);


        return new Result<>(Code.SUCCESS,"安心订单成功!",true,Code.IS_ALERT_NO);
    }

    @Override
    public Result<Boolean> axPolicyCallBack(String str) {
        logger.info("【易小保科技API 安心保单入参密文】"+str);
        String decrypt = null;
        try {
            decrypt = DesUtil.decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【易小保科技API 安心保单创建异常】"+e.getMessage());
            return new Result<>(Code.FAIL,"安心保单创建异常",false,Code.IS_ALERT_YES);
        }
        JSONObject jsonObject = JSONObject.parseObject(decrypt);
        logger.info("【易小保科技API 安心保单入参明文】"+jsonObject.toJSONString());
        String paramInfo = jsonObject.getJSONObject("policyListVo").getJSONObject("channelInfo").getString("param1");
        JSONObject jsonParam = JSONObject.parseObject(paramInfo);
        // 1 订单
        String orderId = jsonParam.getString("orderId");
        EntityWrapper<Order> wrapper = new EntityWrapper<>();
        wrapper.eq("order_id",orderId);
        Order order = selectOne(wrapper);
        if(order==null){
            logger.info("【易小保科技API 安心保单回调异常异常】 不存在的订单信息"+jsonObject.toJSONString());
            return new Result<>(Code.FAIL,"安心保单创建异常",false,Code.IS_ALERT_YES);
        }

        //支付状态
        order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已经支付
        //分佣状态
        order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
        //订单状态
        order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已经出单
        updateById(order);

        EntityWrapper<OrderProtect> wrapper_pro = new EntityWrapper<>();
        wrapper_pro.eq("order_id", orderId);
        OrderProtect orderProtect = orderProtectService.selectOne(wrapper_pro);
        if(orderProtect!=null){
            orderProtect.setPolicyUrl(jsonObject.getJSONObject("policyListVo").getJSONObject("policy").getString("policyUrl"));
            orderProtect.setPolicyNum(jsonObject.getJSONObject("policyListVo").getJSONObject("policy").getString("policyNo"));
            orderProtect.setRemark(jsonObject.toJSONString());
            orderProtectService.updateById(orderProtect);
        }else{
            logger.info("【易小保科技API 安心保单回调异常异常】 不存在的订单从表信息");
        }


        return new Result<>(Code.SUCCESS,"安心保单处理成功!",true,Code.IS_ALERT_NO);
    }

    @Override
    public Result<JSONObject> yiAnNotify(String jsonStr) {
        logger.info("【易小保科技API 易安json数据】"+jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String orderId = jsonObject.getString("othPolicyNo");
        String payResult = jsonObject.getString("payResult");
        if("Y".equals(payResult)){
            EntityWrapper<Order> wrapper = new EntityWrapper<>();
            wrapper.eq("order_id",orderId);
            Order order = selectOne(wrapper);
            if(order==null){
                logger.info("【易小保科技API 易安保单回调异常异常】 不存在的订单信息"+jsonObject.toJSONString());
                return new Result<JSONObject>(Code.FAIL,"易安保单回调异常",null,Code.IS_ALERT_YES);
            }
            //支付状态
            order.setPayStatus(YxbConstants.ORDER_PAY_YES);//已经支付
            //分佣状态
            order.setCommissionStatus(YxbConstants.ORDER_FENYONG_NO); //未分佣
            //订单状态
            order.setOrderStatus(YxbConstants.ORDER_CHUDAN);//已经出单
            updateById(order);

            EntityWrapper<OrderProtect> wrapper_pro = new EntityWrapper<>();
            wrapper_pro.eq("order_id", orderId);
            OrderProtect orderProtect = orderProtectService.selectOne(wrapper_pro);
            if(orderProtect!=null){
                orderProtect.setPolicyUrl(jsonObject.getString("epolicyUrl"));
                orderProtect.setPolicyNum(jsonObject.getString("policyNo"));
                orderProtect.setRemark(jsonObject.toJSONString());
                orderProtectService.updateById(orderProtect);
            }else{
                logger.info("【易小保科技API 安心保单回调异常异常】 不存在的订单从表信息");
            }


            //发送订单通知
            try{
                String orderMemberId = order.getOrderMemberId();
                if(orderMemberId!=null){
                    EntityWrapper<MemberInfo> wrapper_m = new EntityWrapper<>();
                    wrapper_m.eq("member_id",orderMemberId);
                    wrapper_m.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
                    MemberInfo memberInfo = memberInfoService.selectOne(wrapper_m);
                    if(memberInfo!=null){
                        ProductInfo pro  = new ProductInfo();
                        pro.setProductId(order.getProductId());
                        ProductInfo productInfo = productInfoMapper.selectOne(pro);
                        String productName = null;
                        if(productInfo!=null){
                            productName =  productInfo.getProductName();
                        }
                        MemberUtils.sendPayOrderInfo(memberInfo.getOpenId(),order.getOrderId(),productName!=null?productName:"保险产品");
                    }
                }
            }catch (Exception e){
                logger.info("发送通知失败"+e.getMessage());
                e.printStackTrace();
            }

        }else{
            logger.info("【易小保科技API 支付状态异常】"+jsonStr);
        }

        return new Result<JSONObject>(Code.SUCCESS,"易安保单回调处理成功!",null,Code.IS_ALERT_NO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> rechargeNotifyForYiBao(String result) {
        logger.info("易宝支付回调结果" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String orderId = jsonObject.getString("orderId");
        String paySuccessDate = jsonObject.getString("paySuccessDate");
        String payAmount = jsonObject.getString("payAmount");
        String orderAmount = jsonObject.getString("orderAmount");
        String paymentProduct = jsonObject.getString("paymentProduct");
        String status = jsonObject.getString("status");
        if ("SUCCESS".equals(status)) {
            logger.info("支付成功");
            /*OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderId(orderId);
            OrderPayment orderPaymentDb = orderPaymentMapper.selectOne(orderPayment);
            if (orderPaymentDb == null) {
                logger.info("支付失败");
                return new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
            }
            orderPaymentDb.setStatus(YxbConstants.ORDER_PAY_YES);//已经支付
            orderPaymentDb.setUpdateTime(YxbConstants.sysDate());//设置更新时间
            orderPaymentDb.setExtra(result);//设置附加信息
            //修改为已经支付
            Integer integer = orderPaymentMapper.updateById(orderPaymentDb);
            if(integer == 1) {
                logger.info("支付订单状态修改成功");
            } else {
                logger.info("支付订单状态修改失败");
            }*/
            FuelPayOrder fuelPayOrder = new FuelPayOrder();
            fuelPayOrder.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
            fuelPayOrder.setPayOrderId(orderId);
            fuelPayOrder.setPayStatus(YxbConstants.ORDER_PAY_NO);
            FuelPayOrder fuelPayOrder1 = fuelPayOrderMapper.selectOne(fuelPayOrder);
            if (fuelPayOrder1 == null) {
                logger.info("支付订单不存在或已支付");
                return new Result<>(Code.FAIL, "支付失败!支付订单不存在!", null, Code.IS_ALERT_NO);
            }
            String orderMemberId = fuelPayOrder1.getOrderMemberId();
            Long orderMemberPhone = fuelPayOrder1.getOrderMemberPhone();
            fuelPayOrder1.setPayStatus(YxbConstants.ORDER_PAY_YES);
            fuelPayOrder1.setOrderStatus(YxbConstants.ORDER_CHUDAN);
            fuelPayOrderMapper.updateById(fuelPayOrder1);
            fuelRechargeOrderService.addFuelRechargeOrders(orderId);
            Map<String,Object> map = new HashMap<>();
            map.put("activityNo","ACTI20180921181818003");
            map.put("memberId",orderMemberId);
            map.put("activity_type","10000444");
            Map<String, Object> stringObjectMap = fuelCardMapper.elifeActivity(map);
            String code = (String) stringObjectMap.get("code");
            String  money = (String) stringObjectMap.get("money");
            String msg = (String) stringObjectMap.get("msg");
            logger.info("orderId:" + orderId +"code:" + code + "money:"+ money + "msg:" + msg);
            if(code.equals("0")) {
                try {
                    SendSmsResponse sendSmsResponse = SmsUtils.sendSms5(orderMemberPhone + "",money);
                    logger.info("发短信" + sendSmsResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("发短信失败");
                }
            }
            logger.info("支付回调完成确认");
            return new Result<>(Code.SUCCESS, "加油充值成功!", null, Code.IS_ALERT_NO);
        }
        logger.info("加油充值失败，返回" + status);
        return new Result<>(Code.FAIL, "支付失败!", null, Code.IS_ALERT_NO);
    }
}
