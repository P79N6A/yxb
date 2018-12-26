package com.yxbkj.yxb.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.product.FuelCard;

import java.util.Map;

/**
 * <p>
 * 加油卡表 服务类
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
public interface FuelCardService extends IService<FuelCard> {
    Result<Map<String,Object>> bindFuelCard(String token, String cardNumber);
    Result<Map<String,Object>> getFuelCardList(String token);
    Result<Map<String,Object>> unbindCard(String token, String cardNumber);
    Result<Map<String,Object>> exChangeCard(String token, String cardNumber);
}
