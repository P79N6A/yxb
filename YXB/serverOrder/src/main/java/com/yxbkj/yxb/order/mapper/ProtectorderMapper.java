package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.module.ProtectOrder;
import com.yxbkj.yxb.entity.order.OrderProtect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProtectorderMapper extends BaseMapper<OrderProtect> {
    List<ProtectOrder> selectMedical(@Param("orderMemberId") String orderMemberId,
                                     @Param("orderStatus")String orderStatus,
                                     @Param("limit")Integer limit,
                                     @Param("offset")Integer offset);
    List<ProtectOrder> selectPayMedical(@Param("orderMemberId") String orderMemberId,
                                     @Param("payStatus")String payStatus,
                                     @Param("limit")Integer limit,
                                     @Param("offset")Integer offset);
    List<ProtectOrder> medicalAll(@Param("orderMemberId")String orderMemberId,
                                  @Param("limit")Integer limit,
                                  @Param("offset")Integer offset);

}
