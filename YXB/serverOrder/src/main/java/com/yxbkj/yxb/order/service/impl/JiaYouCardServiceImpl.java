package com.yxbkj.yxb.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.order.service.JiaYouCardService;
import com.yxbkj.yxb.util.JiaYouCard.JiaYouCard;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 提交加油卡充值
 * @author zy
 * @desc
 * @since
 */
@Service
public class JiaYouCardServiceImpl implements JiaYouCardService {
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    @Transactional
    public Result<Map<String, Object>> onLineOrder(int proid, String cardNum,String game_userid, String gasCardTel, String gasCardName, int chargeType,String orderId) {
        Result<Map<String,Object>> result = null;
        if(orderId == null) {
            logger.info("订单号为空");
            return result = new Result<>(Code.FAIL,"订单号为空",new HashMap<>(),Code.IS_ALERT_NO);
        }
        boolean flag = redisTemplateUtils.stringAdd(gasCardTel + "jiayou", orderId, -1);
        if(!flag) {
            return result = new Result<>(Code.FAIL,"订单存入缓存失败",new HashMap<>(),Code.IS_ALERT_NO);
        }
        String request3 = JiaYouCard.getRequest3(proid, cardNum, game_userid, gasCardTel, gasCardName, chargeType, orderId);
        Map<String,Object> map = new HashMap<>();
        map.put("onLineOrder",request3);
        return result = new Result<>(Code.SUCCESS,"",map,Code.IS_ALERT_NO);
    }

    @Override
    public Result<Map<String, Object>> orderSta(String phone) {
        Result<Map<String,Object>> result = null;
        if(phone == null) {
            return result = new Result<>(Code.FAIL,"手机号为空",Code.IS_ALERT_NO);
        }
        String orderId = redisTemplateUtils.getStringValue(phone + "jiayou");
        return query(orderId);
    }

    @Override
    public Result<Map<String, Object>> orderStaOrderId(String orderId) {
        Result<Map<String,Object>> result = null;
        return query(orderId);
    }

    @Override
    public Result<Map<String,Object>> queryOrder(Integer page, Integer pageSize, String startTime, String endTime) {
        Result<Map<String,Object>> result = null;
        if(page == null) {
            page = 1;
        }
        if(pageSize == null) {
            pageSize = 50;
        }
       /* String startTimeStr = StringUtil.dateCastToString(startTime);
        String endTimeStr = StringUtil.dateCastToString(endTime);*/
        String resultStr = JiaYouCard.queryByDate(page, pageSize, startTime, endTime);
        Map<String,Object> map = new HashMap<>();
        JSONObject object = JSONObject.parseObject(resultStr);
        if(object.getInteger("error_code")==0){
            map.put("resultOrder",resultStr);
            return result = new Result<>(Code.SUCCESS,"查询成功",map,Code.IS_ALERT_NO);
        }else{
            map.put("error_code",object.get("error_code"));
            map.put("reason",object.get("reason"));
            return result = new Result<>(Code.FAIL,"查询失败",map,Code.IS_ALERT_NO);
        }
    }

    @Override
    public Result<Map<String, Object>> yuE() {
        Result<Map<String,Object>> result = null;
        Map<String, Object> map = JiaYouCard.getRequest2();
        if(map == null || "".equals(map)) {
            return result = new Result<>(Code.FAIL,"查询失败",map,Code.IS_ALERT_NO);
        }
        return new Result<>(map);
    }

    //通过订单查询封装方法
    private Result<Map<String, Object>> query(String orderId) {
        Result<Map<String,Object>> result = null;
        String orderSta = JiaYouCard.getRequest1(orderId);
        JSONObject object = JSONObject.parseObject(orderSta);
        Map<String,Object> map = new HashMap<>();
        if(object.getInteger("error_code")==0){
            map.put("result",orderSta);
            String game_state = object.getString("result");
            JSONObject jsonObject = JSONObject.parseObject(game_state);
            String game_state1 = (String) jsonObject.get("game_state");
            if("0".equals(game_state1)) {
                map.put("status","充值中");
            } else if ("1".equals(game_state1)) {
                map.put("status","充值成功");
            } else {
                map.put("status","充值失败");
            }
            return result = new Result<>(Code.SUCCESS,"查询成功",map,Code.IS_ALERT_NO);
        }else{
            map.put("result",object.get("error_code")+":"+object.get("reason"));
            return result = new Result<>(Code.FAIL,"查询失败",map,Code.IS_ALERT_NO);
        }
    }
}
