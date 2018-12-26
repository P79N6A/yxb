package com.yxbkj.yxb.order.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProductInvestment;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 产品信息表 Mapper 接口
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    Integer getNextProductId();
}