package com.yxbkj.yxb.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.OrderVo;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.order.mapper.CarorderMapper;
import com.yxbkj.yxb.order.service.CarorderService;
import com.yxbkj.yxb.order.service.ConfigService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class CarorderServiceImpl extends ServiceImpl<CarorderMapper, Order> implements CarorderService {
    @Autowired
    private CarorderMapper carorderMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    @AccessToken
    public Result<Map<String, Object>> selectOrder(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token,
            String orderStatus, String payStatus, Integer limit, Integer offset) {
        String orderMemberId = redisTemplateUtils.getStringValue(token);

        if (orderMemberId == null){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        limit = (limit+1-1)*offset;
        String configValue = configService.getConfigValue("systemImageUrl");
        Map<String,Object> map = new HashMap<>();

        if (orderStatus != null){
            List<OrderVo> orders = carorderMapper.selectOrder(orderMemberId, orderStatus, limit, offset);
            for (OrderVo protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
        }

        if (payStatus != null){
            List<OrderVo> orders = carorderMapper.selectPay(orderMemberId, payStatus, limit, offset);
            for (OrderVo protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
        }

        List<OrderVo> orders = carorderMapper.selectAll(orderMemberId, limit, offset);
        for (OrderVo protectOrder : orders){
            protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
        }
        map.put("data",orders);
        return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);

    }

}
