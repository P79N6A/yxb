package com.yxbkj.yxb.order.mapper;

import com.yxbkj.yxb.entity.module.Result;

import java.util.Map;

/**
 * 加油卡
 * @author zy
 * @desc
 * @since
 */
public interface JiaYouCardMapper {
    Result<Map<String,Object>> onLineOrder(int proid, String cardNum, String game_userid, String gasCardTel, String gasCardName, int chargeType);
    Result<Map<String,Object>> orderSta(String phone);
    Result<Map<String,Object>> orderStaOrderId(String orderId);
    Result<Map<String, Object>> queryOrder(Integer page, Integer pageSize, String startTime, String endTime);
    Result<Map<String,Object>> yuE();
}
