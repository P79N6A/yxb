package com.yxbkj.yxb.product.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.product.ProtectCard;

import java.math.BigDecimal;
import java.util.Map;

public interface CardService extends IService<ProtectCard> {
    Result<Map<String, Object>> insertCard(
            String productId, BigDecimal amount, String plateNumber,
            String chassisNumber, String token,
            String policyHolder, String policyCard,
            String policyPhone, String number, String source);
}
