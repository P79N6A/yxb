package com.yxbkj.yxb.system.mapper;

import com.yxbkj.yxb.entity.module.Result;

/**
 * @author zy
 * @desc
 * @since
 */
public interface OnlineOrderMapper {
    Result<String> OnlineOrder(String phone, int cardnum);
}
