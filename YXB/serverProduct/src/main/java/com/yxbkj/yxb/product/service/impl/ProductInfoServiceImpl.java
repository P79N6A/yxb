package com.yxbkj.yxb.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.product.ProductInfo;
import com.yxbkj.yxb.entity.product.ProductInvestment;
import com.yxbkj.yxb.product.mapper.ProductInfoMapper;
import com.yxbkj.yxb.product.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品信息表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInvestment getInvestmentProduct(String productId, String productType) {
        return productInfoMapper.getInvestmentProduct(productId,productType);
    }
}
