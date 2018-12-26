package com.yxbkj.yxb.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.FuelRechargeOrder;

import java.util.Map;

/**
 * <p>
 * 充值订单 服务类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
public interface FuelRechargeOrderService extends IService<FuelRechargeOrder> {
    Result<Map<String,Object>> addFuelRechargeOrders(String payOrderId);
    Result<Map<String,Object>> getRechargeOrderList(String payOrderId);
    Result<Map<String,Object>> getPayRechargeOrderByOrderId(String orderId);
}
