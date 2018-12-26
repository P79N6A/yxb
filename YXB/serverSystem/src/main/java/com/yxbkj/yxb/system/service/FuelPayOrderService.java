package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.FuelPayOrder;

import java.util.Map;

/**
 * <p>
 * 支付订单 服务类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
public interface FuelPayOrderService extends IService<FuelPayOrder> {
    Result<Map<String,Object>> addPayOrder(Double discountNum, Integer months, Double amount, String token, String payType, String orderType,String fuelNumber,Double monthAmount,String orderSource);
    Result<Page<Map<String,Object>>> payOrderList(String token, Integer limit, Integer offset,String payStatus);
    Result<Map<String,Object>> updatePayOrderStatus(String payOrderId);

}
