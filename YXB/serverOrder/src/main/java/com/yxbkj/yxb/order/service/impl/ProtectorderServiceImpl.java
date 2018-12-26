package com.yxbkj.yxb.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.AccidentOrder;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.ProtectOrder;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.OrderProtect;
import com.yxbkj.yxb.order.mapper.ProtectorderMapper;
import com.yxbkj.yxb.order.service.CarorderService;
import com.yxbkj.yxb.order.service.ConfigService;
import com.yxbkj.yxb.order.service.ProtectorderService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class ProtectorderServiceImpl extends ServiceImpl<ProtectorderMapper, OrderProtect> implements ProtectorderService {
    @Autowired
    private ProtectorderMapper protectorderMapper;

    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    @AccessToken
    public Result<Map<String, Object>> selectMedical(
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
            List<ProtectOrder> orders = protectorderMapper.selectMedical(orderMemberId, orderStatus, limit, offset);
            for (ProtectOrder protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);

        }


        if (payStatus != null){
            List<ProtectOrder> orders = protectorderMapper.selectPayMedical(orderMemberId, payStatus, limit, offset);
            for (ProtectOrder protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);

        }

        List<ProtectOrder> orders = protectorderMapper.medicalAll(orderMemberId, limit, offset);
        for (ProtectOrder protectOrder : orders){
            protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
        }
        map.put("data",orders);
        return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);

    }
}
