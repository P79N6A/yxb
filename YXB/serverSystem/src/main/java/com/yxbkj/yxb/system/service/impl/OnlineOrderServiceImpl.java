package com.yxbkj.yxb.system.service.impl;

import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.OnlineOrderService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.reCharge.ShoujiHuaFei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 手机直充
 * @author zy
 * @desc
 * @since
 */
@Service
public class OnlineOrderServiceImpl implements OnlineOrderService {
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    @Transactional
    public Result<Map<String,Object>> OnlineOrder(String phone, int cardnum) {
        Result<Map<String,Object>> result = null;
        if(phone == null || cardnum == 0) {
            return result = new Result<>(Code.FAIL,"手机号码或金额为空",Code.IS_ALERT_YES);
        }
        try {
            String orderId = StringUtil.getUuid();
            redisTemplateUtils.stringAdd(phone,orderId,1 * 60 * 60 * 24);
            String onlineOrder = ShoujiHuaFei.onlineOrder(phone, cardnum, orderId);
            Map<String,Object> map = new HashMap<>();
            map.put("onlineOrder",onlineOrder);
            return result = new Result<>(Code.SUCCESS,"充值成功",map,Code.IS_ALERT_NO);
        } catch (Exception e) {
            e.printStackTrace();
            return result = new Result<>(Code.FAIL,"充值发生异常联系客服",new HashMap<>(),Code.IS_ALERT_NO);
        }
    }
}
