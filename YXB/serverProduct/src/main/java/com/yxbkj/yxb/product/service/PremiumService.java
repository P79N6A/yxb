package com.yxbkj.yxb.product.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.app.Premium;
import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface PremiumService extends IService<Premium> {
    Result<Map<String, Object>> premium (String productId, String minAge, String maxAge, String socialSecurityType, String insuranceScale, String timeLimit);
}

