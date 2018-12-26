package com.yxbkj.yxb.system.service.impl;

import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.ReChargeService;
import com.yxbkj.yxb.util.reCharge.ShoujiHuaFei;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询充值费用
 * @author zy
 * @desc
 * @since
 */
@Service
public class ReChargeServiceImpl implements ReChargeService {
    @Override
    @Transactional
    public Result<Map<String, Object>> reCharge(String phoneno, Integer cardNum) {
        Result<Map<String, Object>> result = null;
        if(phoneno == null || cardNum == null) {
         return result = new Result<>(Code.FAIL,"手机号码或金额为空");
        }
        try {
            //是否可以充值
            int flag = ShoujiHuaFei.telCheck(phoneno, cardNum);
            if(flag != 0) {
                Map<String,Object> map = new HashMap<>();
                map.put("code",flag);
                map.put("message","");
                return new Result<>(Code.FAIL,"充值失败",map, Code.IS_ALERT_YES);
            }
            //查询充值费用
            String telQuery = ShoujiHuaFei.telQuery(phoneno, cardNum);
            Map<String,Object> map = new HashMap<>();
            map.put("code",flag);
            map.put("telQuery",telQuery);
            return new Result<>(Code.FAIL,"可以充值",map, Code.IS_ALERT_YES);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            return new Result<>(Code.FAIL,"充值失败",map,Code.IS_ALERT_YES);
        }
    }
}
