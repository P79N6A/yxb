package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.module.OrderVo;
import com.yxbkj.yxb.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarorderMapper extends BaseMapper<Order> {

    List<OrderVo> selectOrder(@Param("orderMemberId") String orderMemberId,
                              @Param("orderStatus")String orderStatus,
                              @Param("limit")Integer limit,
                              @Param("offset")Integer offset);

    List<OrderVo> selectPay(@Param("orderMemberId") String orderMemberId,
                              @Param("payStatus")String payStatus,
                              @Param("limit")Integer limit,
                              @Param("offset")Integer offset);
    List<OrderVo> selectAll(@Param("orderMemberId")String orderMemberId,
                            @Param("limit")Integer limit,
                            @Param("offset")Integer offset);
}
