package com.yxbkj.yxb.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.entity.vo.CalculatePremiumParam;
import com.yxbkj.yxb.entity.vo.OrderParam;

import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-07-24
 */
public interface DelectService extends IService<Order> {
   Result<Map<String, Object>> delectOrder(String orderId);
}
