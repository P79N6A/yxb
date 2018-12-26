package com.yxbkj.yxb.product.controller;


import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.product.service.PremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/premium")
public class PremiumController {

    @Autowired
    private PremiumService premiumService;
    @GetMapping("/premium")

    public Result<Map<String, Object>> premium(String productId, String minAge, String maxAge, String socialSecurityType, String insuranceScale, String timeLimit) {
        return premiumService.premium(productId,minAge,maxAge, socialSecurityType,insuranceScale, timeLimit);
    }
}
