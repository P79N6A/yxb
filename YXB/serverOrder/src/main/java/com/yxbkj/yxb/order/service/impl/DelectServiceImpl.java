package com.yxbkj.yxb.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.order.Order;
import com.yxbkj.yxb.entity.order.OrderProtect;
import com.yxbkj.yxb.entity.vo.CalculatePremiumParam;
import com.yxbkj.yxb.entity.vo.OrderParam;
import com.yxbkj.yxb.order.mapper.DelectMapper;
import com.yxbkj.yxb.order.mapper.OrderMapper;
import com.yxbkj.yxb.order.service.DelectService;
import com.yxbkj.yxb.order.service.MemberInfoService;
import com.yxbkj.yxb.order.service.OrderProtectService;
import com.yxbkj.yxb.order.service.OrderService;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.pingan.EPay;
import com.yxbkj.yxb.util.pingan.PingAnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-07-24
 */
@Service
public class DelectServiceImpl extends ServiceImpl<DelectMapper, Order> implements DelectService {
    @Autowired
    private DelectMapper delectMapper;

    @Override
    public Result<Map<String, Object>> delectOrder(String orderId) {

        String s = delectMapper.delectOrder(orderId);
        Map<String, Object> map = new HashMap<>();
        map.put("data",s);
        return new Result<Map<String, Object>>(Code.SUCCESS, "删除订单成功!", map , Code.IS_ALERT_NO);
    }
}
