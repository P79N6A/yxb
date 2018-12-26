package com.yxbkj.yxb.system.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public interface OnlineOrderService {
    Result<Map<String,Object>> OnlineOrder(String phone, int cardnum);
}
