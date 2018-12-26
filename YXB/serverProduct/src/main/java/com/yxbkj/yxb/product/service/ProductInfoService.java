package com.yxbkj.yxb.product.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProductInvestment;

/**
 * <p>
 * 产品信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
public interface ProductInfoService extends IService<ProductInfo> {
    ProductInvestment getInvestmentProduct(String productId, String productType);
}
