package com.yxbkj.yxb.order.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.entity.vo.*;

import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-07-24
 */
public interface OrderService extends IService<Order> {
    //E生宝核保
    Result<Map<String,Object>> createOrder(OrderParam orderParam);
    //E生宝核算
    Result<Map<String,Object>> calculatePremium(CalculatePremiumParam calculatePremiumParam);
    //订单支付跳转核算
    Result<Map<String,Object>> orderPay(String orderId);
    //中瑞产品跳转
    Result<Map<String,Object>> zrProductRediect(ZrProductParam zrProductParam);
    //众安产品跳转
    Result<Map<String,Object>> znProductRediect(ZnProductParam znProductParam);
    ///E生宝承保
    Result<Map<String,Object>> acceptOrder(AcceptParam acceptParam);
    //众安回调
    Result<Map<String,Object>> znProductNotify(String result);
    //理财产品 借款处理
    Result<Map<String,Object>> handBorrowProduct(String result);
    //理财产品 创建借款产品
    Result<Map<String,Object>> createBorrowProduct(String result);
    //会员升级回调--> 易宝
    Result<Map<String,Object>> buyMemberNotifyForYiBao(String result);
    //中瑞回调
    Result<Map<String,Object>> zrProductNotify(String result);
    //会员升级回调--> 微信H5
    Result<Map<String,Object>> buyMemberNotifyForWxH5(String orderId);
    //加油卡回调--> 微信H5
    Result<Map<String,Object>> rechargeNotifyForWxH5Recharge(String orderId);
    //易安创建订单
    Result<JSONObject> yiAnCreateOrder(String jsonStr);
    //易安支付
    Result<JSONObject> yiAnOrderPay(String jsonStr);

    Result<Boolean> axOrderCallBack(String str);

    Result<Boolean> axPolicyCallBack(String str);

    Result<JSONObject> yiAnNotify(String jsonStr);
    Result<Map<String, Object>> rechargeNotifyForYiBao(String result);
}
