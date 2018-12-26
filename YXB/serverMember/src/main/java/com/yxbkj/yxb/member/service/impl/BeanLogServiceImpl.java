package com.yxbkj.yxb.member.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxbkj.yxb.entity.member.*;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.module.YxbConstants;
import com.yxbkj.yxb.member.mapper.BeanLogMapper;
import com.yxbkj.yxb.member.service.BeanLogService;
import com.yxbkj.yxb.member.service.MemberInfoService;
import com.yxbkj.yxb.member.service.MemberPropertyHisService;
import com.yxbkj.yxb.member.service.MemberPropertyService;
import com.yxbkj.yxb.util.DateUtils;
import com.yxbkj.yxb.util.HttpKit;
import com.yxbkj.yxb.util.RedisTemplateUtils;
import com.yxbkj.yxb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 易豆日志表 服务实现类
 * </p>
 *
 * @author 李明
 * @since 2018-08-13
 */
@Service
public class BeanLogServiceImpl extends ServiceImpl<BeanLogMapper, BeanLog> implements BeanLogService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberPropertyService memberPropertyService;
    @Autowired
    private MemberPropertyHisService memberPropertyHisService;
    @Autowired
    private RedisTemplateUtils redisTemplateUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Map<String, Object>> memberSignIn(String token,String activeType) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Map<String,Object>>(Code.FAIL, "签到失败!会员ID为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<MemberInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        MemberInfo memberInfo = memberInfoService.selectOne(wrapper);
        if(memberInfo==null){
            return new Result<Map<String,Object>>(Code.FAIL, "签到失败!会员不存在或者已经被冻结!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<BeanLog> logWrapper = new EntityWrapper<>();
        logWrapper.eq("member_id",memberId);
        logWrapper.eq("active_type",YxbConstants.ACTIVETYPE_YIDOU_SIGN);
        logWrapper.ge("creator_time",LocalDate.now().toString()+" 00:00:00");
        int logCount = selectCount(logWrapper);
        if(logCount>0){
            return new Result<Map<String,Object>>(Code.FAIL, "今日已签到!", null, Code.IS_ALERT_YES);
        }
        //构建实体信息
        BeanLog log = new BeanLog();
        log.setId(StringUtil.getUuid());
        log.setMemberId(memberInfo.getMemberId());
        log.setActiveType(YxbConstants.ACTIVETYPE_YIDOU_SIGN);
        log.setCreatorIp(HttpKit.getClientIP());
        log.setValidity(YxbConstants.DATA_NORMAL_STATUS_CODE);
        log.setCreatorTime(DateUtils.getSysDate());
        log.setRemark("签到奖励");
        //计算签到天数  算法:查询上一天是否签到 签到则 连续天数+1 否则 连续签到天数为1 如果大于7  则 取模 =7 取7
        int signDays = getContinuSignDays(LocalDate.now().toString(),memberInfo.getMemberId());
        int continueDays = signDays;
        if(signDays % 7 == 0){
            signDays = 7;
        }else{
            signDays = signDays % 7;
        }
        signDays = calcEban(signDays);//重新计算易豆的金额
        log.setBean(signDays);
        insert(log);
        // 修改资产信息
        EntityWrapper<MemberProperty> propertyWrapper = new EntityWrapper<>();
        propertyWrapper.eq("member_id",memberId);
        MemberProperty property = memberPropertyService.selectOne(propertyWrapper);
        // 添加资产信息流水
        MemberPropertyHis propertyHis = new MemberPropertyHis();
        propertyHis.setId(StringUtil.getUuid());
        propertyHis.setPropertyAmount(property.getPropertyAmount());//总收入
        propertyHis.setFrozenAmount(property.getFrozenAmount());//冻结金额
        propertyHis.setAvailableAmount(property.getAvailableAmount());//可用金额  为 0
        propertyHis.setSubmittedAmount(property.getSubmittedAmount());//已提金额
        propertyHis.setEbean(property.getEbean());
        propertyHis.setRemark("签到奖励");
        propertyHis.setMemberId(memberId);
        propertyHis.setModifierTime(YxbConstants.sysDate());
        memberPropertyHisService.insert(propertyHis);
        property.setEbean(property.getEbean()+signDays);
        memberPropertyService.updateById(property);
        Map<String,Object> map = new HashMap<>();
        map.put("continueDays",continueDays);
        map.put("signInfo",log);
        map.put("property",property);
        return new Result<Map<String,Object>>(Code.SUCCESS, "签到成功!", map, Code.IS_ALERT_NO);
    }
    public int calcEban(int days){
        double pow = Math.pow(2, days-1);
        return (int) pow;
    }
    @Override
    public Result<Boolean> todaySignIn(String token) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Boolean>(Code.FAIL, "查询失败!会员ID为空!", null, Code.IS_ALERT_YES);
        }
        EntityWrapper<BeanLog> logWrapper = new EntityWrapper<>();
        logWrapper.eq("member_id",memberId);
        logWrapper.eq("active_type",YxbConstants.ACTIVETYPE_YIDOU_SIGN);
        logWrapper.ge("creator_time",LocalDate.now().toString()+" 00:00:00");
        int logCount = selectCount(logWrapper);
        return new Result<Boolean>(Code.SUCCESS, "查询成功!", logCount>0, Code.IS_ALERT_NO);
    }

    @Override
    public Result<Page<BeanLog>> getBeanLog(String token,String type,Integer offset,Integer limit) {
        String memberId = redisTemplateUtils.getStringValue(token);
        if(memberId==null){
            return new Result<Page<BeanLog>>(Code.FAIL, "查询失败!会员ID为空!", null, Code.IS_ALERT_YES);
        }
        Page<BeanLog> page = new Page(offset,limit);
        EntityWrapper<BeanLog> wrapper = new EntityWrapper();
        wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
        wrapper.eq("member_id",memberId);
        //wrapper.eq("active_type",YxbConstants.ACTIVETYPE_YIDOU_SIGN);
        if(!StringUtil.isEmpty(type)){
            if("0".equals(type)){
                wrapper.gt("bean","0");
            }
            if("1".equals(type)){
                wrapper.lt("bean","0");
            }
        }
        page.setOrderByField("creator_time"); // 排序参数
        page.setAsc(false); // 为true表示顺序排列，false为倒序排列
        Page<BeanLog> resPage = selectPage(page, wrapper);
        return new Result<Page<BeanLog>>(Code.SUCCESS,"获取成功!",resPage,Code.IS_ALERT_NO);

    }

    /**
     * 查询今天之前 签到的天数
     * @param day
     * @param memberId
     * @return
     */
    private int getContinuSignDays(String day,String memberId){
        int days = 1;
        try{
            // 转换成昨天的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(day);
            Date date = new Date();
            date.setTime(parse.getTime()-24L*60L*60L);
            String yestoday = sdf.format(date);
            // 构建查询条件
            EntityWrapper<BeanLog> wrapper = new EntityWrapper<>();
            wrapper.eq("member_id",memberId);
            wrapper.eq("active_type",YxbConstants.ACTIVETYPE_YIDOU_SIGN);
            wrapper.eq("validity",YxbConstants.DATA_NORMAL_STATUS_CODE);
            wrapper.ge("creator_time",yestoday + " 00:00:00");
            wrapper.le("creator_time",day + " 00:00:00");
            int count = selectCount(wrapper);
            if(count>0){
                //不为空 则之前已经签到过  为空则之前没有签到 值为1
                days+=getContinuSignDays(yestoday,memberId);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("日期转换异常"+e.getMessage());
        }
       return days;
    }

}
