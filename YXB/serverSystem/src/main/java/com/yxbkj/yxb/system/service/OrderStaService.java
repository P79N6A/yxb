package com.yxbkj.yxb.system.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public interface OrderStaService {
    Result<Map<String,Object>> orderSta(String phone);

}
