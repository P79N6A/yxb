package com.yxbkj.yxb.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.OrderStaService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.reCharge.ShoujiHuaFei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态查询
 * @author zy
 * @desc
 * @since
 */
@Service
public class OrderStaServiceImpl implements OrderStaService {
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    @Transactional
    public Result<Map<String, Object>> orderSta(String phone) {
        Result<Map<String,Object>> result = null;
        if(phone == null || phone.equals("")) {
            return result = new Result<>(Code.FAIL,"查询异常",new HashMap<>(),Code.IS_ALERT_NO);
        }
        String orderId = redisTemplateUtils.getStringValue(phone);
        if(orderId == null) {
            return result = new Result<>(Code.FAIL,"订单不存在或过期",new HashMap<>(),Code.IS_ALERT_NO);
        }
        try {
            Map<String,Object> map = new HashMap<>();
            String orderSta = ShoujiHuaFei.orderSta(orderId);
            JSONObject jsonObject = JSONObject.parseObject(orderSta);
            map.put("orderSta",orderSta);
            map.put("error_code",jsonObject.get("error_code"));
            return result = new Result<>(Code.SUCCESS,"查询成功",map,Code.IS_ALERT_NO);
        } catch (Exception e) {
            e.printStackTrace();
            return result = new Result<>(Code.FAIL,"查询异常",new HashMap<>(),Code.IS_ALERT_NO);
        }
    }
}
