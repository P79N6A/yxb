package com.yxbkj.yxb.system.service;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public interface ReChargeService {
    Result<Map<String, Object>> reCharge(String phoneno, Integer cardNum);
}
