package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;

import java.util.Map;

/**
 * <p>
 * 删除订单
 * </p>
 *
 * @author 唐漆
 * @since 2018-07-24
 */
public interface DelectMapper extends BaseMapper<Order> {
    String delectOrder (String orderId);
}