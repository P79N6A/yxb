package com.yxbkj.yxb.product.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yxbkj.yxb.entity.product.ProductInvestment;
import com.yxbkj.yxb.entity.product.ProductRepertory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 产品仓库表 Mapper 接口
 * </p>
 *
 * @author 李明
 * @since 2018-11-12
 */
public interface ProductRepertoryMapper extends BaseMapper<ProductRepertory> {
    List<ProductRepertory> getListByCategory(@Param(value = "categoryId") String categoryId);
}