package com.yxbkj.yxb.system.service.impl;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.MemberInfo;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.entity.product.FuelCard;
import com.yxbkj.yxb.system.mapper.FuelCardMapper;
import com.yxbkj.yxb.system.mapper.MemberInfoMapper;
import com.yxbkj.yxb.system.service.FuelCardService;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.aliyun.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 加油卡表 绑定卡号
 * </p>
 *
 * @author ZY
 * @since 2018-12-13
 */
@Service
public class FuelCardServiceImpl extends ServiceImpl<FuelCardMapper, FuelCard> implements FuelCardService {
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private FuelCardMapper fuelCardMapper;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> bindFuelCard(String token, String cardNumber) {
        Result<Map<String,Object>> result = null;
        redisTemplateUtils.stringAdd("234","M100002374",60*60*1024);
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
            return result = new Result<>(Code.FAIL,"token为空或者已过期",null,Code.IS_ALERT_YES);
           // memberId = "M100002374";
        }
        EntityWrapper<FuelCard> ef = new EntityWrapper<>();
        ef.eq("member_id",memberId).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        List<FuelCard> fuelCardList = selectList(ef);
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(memberId);
        MemberInfo memberInfo1 = memberInfoMapper.selectOne(memberInfo);
        if(memberInfo1 == null) {
            return result = new Result<>(Code.FAIL,"会员不存在",null,Code.IS_ALERT_YES);
        }
        if(cardNumber == null) {
            return result = new Result<>(Code.FAIL,"卡号为空或格式错误",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelCard> ef1 = new EntityWrapper<>();
        ef1.eq("member_id",memberId).eq("card_number",cardNumber).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        FuelCard fuelCard1 = selectOne(ef1);
        if(fuelCard1 != null) {
            return result = new Result<>(Code.FAIL,"已绑定的卡号",null,Code.IS_ALERT_YES);
        }
        FuelCard fuelCard = new FuelCard();
        fuelCard.setId(StringUtil.getUuid());
        fuelCard.setMemberPhone(memberInfo1.getPhone());
        fuelCard.setMemberName(memberInfo1.getMemberName());
        fuelCard.setMemberId(memberId);
        if(fuelCardList.isEmpty()) {
            fuelCard.setIsDefault(YxbConstants.ISDEFAULT);
        } else {
            fuelCard.setIsDefault(YxbConstants.NOTISDEFAULT);
        }
        fuelCard.setCardNumber(cardNumber);
        if(cardNumber.startsWith("100011") && cardNumber.length() == 19) {
            fuelCard.setCardType("中石化");
        } else if(cardNumber.startsWith("9") && cardNumber.length() == 16) {
            fuelCard.setCardType("中石油");
        } else {
            return result = new Result<>(Code.FAIL,"卡号为空或格式错误",null,Code.IS_ALERT_YES);
        }
        fuelCard.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelCard.setCreatorTime(StringUtil.dateNow());
        Integer insert = fuelCardMapper.insert(fuelCard);
        if(insert > 0) {
            return result = new Result<>(Code.SUCCESS,"绑定成功",null,Code.IS_ALERT_YES);
        } else {
            return result = new Result<>(Code.SUCCESS,"绑定异常",null,Code.IS_ALERT_YES);
        }
    }

    @Override
    public Result<Map<String, Object>> getFuelCardList(String token) {
        Result<Map<String,Object>> result = null;
        Map<String,Object> map = new HashMap<>();
        redisTemplateUtils.stringAdd("123","M100002824",60*60*12);
        redisTemplateUtils.stringAdd("321","M100002374",60*60*12);

        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
           return result = new Result<>(Code.FAIL,"token为空或者已过期",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelCard> ef = new EntityWrapper<>();
        ef.eq("member_id",memberId).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).orderBy("creator_time DESC");
        List<FuelCard> fuelCardList = selectList(ef);
        if(!fuelCardList.isEmpty()) {
            map.put("fuelCardList",fuelCardList);
            return result = new Result<>(Code.SUCCESS,"查询成功",map,Code.IS_ALERT_NO);
        } else {
            map.put("fuelCardList",fuelCardList);
            return result = new Result<>(Code.SUCCESS,"没有绑定加油卡",map,Code.IS_ALERT_NO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> unbindCard(String token, String cardNumber) {
        Result<Map<String,Object>> result = null;
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
            return result = new Result<>(Code.FAIL,"token为空或者已过期",null,Code.IS_ALERT_YES);
        }
        if(cardNumber == null) {
            return result = new Result<>(Code.FAIL,"卡号为空或格式错误",null,Code.IS_ALERT_YES);
        }
        EntityWrapper<FuelCard> ef = new EntityWrapper<>();
        ef.eq("member_id",memberId).eq("card_number",cardNumber).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        FuelCard fuelCard1 = selectOne(ef);
        if(fuelCard1 == null) {
            return result = new Result<>(Code.FAIL,"卡号不存在",null,Code.IS_ALERT_YES);
        }
        fuelCard1.setValidity(YxbConstants.DATA_DELETE_STATUS_CODE);
        boolean flag = updateById(fuelCard1);
        if(flag) {
            EntityWrapper<FuelCard> ef1 = new EntityWrapper<>();
            ef1.eq("member_id",memberId).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE).orderBy("creator_time DESC");
            List<FuelCard> fuelCardList = selectList(ef1);
            if(!fuelCardList.isEmpty()) {
                for (int i = 0; i < fuelCardList.size(); i++) {
                    if(YxbConstants.ISDEFAULT.equals(fuelCardList.get(i).getIsDefault())) {
                        return result = new Result<>(Code.SUCCESS,"解绑成功",null,Code.IS_ALERT_YES);
                    }
                }
                fuelCardList.get(0).setIsDefault(YxbConstants.ISDEFAULT);
                boolean b = updateById(fuelCardList.get(0));
                if(b) {
                    return result = new Result<>(Code.SUCCESS,"解绑成功",null,Code.IS_ALERT_YES);
                } else {
                    return result = new Result<>(Code.FAIL,"设置默认失败",null,Code.IS_ALERT_YES);
                }
            }
            return result = new Result<>(Code.SUCCESS,"解绑成功",null,Code.IS_ALERT_YES);
        }
            return result = new Result<>(Code.FAIL,"解绑失败",null,Code.IS_ALERT_YES);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> exChangeCard(String token, String cardNumber) {
        Result<Map<String,Object>> result = null;
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId == null) {
            return result = new Result<>(Code.FAIL,"token为空或者token过期",null,Code.IS_ALERT_YES);
        }
        FuelCard fuelCard2 = new FuelCard();
        fuelCard2.setIsDefault(YxbConstants.ISDEFAULT);
        fuelCard2.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelCard2.setMemberId(memberId);

        FuelCard fuelCard = fuelCardMapper.selectOne(fuelCard2);
        if(fuelCard != null) {
            fuelCard.setIsDefault(YxbConstants.NOTISDEFAULT);
            boolean b = updateById(fuelCard);
            if(!b) {
                return result = new Result<>(Code.FAIL,"修改默认失败",null,Code.IS_ALERT_NO);
            }
        }
        EntityWrapper<FuelCard> ea = new EntityWrapper<>();
        ea.eq("member_id",memberId).eq("card_number",cardNumber).eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        FuelCard fuelCard3 = selectOne(ea);
       /* FuelCard fuelCard1 = new FuelCard();
        fuelCard1.setMemberId(memberId);
        fuelCard1.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        fuelCard1.setCardNumber(cardNumber);*/
        if(fuelCard3 == null) {
            return new Result<>(Code.FAIL,"不存在的加油卡",null,Code.IS_ALERT_YES);
        }
        fuelCard3.setIsDefault(YxbConstants.ISDEFAULT);
        Integer integer1 = fuelCardMapper.updateById(fuelCard3);
        if(integer1 == 1) {
            return result = new Result<>(Code.SUCCESS,"切换成功",null,Code.IS_ALERT_NO);
        } else if(integer1 <= 0) {
            return result = new Result<>(Code.FAIL,"切换默认失败",null,Code.IS_ALERT_NO);
        } else {
            return result = new Result<>(Code.FAIL,"系统错误",null,Code.IS_ALERT_NO);
        }
    }
}
