package com.yxbkj.yxb.order.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;


public interface CommissionService {
    Result<Map<String,Object>> commissionAll(String status, String sort, String token, Integer limit, Integer offset);
}
