
package com.yxbkj.yxb.system.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

public interface FuePaylService {
    Result<Map<String,Object>> fuelPay(String token,String payOrderId,String source);
}

