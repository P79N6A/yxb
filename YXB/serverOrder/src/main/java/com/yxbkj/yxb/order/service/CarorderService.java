package com.yxbkj.yxb.order.service;

import com.yxbkj.yxb.entity.module.Result;
import java.util.Map;


public interface CarorderService {
    Result<Map<String,Object>> selectOrder(String token, String orderStatus, String payStatus, Integer limit, Integer offset);
}
