package com.yxbkj.yxb.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.app.ImgInfo;
import com.yxbkj.yxb.entity.module.AccidentOrder;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.order.mapper.AccidentMapper;
import com.yxbkj.yxb.order.service.AccidentService;
import com.yxbkj.yxb.order.service.ConfigService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class AccidentServiceImpl extends ServiceImpl<AccidentMapper, Order> implements AccidentService {
    @Autowired
    private AccidentMapper accidentMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    @AccessToken
    public Result<Map<String, Object>> selectAccident(
            @ApiParam(value = "令牌",required = true)@RequestParam(value = "token") String  token,
            String orderStatus,String payStatus, Integer limit, Integer offset) {

        String orderMemberId = redisTemplateUtils.getStringValue(token);

        if (orderMemberId == null){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }

        limit = (limit+1-1)*offset;
        String configValue = configService.getConfigValue("systemImageUrl");
        Map<String,Object> map = new HashMap<>();

        if (orderStatus != null){
            List<AccidentOrder> orders = accidentMapper.selectOrderAccident(orderMemberId, orderStatus, limit, offset);
            for (AccidentOrder protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
        }

        if (payStatus != null){
            List<AccidentOrder> orders = accidentMapper.selectPayAccident(orderMemberId, payStatus, limit, offset);
            for (AccidentOrder protectOrder : orders){
                protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
            }
            map.put("data",orders);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
        }

        List<AccidentOrder> orders = accidentMapper.accidentAll(orderMemberId, limit, offset);
        for (AccidentOrder protectOrder : orders){
            protectOrder.setCompanyLogo(configValue + protectOrder.getCompanyLogo());
        }
        map.put("data",orders);
        return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);

    }
}
