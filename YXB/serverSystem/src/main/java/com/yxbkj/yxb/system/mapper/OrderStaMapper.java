package com.yxbkj.yxb.system.mapper;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * @author zy
 * @desc
 * @since
 */
public interface OrderStaMapper {
    Result<Map<String,Object>> orderSta(String phone);
}
