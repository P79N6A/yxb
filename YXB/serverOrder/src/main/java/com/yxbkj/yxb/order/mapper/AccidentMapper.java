package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.module.AccidentOrder;
import com.yxbkj.yxb.entity.module.ProtectOrder;
import com.yxbkj.yxb.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccidentMapper extends BaseMapper<Order> {
    List<AccidentOrder> selectOrderAccident(@Param("orderMemberId") String orderMemberId,
                                       @Param("orderStatus")String orderStatus,
                                       @Param("limit")Integer limit,
                                       @Param("offset")Integer offset);
    List<AccidentOrder> selectPayAccident(@Param("orderMemberId") String orderMemberId,
                                       @Param("payStatus")String payStatus,
                                       @Param("limit")Integer limit,
                                       @Param("offset")Integer offset);
    List<AccidentOrder> accidentAll(@Param("orderMemberId")String orderMemberId,
                                    @Param("limit")Integer limit,
                                    @Param("offset")Integer offset);
}
