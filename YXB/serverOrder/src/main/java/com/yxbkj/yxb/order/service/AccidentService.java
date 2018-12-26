package com.yxbkj.yxb.order.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;


public interface AccidentService {
    Result<Map<String,Object>> selectAccident(String token, String orderStatus, String payStatus, Integer limit, Integer offset);
}
