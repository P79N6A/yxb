package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.product.FuelDiscount;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 折扣表 服务类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
public interface FuelDiscountService extends IService<FuelDiscount> {
    Result<Map<String,Object>> incrDiscount(String discountType, Integer months, Double discountNum,Double costRate,Double commissionRate,Integer hesiPeriod);
    Result<List<FuelDiscount>> getDiscountList(String disCountType);
}
