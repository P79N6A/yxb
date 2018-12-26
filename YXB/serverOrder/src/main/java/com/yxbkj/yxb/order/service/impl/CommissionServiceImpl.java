package com.yxbkj.yxb.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.order.Commission;
import com.yxbkj.yxb.order.mapper.CommissionMapper;
import com.yxbkj.yxb.order.service.CommissionService;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommissionServiceImpl extends ServiceImpl<CommissionMapper, Commission> implements CommissionService {

    @Autowired
    private CommissionMapper commissionMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;

    @Override
    @AccessToken
    public Result<Map<String, Object>> commissionAll(
            String status, String sort,
            @ApiParam(value = "令牌",required = true)@RequestParam(value="token")String token,
            Integer limit, Integer offset) {
        limit = (limit-1)*offset;

        String memberId = redisTemplateUtils.getStringValue(token);

        if (memberId == null){
            return new Result<>(Code.FAIL, "token为空!", null, Code.IS_ALERT_YES);
        }
        if (StringUtil.isEmpty(sort)){
            sort = "desc";
        }
        if (status == null){
            List<Commission> commissions = commissionMapper.commissionAll(sort, memberId, limit, offset);
            Map<String, Object> map = new HashMap<>();
            map.put("commissions", commissions);
            return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
        }
        List<Commission> commissions = commissionMapper.commissionSelectS(status, sort, memberId, limit, offset);
        Map<String, Object> map = new HashMap<>();
        map.put("commissions", commissions);
        return new Result<>(Code.SUCCESS, "获取数据成功!", map, Code.IS_ALERT_NO);
    }

}
