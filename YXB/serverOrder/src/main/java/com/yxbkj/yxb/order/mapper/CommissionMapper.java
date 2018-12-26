package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.order.Commission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommissionMapper extends BaseMapper<Commission> {
    List<Commission> commissionAll(@Param("sort")String sort, @Param("memberId")String memberId, @Param("limit")Integer limit, @Param("offset")Integer offset);
    List<Commission> commissionSelectS(@Param("status")String status,
                                       @Param("sort")String sort,
                                       @Param("memberId")String memberId,
                                       @Param("limit")Integer limit,
                                       @Param("offset")Integer offset);
}
