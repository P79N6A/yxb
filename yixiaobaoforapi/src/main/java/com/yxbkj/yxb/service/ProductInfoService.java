package com.yxbkj.yxb.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.domain.model.ProductInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 产品信息表 服务类
 * </p>
 *
 * @author 李明
 * @since 2018-07-10
 */
public interface ProductInfoService extends IService<ProductInfo> {

    Map<String,Object> selectProductInfoList(String productName, Integer page, Integer limit);

    Map<String, Object> selectProductInfoList(ProductInfo entity);
    int updateProductInfoByIds(String codeIds, HttpServletRequest request) throws Exception;
}
