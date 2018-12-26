package com.yxbkj.yxb.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.Premium;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.product.mapper.PremiumMapper;
import com.yxbkj.yxb.product.service.PremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PremiumServiceImpl extends ServiceImpl<PremiumMapper, Premium> implements PremiumService {

    @Autowired
    private PremiumMapper premiumMapper;

    public List<Premium> selectPremium(String productId, String minAge, String maxAge, String socialSecurityType, String insuranceScale, String timeLimit) {
        if(timeLimit == null || timeLimit.equals("") ){
            List<Premium> premium = premiumMapper.selectList(
                    new EntityWrapper<Premium>()
                            .eq("validity",10000001)
                            .eq("product_id", productId)
                            .eq("min_age",minAge)
                            .eq("max_age",maxAge)
                            .eq("social_security_type",socialSecurityType)
                            .eq("insurance_scale",insuranceScale)
            );
            return premium;
        }
        List<Premium> premium = premiumMapper.selectList(
                new EntityWrapper<Premium>()
                        .eq("validity",10000001)
                        .eq("product_id", productId)
                        .eq("time_limit",timeLimit)
                        .eq("insurance_scale",insuranceScale)
        );
        return premium;
    }

    @Override
    public Result<Map<String, Object>> premium(String productId, String minAge, String maxAge, String socialSecurityType, String insuranceScale, String timeLimit) {
        Result<Map<String, Object>> result = null;
        List<Premium> news = selectPremium(productId,minAge,maxAge,socialSecurityType,insuranceScale,timeLimit);
        Map<String, Object> map = new HashMap<>();
        map.put("test", news);
        result = new Result<>(Code.SUCCESS, "查询成功!", map, Code.IS_ALERT_NO);
        return result;
    }
}
